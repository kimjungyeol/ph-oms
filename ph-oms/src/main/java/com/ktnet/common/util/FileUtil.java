package com.ktnet.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ktnet.common.dto.FileDto;
import com.ktnet.common.dto.FileUploadDto;
import com.ktnet.common.exception.BizzException;
import com.ktnet.core.map.ParamMap;
import com.ktnet.fta.common.mapper.FileMapper;

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

	@Autowired
	private FileMapper fileMapper;

	@Value("${common.file.uploadPath}")
	private String uploadPath;

	@Value("${common.file.allowExtention}")
	private String allowExtention;

	private FileUtil() {
	}

	/**
	 * image view in HTML.
	 * 
	 * @param request
	 * @param response
	 * @param paramMap
	 * @throws Exception
	 */
	public void fileView(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap)
			throws Exception {
		
		String fileNm = paramMap.get("fileNm")+""; 
		String fileUploadPath = paramMap.get("fileUploadPath")+"";

		File file = new File(uploadPath + File.separator + fileUploadPath + File.separator + fileNm);

		FileInputStream ifo = null;
		ByteArrayOutputStream baos = null;
		OutputStream out = null;

		try {
			ifo = new FileInputStream(file);
			baos = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];
			int readlength = 0;
			while ((readlength = ifo.read(buf)) != -1) {
				baos.write(buf, 0, readlength);
			}

			byte[] imgbuf = null;
			imgbuf = baos.toByteArray();

			baos.close();
			ifo.close();

			int length = imgbuf.length;
			out = response.getOutputStream();
			out.write(imgbuf, 0, length);

			out.close();

		} catch (IOException ex) {
		} catch (Exception ex) {
		} finally {
			if (baos != null) { baos.close(); }
			if (ifo != null) { ifo.close(); }
			if (out != null) { out.close(); }
		}
	}

	/**
	 * file download.
	 *  - check file info.
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @throws ServletException
	 * @throws IOException
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap)
			throws Exception {

		String fileNm = paramMap.get("fileNm")+""; 
		String fileUploadPath = paramMap.get("fileUploadPath")+"";
		String fileOrignNm = paramMap.get("fileOrignNm")+"";

		File file = new File(uploadPath + File.separator + fileUploadPath + File.separator + fileNm);

		String mimetype = request.getSession().getServletContext().getMimeType(fileOrignNm);

//		System.out.println("file == null ***** " + (file == null));
//		System.out.println("!file.exists() ***** " + (!file.exists()));
//		System.out.println("file.length() ***** " + (file.length()));
//		System.out.println("file.isDirectory() ***** " + (file.isDirectory()));

		if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
			throw new IOException("The file object is null, does not exist, has a length of 0, or is a directory that is not a file.");
		}

		InputStream is = null;

		try {
			is = new FileInputStream(file);
			download(request, response, is, fileOrignNm, file.length(), mimetype);
		} catch (IOException ex) {
		} catch (Exception ex) {
		} finally {
			if (is != null) { is.close(); }
		}
	}

	/**
	 * file download.
	 *   - flush file stream.
	 * 
	 * @param request
	 * @param response
	 * @param is
	 * @param filename
	 * @param filesize
	 * @param mimetype
	 * @throws ServletException
	 * @throws IOException
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, InputStream is, String filename,
			long filesize, String mimetype) throws Exception, IOException {

		String mime = mimetype;

		if (mimetype == null || mimetype.length() == 0) {
			mime = "application/octet-stream;";
		}
		response.setContentType(mime + "; charset=" + CHARSET);

		String userAgent = request.getHeader("User-Agent");
		byte[] buffer = new byte[BUFFER_SIZE];

		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MSIE (<= 5.5 version) 
			response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");

		} else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MSIE (>= 6.x version)
			response.setHeader("Content-Disposition",
					"attachment; filename=" + URLEncoder.encode(filename, "UTF-8") + ";");

		} else { // CHROME, MOZLIA, OPERA, EDGE
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ URLEncoder.encode(new String(filename.getBytes(CHARSET), "UTF-8"), "UTF-8") + ";");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ URLEncoder.encode(filename, "UTF-8") + ";");
		}

		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;

			while ((read = fin.read(buffer)) != -1) {
				outs.write(buffer, 0, read);
			}

			outs.flush();
			response.flushBuffer();

		} catch (IOException ex) {
		} catch (Exception ex) {
		} finally {
			if (outs != null) { outs.close(); }
			if (fin != null) { fin.close(); }
		} // end of try/catch
	}

	/**
	 * upload file remove for error.
	 * 
	 * @param request
	 * @param response
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void uploadFileDelete(Map<String, Object> paramMap) throws Exception {
		String uploadPath = paramMap.get("uploadpath") + "";
		if ("".equals(uploadPath)) {
			return;
		}

		List<HashMap<String, Object>> upFileList = (List<HashMap<String, Object>>) paramMap.get("uploadFileList");

		if (upFileList != null && upFileList.size() > 0) {
			HashMap<String, Object> upFileMap = null;

			String filePath = "";
			String fileCnvrFilePath = "";

			for (int i = 0; i < upFileList.size(); i++) {
				upFileMap = (HashMap<String, Object>) upFileList.get(i);

				filePath = upFileMap.get("filePathNm") + ""; // file Path
				fileCnvrFilePath = upFileMap.get("cnvrFilePath") + ""; // file Saved name.

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
	 * Add MULTIPART Upload Files Info. deleteFileInfo : Handle files targeted for
	 * deletion id
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

		Map<String, Object> paramMap = collector.getMap();

		/**
		 * 1. Set file Path.
		 */
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		String fileUploadPath = "";
		String detailPath = "default";

		String fileClsfCd = ""; // 첨부 파일 분류 코드
		if (reqUri != null && !"".equals(reqUri)) {
			fileClsfCd = reqUri.split("\\/")[1];
		}
		if (fileClsfCd != null && !"".equals(fileClsfCd)) {
			detailPath = fileClsfCd;
		}
		fileUploadPath = detailPath + File.separator + year + File.separator + month;

		Map<String, Object> fileDtoMap = new HashMap<String, Object>();
		List<FileUploadDto> upFileList = null;

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		List<MultipartFile> fileList = null;

		FileUploadDto upFileDto = null;
		String sourceFileName = "";
		String sourceExtension = "";
		String errMsg = "";
		String fileId = "";
		String atchFileId = "";
		String atchFileNm = "";

		/**
		 * 2. Set upload file data.
		 */
		while (iterator.hasNext()) {
			fileId = iterator.next();
			fileList = multipartHttpServletRequest.getFiles(fileId);
			
			fileId = fileId.replace("[]", "");

			// saved fileId.
			atchFileId = paramMap.get(fileId) == null ? "" : (String) paramMap.get(fileId);
			int fileSn = 1;
			if ("".equals(atchFileId)) {
				atchFileId = UUID.randomUUID().toString();
			} else {
				fileSn = fileMapper.selectAttachFileSn(atchFileId);
			}

			upFileList = new ArrayList<FileUploadDto>();

			if (!fileList.isEmpty()) {
				for (MultipartFile multipartFile : fileList) {
					sourceFileName = multipartFile.getOriginalFilename();
					sourceExtension = FilenameUtils.getExtension(sourceFileName);

					// 2.1 file allow extension check.
					errMsg = allowCheckExtension(sourceExtension);
					if (!"".equals(errMsg)) {
						throw new BizzException(errMsg);
					}
					
					atchFileNm = UUID.randomUUID().toString();

					// 2.2 set file info.
					upFileDto = new FileUploadDto();
					upFileDto.setFileObj(multipartFile);
					upFileDto.setFileId(atchFileId);
					upFileDto.setFileSn(fileSn++);
 					upFileDto.setFileNm(atchFileNm);   		   //uploaded file name.
					upFileDto.setFileOrignNm(sourceFileName);  //origin file name.
					upFileDto.setFileExtension(sourceExtension);
					upFileDto.setFileSize(multipartFile.getSize());
					upFileDto.setFileUploadPath(fileUploadPath);
					upFileDto.setFileClsfCd(fileClsfCd);

					String fileDirPath = uploadPath + File.separator + fileUploadPath;

					File fileDir = new File(fileDirPath);
					if (!fileDir.exists()) {
						fileDir.mkdirs();
					}

					// 2.3 real file upload.
					Path path = Paths.get(fileDirPath + File.separator + sourceFileName);
					Files.write(path, multipartFile.getBytes());

					// 2.4 uploaded file name change.
					File file = new File(fileDirPath + File.separator + sourceFileName);
					File fileToMove = new File(fileDirPath + File.separator + atchFileNm);
					FileUtils.moveFile(file, fileToMove);

					logger.info("------------- file info start -------------");
					upFileDto.printData();
					logger.info("-------------- file info end --------------\n");

					// 2.5 uploaded file Object add.
					upFileList.add(upFileDto);
				} // for
			}

			// 2.6 append DTO file Object.
			fileDtoMap.put(fileId, new FileDto(upFileList));
		} // while

		// 3. append Map All DTO File Object.
		collector.put("fileDtoMap", fileDtoMap); // file Object.
	}

	/**
	 * upload file extension check.
	 * 
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

	/**
	 * upload file remove for error.
	 * 
	 * @param request
	 * @param response
	 * @param paramMap
	 * @throws Exception
	 */
	public void uploadFileDeleteForError(String uploadpath, List<Map<String, Object>> upFileList) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uploadpath", uploadpath);
		paramMap.put("uploadFileList", upFileList);
		uploadFileDelete(paramMap);
	}
}