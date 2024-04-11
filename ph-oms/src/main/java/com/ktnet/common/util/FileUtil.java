package com.ktnet.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ktnet.common.dto.FileDeleteDto;
import com.ktnet.common.dto.FileDto;
import com.ktnet.common.dto.FileUploadDto;
import com.ktnet.core.map.ParamMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@Component
public class FileUtil {
	
	Logger logger = LoggerFactory.getLogger(getClass());
 
    /** 다운로드 버퍼 크기 */
    private final int BUFFER_SIZE = 8192; // 8kb
    /** 문자 인코딩 */
    private final String CHARSET = "euc-kr";
    
    @Value("${common.file.uploadPath}")
    private String uploadPath;
    
    @Value("${common.file.allowExtention}")
    private String allowExtention;
    
    private FileUtil() {
    }
    
    /**
     * img tag view
     * @param request
     * @param response
     * @param paramMap
     * @throws Exception
     */
    public void fileView( HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap )
        throws Exception {
            
        String uploadPath = paramMap.get( "uploadpath" )+"";
        String fileCstdyNm = URLDecoder.decode( (String)paramMap.get( "atchFileCstdyNm" ), "UTF-8"); //첨부파일 보관 경로
        
        File file = new File( uploadPath + File.separator + fileCstdyNm );
        
        FileInputStream ifo = null;
        ByteArrayOutputStream baos = null;
        OutputStream out = null;
        
        try {
            ifo = new FileInputStream( file );
            baos = new ByteArrayOutputStream();
            
            byte[] buf = new byte[1024];
            int readlength = 0;
            while( (readlength =ifo.read(buf)) != -1 ) {
                baos.write(buf,0,readlength);
            }
            
            byte[] imgbuf = null;
            imgbuf = baos.toByteArray();
            
            baos.close();
            ifo.close();
            
            int length = imgbuf.length;   
            out = response.getOutputStream();
            out.write(imgbuf , 0, length);
            
            out.close(); 
            
        } catch (Exception ex) {
            baos.close();
            ifo.close(); 
            out.close(); 
        }
    }
    
    /**
     * 지정된 파일을 다운로드.
     * 
     * @param request
     * @param response
     * @param file 다운로드할 파일
     * @throws ServletException
     * @throws IOException
     */
    public void download( HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap)
        throws Exception {
        
        String uploadPath = paramMap.get( "uploadpath" )+"";
        
        String fileCstdyNm = URLDecoder.decode( (String)paramMap.get( "atchFileCstdyNm" ), "UTF-8"); //첨부파일 보관 경로
        String fileOrgnNm  = URLDecoder.decode( (String)paramMap.get( "atchFileOrgnNm" ), "UTF-8");   //원본 파일명
        
        File file = new File( uploadPath + File.separator + fileCstdyNm );
        
        String mimetype = request.getSession().getServletContext().getMimeType( fileOrgnNm );
        
        System.out.println( "file == null ***** " + (file == null));
        System.out.println( "!file.exists() ***** " + (!file.exists()));
        System.out.println( "file.length() ***** " + (file.length()));
        System.out.println( "file.isDirectory() ***** " + (file.isDirectory()));
        
        if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
          throw new IOException( "파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리 입니다." );
        }
        
        InputStream is = null;
        
