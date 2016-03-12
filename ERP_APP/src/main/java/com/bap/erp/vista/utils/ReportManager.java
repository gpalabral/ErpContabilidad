package com.bap.erp.vista.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.apache.log4j.Logger;

public class ReportManager {

    private static org.apache.log4j.Logger log = Logger.getLogger(ReportManager.class);
    private final String REPORT_PATH = "/WEB-INF/reportes/";
    private transient Map<String, Object> reportParam;
    private String formato;

    public void drawReport(FacesContext context, List coleccion, final String reportName) throws Exception {
        log.info("::::::::::::::::ReportPhaseListener.drawReport::::::::::::::::::::::");

        if (formato == null) {
            formato = "PDF";
            //throw new Exception("Debe establecer el formato del reporte.");
        }

        if (formato.equals("HTML")) {
            exportHTML(context, coleccion, reportName);
        } else if (formato.equals("PDF")) {
            exportPDF(context, coleccion, reportName);
        }
    }

    private void exportHTML(FacesContext context, List coleccion, final String reportName) {
        ExternalContext externalContext = context.getExternalContext();

        InputStream stream = null;
        JRExporter exporter = new JRXhtmlExporter();
        JasperPrint jasperPrint = null;

        try {

            if (reportParam == null) {
                throw new Exception("Parametros del reporte no iniciados......");
            }

            String path = REPORT_PATH + reportName + ".jasper";
            stream = externalContext.getResourceAsStream(path);

            String realPath = externalContext.getResource(REPORT_PATH).getPath();
            reportParam.put("REPORT_PATH", realPath);



            log.debug("PARAMETROS ENVIADOS:::: " + reportParam);

            if (stream == null) {
                throw new Exception("Reporte no encontrado:::::::::::::::: " + reportName);
            }


            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(coleccion);


            jasperPrint = JasperFillManager.fillReport(stream, reportParam, ds);

            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("text/html");
            response.setHeader("control-cache", "no-cache");
            response.setHeader("Content-Disposition", "inline;filename=Report.html");

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
            //Muestra las imagenes para la salida HTML
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, new HashMap());
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/image?image=");
            exporter.exportReport();
            stream.close();

            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stream = null;
        exporter = null;
        jasperPrint = null;
    }

    private void exportPDF(FacesContext context, List coleccion, final String reportName) {
        ExternalContext externalContext = context.getExternalContext();

        InputStream stream = null;
        JRExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = null;

        try {

            if (reportParam == null) {
                throw new Exception("Parametros del reporte no iniciados......");
            }

            String path = REPORT_PATH + reportName + ".jasper";            
            stream = externalContext.getResourceAsStream(path);

            //String realPath = externalContext.getResource(REPORT_PATH).getPath();
            String realPath = externalContext.getRealPath(REPORT_PATH) + File.separatorChar;            
            reportParam.put("REPORT_PATH", realPath);

            reportParam.put("formato", formato);


            log.debug("PARAMETROS ENVIADOS:::: " + reportParam);

            if (stream == null) {
                throw new Exception("Reporte no encontrado:::::::::::::::: " + reportName);
            }


            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(coleccion);


            jasperPrint = JasperFillManager.fillReport(stream, reportParam, ds);

            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("application/pdf");
            response.setHeader("content-type", "application/pdf");
            response.setHeader("Transfer-Encoding", "chunked");
            response.setHeader("Content-Disposition", "inline;filename=Report.pdf");

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());

            exporter.exportReport();
            stream.close();

            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stream = null;
        exporter = null;
        jasperPrint = null;
    }

    public void drawReportStickers(FacesContext context, List coleccion, final String reportName, final String qrName) throws Exception {
        if (formato == null) {
            formato = "PDF";
            //throw new Exception("Debe establecer el formato del reporte.");
        }

        if (formato.equals("HTML")) {
            exportHTML(context, coleccion, reportName);
        } else if (formato.equals("PDF")) {
            exportPDFStickers(context, coleccion, reportName, qrName);
        }
    }

    private void exportPDFStickers(FacesContext context, List coleccion, final String reportName, final String qrName) {
        ExternalContext externalContext = context.getExternalContext();
        InputStream stream = null;
        JRExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = null;
        try {

            if (reportParam == null) {
                throw new Exception("Parametros del reporte no iniciados......");
            }
            String path = REPORT_PATH + reportName + ".jasper";
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String qrPath = (String) servletContext.getRealPath("/WEB-INF/uploaded/");
            String pathQr = qrPath+File.separator + qrName+".jpg";
            stream = externalContext.getResourceAsStream(path);
            String realPath = externalContext.getRealPath(REPORT_PATH) + File.separatorChar;            
            reportParam.put("REPORT_PATH", realPath);
            reportParam.put("QR_PATH", pathQr);
            reportParam.put("formato", formato);


            log.debug("PARAMETROS ENVIADOS:::: " + reportParam);

            if (stream == null) {
                throw new Exception("Reporte no encontrado:::::::::::::::: " + reportName);
            }


            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(coleccion);


            jasperPrint = JasperFillManager.fillReport(stream, reportParam, ds);

            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("application/pdf");
            response.setHeader("content-type", "application/pdf");
            response.setHeader("Transfer-Encoding", "chunked");
            response.setHeader("Content-Disposition", "inline;filename=Report.pdf");

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());

            exporter.exportReport();
            stream.close();

            context.renderResponse();
            context.responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stream = null;
        exporter = null;
        jasperPrint = null;
    }

    public void setReportParam(Map<String, Object> reportParam) {
        this.reportParam = reportParam;
    }

    public Map<String, Object> getReportParam() {
        return reportParam;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFormato() {
        return formato;
    }
}
