package com.ktnet.core.jasper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.ktnet.common.dto.JasperParamDto;
import com.ktnet.common.util.CommonUtil;
import com.ktnet.common.util.MimeType;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Component
public class JasperPDFViewer extends AbstractView {
	private Logger logger = LoggerFactory.getLogger(JasperPDFViewer.class);
	public final static String SUBDS_KEY = "SUBDS01";

	public JasperPDFViewer() {
		super.setContentType(MimeType.getMimeType("pdf"));
	}
	
	public void getJasperView(Map<String, Object> pMap, String reportFileName, HttpServletRequest req, HttpServletResponse res) throws Exception {
        List<Map<String, Object>> jList = new ArrayList<Map<String,Object>>();
        jList.add(pMap);
        
        JasperParamDto jParam = JasperUtils.convertKey(reportFileName+".jrxml", reportFileName+".pdf");
        jParam.setList(jList);
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ViewerSupport.PARAM_KEY, jParam);
		
		renderMergedOutputModel(map, req, res);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JasperParamDto jParam = (JasperParamDto) map.get(ViewerSupport.PARAM_KEY);
		
		List<Map<String, Object>> jasList = jParam.getList();
		if (jasList == null || (jasList != null && jasList.size() == 0)) {
			return;
		}
		Map<String, Object> jMap = jasList.get(0);
		
		String jasperPath = CommonUtil.getCommonProperty("jasper.path") + "/";
		String imagePath = CommonUtil.getCommonProperty("jasper.imagePath") + "/";
		
		URL jasperUrl = this.getClass().getResource(jasperPath);
		URL imgUrl = this.getClass().getResource(imagePath);
		
		jMap.put("upImagePath", imgUrl.getPath());
		jMap.put("subreportDir", jasperUrl.getPath());
		
		URL url = this.getClass().getResource(jasperPath + jParam.getJasperFileName());
		JasperReport jasperReport = getCompiledReport(url.getPath());
		ViewDataSource primaryDatasource = new ViewDataSource(jParam.getList());
		
		byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, jMap, primaryDatasource);

		if (bytes != null && bytes.length > 0) {
			response.setContentType(super.getContentType());
			response.setContentLength(bytes.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + jParam.getPrintFileName() + "\"");
			response.setHeader("Content-Transfer-Encoding", "binary");
			ServletOutputStream ouputStream = response.getOutputStream();
			try {
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
			} catch(NullPointerException e) {
				logger.debug("renderMergedOutputModel ERROR!!");
			} catch(IOException e) {
				logger.debug("renderMergedOutputModel ERROR!!");
			} catch(Exception e) {
				logger.debug("renderMergedOutputModel ERROR!!");
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException ex) {
					}
				}
			}
		}
	}

	private JasperReport getCompiledReport(String fileName) throws JRException, IOException {
		logger.debug("Jasper Design File is : {}", fileName);

		JasperDesign jasDesign = JRXmlLoader.load(fileName);
		JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);

		return jasReport;
	}
}