        try {
          is = new FileInputStream( file );
          download( request, response, is, fileOrgnNm, file.length(), mimetype );
          
        } finally {
          try {
            is.close();
          } catch (IOException ex) {
          } catch (Exception ex) {
          }
        }
    }
    
    /**
     * 해당 입력 스트림으로부터 오는 데이터를 다운로드.
     * 
     * @param request
     * @param response
     * @param is
     *            입력 스트림
     * @param filename
     *            파일 이름
     * @param filesize
     *            파일 크기
     * @param mimetype
     *            MIME 타입 지정
     * @throws ServletException
     * @throws IOException
     */
    public void download(HttpServletRequest request, HttpServletResponse response, InputStream is,
        String filename, long filesize, String mimetype) throws Exception, IOException {
          
        String mime = mimetype;
        
        if ( mimetype == null || mimetype.length() == 0 ) {
            mime = "application/octet-stream;";
        }
        response.setContentType( mime + "; charset=" + CHARSET );
        
        String userAgent = request.getHeader( "User-Agent" );
        byte[] buffer = new byte[ BUFFER_SIZE ];
        
        if (userAgent != null && userAgent.indexOf( "MSIE 5.5" ) > -1) { // MS IE 5.5 이하
            response.setHeader( "Content-Disposition", "filename=" + URLEncoder.encode( filename, "UTF-8" ) + ";" );
          
        } else if ( userAgent != null && userAgent.indexOf( "MSIE" ) > -1 ) { // MS IE (보통은 6.x 이상 가정)
            response.setHeader( "Content-Disposition", "attachment; filename="
                + java.net.URLEncoder.encode( filename, "UTF-8" ) + ";" );
          
        } else { //chrome, 모질라, 오페라
            response.setHeader( "Content-Disposition", "attachment; filename="
                + URLEncoder.encode( new String( filename.getBytes( CHARSET ), "UTF-8" ), "UTF-8" ) + ";" );
        }
        
        if ( filesize > 0 ) {
            response.setHeader( "Content-Length", "" + filesize );
        }
        
        BufferedInputStream fin = null;
        BufferedOutputStream outs = null;
        
        try {
          fin = new BufferedInputStream(is);
          outs = new BufferedOutputStream( response.getOutputStream() );
          int read = 0;
        
          while ( ( read = fin.read( buffer ) ) != -1 ) {
              outs.write( buffer, 0, read );
          }
          
          outs.flush();
          response.flushBuffer();
          
        } catch (IOException ex) {
        } catch (Exception ex) {
        } finally {
            try {
                outs.close();
            } catch (IOException ex) {
            } catch (Exception ex1) {}
            
            try {
                fin.close();
            } catch (IOException ex) {
            } catch (Exception ex2) {}
        } // end of try/catch
    }
    
    /**
     * upload file remove for error.
     * @param request
     * @param response
     * @param paramMap
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void uploadFileDelete(Map<String, Object> paramMap) throws Exception {
        String uploadPath = paramMap.get("uploadpath")+"";
        if ("".equals(uploadPath)) {
            return;
        }

        List<HashMap<String, Object>> upFileList = (List<HashMap<String, Object>>) paramMap.get("uploadFileList");

        if (upFileList != null && upFileList.size() > 0) {
            HashMap<String, Object> upFileMap = null;

            String filePath = "";
            String fileCnvrFilePath = "";

            for (int i = 0; i < upFileList.size(); i++) {
                upFileMap = (HashMap<String, Object>)upFileList.get(i);

                filePath = upFileMap.get("filePathNm")+"";        //file Path
                fileCnvrFilePath = upFileMap.get("cnvrFilePath")+"";  //file Saved name.

                if (fileCnvrFilePath != null && !"".equals(fileCnvrFilePath)) {
                    filePath = filePath.replaceAll("[../]", "");
                    fileCnvrFilePath = fileCnvrFilePath.replaceAll("[../]", "");

                    File file = new File(uploadPath + File.separator + filePath + File.separator + fileCnvrFilePath);
                    file.delete();
                }
            }
        }
        
    }
    
    /**
     * Add MULTIPART Upload Files Info.
     * 	deleteFileInfo : Handle files targeted for deletion id
     */
    public void uploadFileConfig(HttpServletRequest request, ParamMap collector) throws Exception {
        String contentType = (String) request.getContentType();
        String reqUri = (String) request.getRequestURI();  
        
        logger.debug("==================== FileUtil.uploadFileConfig ====================");
        logger.debug("reqUri ==================== {}", reqUri);
        logger.debug("contentType ==================== {}", contentType);
        
        if (contentType == null || contentType.indexOf("multipart/form-data") == -1) {
        	return;
        }
        	
        /**
         * 1. Set file Path.
         */
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        
        String fileUploadPath = "";
        String detailPath = "default";
        
        String fileClsfCd = ""; //첨부 파일 분류 코드
        if (reqUri != null && !"".equals(reqUri))  {
            fileClsfCd = reqUri.split("\\/")[1];
        }
        if (fileClsfCd != null && !"".equals(fileClsfCd)) {
            detailPath = fileClsfCd;
        }
        fileUploadPath = detailPath + File.separator + year + File.separator + month;
        
        /**
         * 2. Set remove file data.
         */
        List<FileDeleteDto> delFileList = new ArrayList<FileDeleteDto>();
        String deleteFileInfo = (String)request.getParameter("deleteFileInfo");
        
        if (deleteFileInfo != null && !"".equals(deleteFileInfo)) {
        	
            String[] delfileId = deleteFileInfo.split(",");
            FileDeleteDto delFileDto = new FileDeleteDto();
            for (int i = 0; i < delfileId.length; i++) {
                
                delFileDto.setFileId(delfileId[i]);
                delFileDto.setFilePath(uploadPath + File.separator + fileUploadPath + File.separator + delfileId[i]);
                delFileList.add(delFileDto);
            }
        }
        
        List<FileUploadDto> upFileList = new ArrayList<FileUploadDto>();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames(); 
        MultipartFile multipartFile = null; 
        
        FileUploadDto upFileDto = null;
        String sourceFileName = "";
        String sourceExtension = "";
        String fileId = "";
        String errMsg = "";
        
        /**
         * 3. Set upload file data.
         */
        while(iterator.hasNext()) { 
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            
            if (!multipartFile.isEmpty()) { 
                sourceFileName = multipartFile.getOriginalFilename();
                sourceExtension = FilenameUtils.getExtension(sourceFileName);
                
                //file allow extension check.
                errMsg = allowCheckExtension(sourceExtension);
                if (!"".equals(errMsg)) {
                    collector.put("errMsg", errMsg);
                    break;
                }
                
                fileId = UUID.randomUUID().toString().replace( "-", "" );

                upFileDto = new FileUploadDto();
                upFileDto.setFileObj(multipartFile);
                upFileDto.setFileOrgnNm(sourceFileName);
                upFileDto.setFileExtension(sourceExtension);
                upFileDto.setFileId(fileId);
                upFileDto.setFileSize(multipartFile.getSize());
                upFileDto.setFileUploadPath(fileUploadPath);
                upFileDto.setFileClsfCd(fileClsfCd);
                upFileDto.setFileCstdyPath(fileUploadPath + File.separator + fileId);
                
                fileUploadPath = uploadPath + File.separator + fileUploadPath;
                
                File fileDir = new File(fileUploadPath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                
                Path path = Paths.get(fileUploadPath + File.separator + sourceFileName);
                Files.write(path, multipartFile.getBytes());
                
                //file name change.
                File file = new File(fileUploadPath + File.separator + sourceFileName);
                File fileToMove = new File(fileUploadPath + File.separator + fileId);
                FileUtils.moveFile(file, fileToMove);

                logger.info("------------- file info start -------------"); 
                upFileDto.printData();
                logger.info("-------------- file info end --------------\n"); 
            } 
            
            upFileList.add(upFileDto);
        }
        
        collector.put("fileList", new FileDto(upFileList, delFileList)); //file Object.
    }
    
    /**
     * upload file extension check.
     * @param extension
     * @param upFileMap
     * @return
     */
    private String allowCheckExtension(String extension) {
        String errMsg = "";
        
        if (extension == null || "".equals(extension)) {
            errMsg = "ERR_EXTENTION";
        }
        
        if (allowExtention != null && !"".equals(allowExtention)) {
        	allowExtention = allowExtention.toLowerCase();
        	extension = extension.toLowerCase();
        	if (allowExtention.indexOf(extension) == -1) {
        		errMsg = "ERR_EXTENTION";
        		logger.debug("extention: .{}, \t allowExtention Exception: {}", extension, allowExtention);
        	}
        }
        return errMsg;
    }
}