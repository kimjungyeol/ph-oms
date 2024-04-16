package com.ktnet.fta.common.web;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ktnet.common.dto.ResultResponseDto;
import com.ktnet.common.util.FileUtil;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.service.FileService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileController extends BasicController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "fileService")
    private FileService fileService;
	
	@Resource(name = "fileUtil")
	private FileUtil fileUtil;
    
	/**
     * Search file list.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
	 * <pre>
	 *      Date          Person in Charge                  Comment
	 *  ============  =======================    ===========================
	 *  2024. 04. 15.      Jung Yeol KIM                initial version
	 * </pre>
     */
    @PostMapping("/file/attach/search")
    public ResponseEntity<ResultResponseDto> fileListSearch(HttpServletRequest req, ParamMap pMap) throws Exception {
    	logger.info(">> fileListSearch <<");
    	List<Map<String, Object>> list = fileService.searchFileInfoList(pMap.getMap());
    	return new ResponseEntity<>(ResultResponse(list), HttpStatus.OK);
    }
    
    /**
     * File Download.
     * @param req
     * @param res
     * @param pMap
     */
    @RequestMapping(value = "/file/attach/down", method = {RequestMethod.GET, RequestMethod.POST})
    public void fileDown(HttpServletRequest req, HttpServletResponse res, ParamMap pMap) throws Exception  {
        Map<String, Object> paramMap = pMap.getMap();
        
        String fileId = paramMap.get("f") == null ? "" : paramMap.get("f")+"";
        String fileSn = paramMap.get("s") == null ? "" : paramMap.get("s")+"";

        Decoder decoder = Base64.getDecoder();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileId", new String(decoder.decode(fileId)));
        map.put("fileSn", new String(decoder.decode(fileSn)));
        
        try {

            Map<String,Object> fMap = fileService.searchFileInfo(map);
            fileUtil.download(req, res, fMap);

            /**
             * 공통 Log 정보.
             */
            //Map<String, Object> logInfoMap = CommonUtil.getLogInfo( req );
            //logInfoMap.put( "acesFileId", fileId );    //접근FileId

            //파일 다운로드 로그 저장.
            //commonService.saveFileDownLog( logInfoMap );

        } catch(SQLException e) {
        	logger.debug("fileDown SQLException");
        } catch(Exception e) {
        	logger.debug("fileDown Exception");
        }
    }

    /**
     * file view.
     * @param req
     * @param model
     * @param pMap
     * @return
     * @throws Exception
     */
	/*
	 * @PostMapping( value = "/attach/view") public @ResponseBody Map<String,
	 * Object> fileView(HttpServletRequest req, Model model, ParamMap pMap) throws
	 * Exception { Map<String, Object> map = pMap.getMap(); Map<String, Object>
	 * rtMap = new HashMap<String, Object>();
	 * 
	 * try {
	 * 
	 * List<Map<String, Object>> list = cmsCommonService.selectFileInfoTxList( map
	 * ); rtMap.put( EtmConstant.DATALIST, list );
	 * 
	 * } catch(SQLException e) { return responseErrorMap( e, rtMap ); }
	 * catch(Exception e) { return responseErrorMap( e, rtMap ); }
	 * 
	 * return responseSuccessMap( rtMap ); }
	 */
}