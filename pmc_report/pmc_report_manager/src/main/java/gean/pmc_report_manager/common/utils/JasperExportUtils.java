package gean.pmc_report_manager.common.utils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * 导出工具类
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class JasperExportUtils {
	
	 public static final String PRINT_TYPE = "print";
	 public static final String PDF_TYPE = "pdf";
	 public static final String EXCEL_TYPE = "excel";
	 public static final String HTML_TYPE = "html";
	 public static final String WORD_TYPE = "word";
	 /**
	  * 按照类型导出不同格式文件
	  * 
	  * @param datas
	  *            数据
	  * @param type
	  *            文件类型
	  * @param is
	  *            jasper文件的来源
	  * @param request
	  * @param response
	  */
	 public static void export(Collection datas, String type, InputStream is,
			HttpServletRequest request, HttpServletResponse response,String exportName) {
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
			prepareReport(jasperReport, type);
			JRDataSource ds = new JRBeanCollectionDataSource(datas, false);
			Map parameters = new HashMap();
			if("TA_Detail_报表".equals(exportName)) {
				//for(int i=1;i<38;i++) {
					parameters.put("SUBREPORT_DIR1", "export/");
				//}
					parameters.put("SUBREPORT_DIR2", "export/");
					parameters.put("SUBREPORT_DIR3", "export/");
					parameters.put("SUBREPORT_DIR4", "export/");
					parameters.put("SUBREPORT_DIR5", "export/");
					parameters.put("SUBREPORT_DIR6", "export/");
					parameters.put("SUBREPORT_DIR7", "export/");
					parameters.put("SUBREPORT_DIR8", "export/");
					parameters.put("SUBREPORT_DIR9", "export/");
					parameters.put("SUBREPORT_DIR10", "export/");
					parameters.put("SUBREPORT_DIR11", "export/");
					parameters.put("SUBREPORT_DIR12", "export/");
					parameters.put("SUBREPORT_DIR13", "export/");
					parameters.put("SUBREPORT_DIR14", "export/");
					parameters.put("SUBREPORT_DIR15", "export/");
					parameters.put("SUBREPORT_DIR16", "export/");
					parameters.put("SUBREPORT_DIR17", "export/");
					parameters.put("SUBREPORT_DIR18", "export/");
					parameters.put("SUBREPORT_DIR19", "export/");
					parameters.put("SUBREPORT_DIR20", "export/");
					parameters.put("SUBREPORT_DIR21", "export/");
					parameters.put("SUBREPORT_DIR22", "export/");
					parameters.put("SUBREPORT_DIR23", "export/");
					parameters.put("SUBREPORT_DIR24", "export/");
					parameters.put("SUBREPORT_DIR25", "export/");
					parameters.put("SUBREPORT_DIR26", "export/");
					parameters.put("SUBREPORT_DIR27", "export/");
					parameters.put("SUBREPORT_DIR28", "export/");
					parameters.put("SUBREPORT_DIR29", "export/");
					parameters.put("SUBREPORT_DIR30", "export/");
						parameters.put("SUBREPORT_DIR31", "export/");
						parameters.put("SUBREPORT_DIR32", "export/");
						parameters.put("SUBREPORT_DIR33", "export/");
						parameters.put("SUBREPORT_DIR34", "export/");
						parameters.put("SUBREPORT_DIR35", "export/");
						parameters.put("SUBREPORT_DIR36", "export/");
						parameters.put("SUBREPORT_DIR37", "export/");
						parameters.put("SUBREPORT_DIR38", "export/");
				
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

			if (EXCEL_TYPE.equals(type)) {
				exportExcel(jasperPrint, request, response,exportName);
			} else if (PDF_TYPE.equals(type)) {
				exportPdf(jasperPrint, request, response,exportName);
			} else if (HTML_TYPE.equals(type)) {
				exportHtml(jasperPrint, request, response);
			} else if (WORD_TYPE.equals(type)) {
				exportWord(jasperPrint, request, response,exportName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void prepareReport(JasperReport jasperReport, String type) {
		if ("excel".equals(type)) {
			try {
				Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);
				margin = JRBaseReport.class.getDeclaredField("topMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);
				margin = JRBaseReport.class.getDeclaredField("bottomMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);
				Field pageHeight = JRBaseReport.class.getDeclaredField("pageHeight");
				pageHeight.setAccessible(true);
				pageHeight.setInt(jasperReport, 2147483647);
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	/**
	 * 导出excel
	 * @param jasperPrint
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	public static void exportExcel(JasperPrint jasperPrint,HttpServletRequest request,HttpServletResponse response,String exportName) throws IOException,JRException{
		
	/**
	 * 设置头信息	
	 */
		response.setContentType("application/vnd.ms-excel");
		String fileName = new String((exportName+".xls").getBytes("GBK"),"ISO8859_1");
		response.setHeader("Content-disposition" , "attachment; filename="+ fileName);
		ServletOutputStream outputStream = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outputStream);
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,bos);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			System.out.println("开始写文件....");
			long startTime = System.currentTimeMillis();
			exporter.exportReport();
			long endTime = System.currentTimeMillis();
	    	long usedTime = (endTime - startTime)/1000;
			System.out.println("写文件结束，耗时："+usedTime+" 秒");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bos.flush();
				bos.close();
				outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	 /**
	  * 导出pdf，注意此处中文问题， 1）在ireport的classpath中加入iTextAsian.jar
	  * 2）在ireport画jrxml时，pdf font name ：STSong-Light ，pdf encoding ：
	  * UniGB-UCS2-H
	  */
	 public static void exportPdf(JasperPrint jasperPrint,
	   HttpServletRequest request, HttpServletResponse response,String exportName) throws IOException, JRException {
	   response.setContentType("application/pdf");
	   String fileName = new String((exportName+".pdf").getBytes("utf-8"), "ISO8859_1");
	   response.setHeader("Content-disposition", "attachment; filename="
	     + fileName);
	   ServletOutputStream ouputStream = response.getOutputStream();
	   JasperExportManager.exportReportToPdfStream(jasperPrint,ouputStream);
	   ouputStream.flush();
	   ouputStream.close();
	 }
	 
	 /**
	  * 导出html
	  */
	 public static void exportHtml(JasperPrint jasperPrint,
	   HttpServletRequest request, HttpServletResponse response) throws IOException, JRException {
	   response.setContentType("text/html");
	   ServletOutputStream ouputStream = response.getOutputStream();
	   JRHtmlExporter exporter = new JRHtmlExporter();
	   exporter.setParameter(
	     JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
	     Boolean.FALSE);
	   exporter
	     .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	   exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
	     "UTF-8");
	   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
	     ouputStream);

	   exporter.exportReport();

	   ouputStream.flush();
	   ouputStream.close();
	 }
	 
	 /**
	  * 导出word
	  */
	public static void exportWord(JasperPrint jasperPrint,
	   HttpServletRequest request, HttpServletResponse response,String exportName) throws JRException, IOException {
	  response.setContentType("application/msword;charset=utf-8");
	  String fileName = new String((exportName+".doc").getBytes("GBK"), "ISO8859_1");
	  response.setHeader("Content-disposition", "attachment; filename="
	    + fileName);
	  if("OPR报表".equals(exportName)) {
		  JRExporter exporter = new JRRtfExporter();
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
		    .getOutputStream());

		  exporter.exportReport();
	  }else {
		  JRAbstractExporter exporter = new JRDocxExporter();
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
		    .getOutputStream());

		  exporter.exportReport();
	  }  
	 
	 }
	
	 /**
	  * 打印
	  */
	 public static void exportPrint(JasperPrint jasperPrint,
	   HttpServletResponse response, HttpServletRequest request) throws IOException {
	  response.setContentType("application/octet-stream");
	  ServletOutputStream ouputStream = response.getOutputStream();
	  ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
	  oos.writeObject(jasperPrint);
	  oos.flush();
	  oos.close();
	  ouputStream.flush();
	  ouputStream.close();
	 }
}
