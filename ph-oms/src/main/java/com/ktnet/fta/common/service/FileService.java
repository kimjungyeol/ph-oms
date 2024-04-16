package com.ktnet.fta.common.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktnet.common.dto.FileDto;
import com.ktnet.common.dto.FileUploadDto;
import com.ktnet.common.exception.BizzException;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.mapper.FileMapper;

@Service("fileService")
public class FileService {
 
	Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private FileMapper fileMapper;
	
	@Value("${common.file.uploadSystem}")
    private String uploadSystem;

    @Value("${common.file.uploadPath}")
    private String uploadPath;

    @Value("${spring.profiles.active}")
    private String RESOURCE;
    
    /**
     * search atchFile Info
     * @param paramMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchFileInfo(Map<String, Object> paramMap) throws Exception {
        Map<String, Object> rtMap = fileMapper.selectFileInfo(paramMap);
        return rtMap;
    }

    /**
     * search atchFile List
     * @param paramMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFileInfoList(Map<String, Object> paramMap) throws Exception {
        List<Map<String, Object>> rtList = fileMapper.selectFileInfoList(paramMap);
        return rtList;
    }
    
    /**
     * file upload/delete.
     * @param req
     * @param files
     * @throws Exception
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public void saveFileData(ParamMap collector) throws Exception {
        String ERRMSG = "An error occurred while registering the file.";
        
		Map<String, Object> fileDtoMap = (Map<String, Object>) collector.get("fileDtoMap");
        Map<String, Object> paramMap = null;
        
        try {
        	
        	FileDto fileDto = null;
            List<FileUploadDto> saveFileList = null;
            FileUploadDto fileUploadDto = null;
            
            /**
             * 1.delete target file.
             */
			deleteTargetFile(collector);
            
            /**
             * delete/insert file info.
             */
			String fileId = "";
    		for (String key : fileDtoMap.keySet()) {
    			fileDto = (FileDto) fileDtoMap.get(key);
    			saveFileList = fileDto.getUploadList();
    			
                /**
                 * 2.insert file.
                 */
    			if (saveFileList != null && saveFileList.size() > 0) {
    				
    				fileId = saveFileList.get(0).getFileId();
                    for (int i = 0; i < saveFileList.size(); i++) {
                    	fileUploadDto = (FileUploadDto)saveFileList.get(i);
                    	fileUploadDto.printData();
                    	
                    	paramMap = fileUploadDto.getDataMap();
                    	paramMap.put("loginUserId", collector.get("loginUserId")+"");
                    	
                        fileMapper.insertUploadFileInfo(paramMap);
                    }
                }
    			paramMap.put("atchFileId_" + key, fileId);
    		}
    		
            /**
             * 3.save event log.
             */
            //saveEventLog(req, paramMap);

        }catch(NullPointerException e) {
           // FileUtil.uploadFileDeleteForError(uploadPath, saveFileList);
            throw new BizzException(ERRMSG);
        }catch(IOException e) {
           // FileUtil.uploadFileDeleteForError(uploadPath, saveFileList);
            throw new BizzException(ERRMSG);
        }catch(Exception e) {
          //  FileUtil.uploadFileDeleteForError(uploadPath, saveFileList);
            throw new BizzException(ERRMSG);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    public void deleteTargetFile(ParamMap collector) throws Exception {
		List<Map<String, Object>> delFileList = (List<Map<String, Object>>) collector.getMap().get("deleteFileList");
    	
        if ( delFileList != null && delFileList.size() > 0 ) {
        	
        	Map<String, Object> delFileMap= null;
            for ( int i = 0; i < delFileList.size(); i++ ) {
            	delFileMap = (Map<String, Object>)delFileList.get(i);
            	
            	String fileId = delFileMap.get("fileId")+"";
            	String fileUploadPath = delFileMap.get("fileUploadPath")+"";
                if (!"null".equals(fileId)&& !"".equals(fileId)) {
                	
                    File file = new File(uploadPath + File.separator + fileUploadPath + File.separator + fileId);
                    file.delete();  //real file delete.
                    
                    logger.debug("fileMapper.deleteUploadFileInfo, No.{}", i+1);
                    logger.debug("delete File : {}", uploadPath + File.separator + fileUploadPath + File.separator + fileId);
                    
                    //delete info.
                    fileMapper.deleteUploadFileInfo(delFileMap);  //delete file data.
                }
            }
        }
    }
    
    /**
     * Delete all attachments when deleting data.
     * @param paramMap
     * @throws Exception
     */
    @Transactional
    public void removeFileInfo( Map<String, Object> paramMap ) throws Exception {
        if (paramMap.get("fileId") == null || "".equals(paramMap.get("fileId"))) {
            return;
        }

        List<Map<String, Object>> rtList = fileMapper.selectFileInfoList(paramMap);
        if (rtList != null && rtList.size() > 0) {
            Map<String, Object> delFileMap = null;
            String fileUploadPath = "";
            String fileId = "";
            for (int i = 0; i < rtList.size(); i++) {
                delFileMap = rtList.get( i );

                fileUploadPath = delFileMap.get("fileUploadPath")+"";   //target file filePathNm
                fileId = delFileMap.get( "fileId" )+""; 				//target saved atchFile name.
                
                if (fileUploadPath != null && !"".equals(fileUploadPath)) {
                    File file = new File(uploadPath + File.separator + fileUploadPath + File.separator + fileId);
                    file.delete();  //real file delete.
                }
            }

            fileMapper.deleteAllFileInfo(paramMap);
        }
    }

}