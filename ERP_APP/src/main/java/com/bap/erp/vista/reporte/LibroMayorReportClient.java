/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.reporte;

//import com.bap.cpanel.entities.AdmRoles;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author gus
 */
public class LibroMayorReportClient {

    public static void main(String args[]) {

//        ApplicationContext context = new FileSystemXmlApplicationContext("//Users//gus//Documents//GUS//BAP//ERP//Codigo//Version12032014//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//PROYECTO_ERP//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");

        try {


            CntComprobantesService cntComprobantesService = (CntComprobantesService) context.getBean("cntComprobantesService");
            CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
//            List<CntComprobante> listaDeComprobantesPorParametros = cntComprobantesService.listaComprobantesEnUnRango(1L, 500L, "3", "2014", "EGRE");
            CntComprobante compro = cntComprobantesService.find(CntComprobante.class, 7326L);
            System.out.println("...comprobante es..-->"+compro);
            List<CntDetalleComprobante> listaDeDetalles = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(compro);

//            System.out.println("Size:: " + listaDeComprobantesPorParametros.size());
            System.out.println("Size:: " + listaDeDetalles.size());

            Map parameters = new HashMap();

//            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDeComprobantesPorParametros);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(listaDeDetalles);

//            String path = "//Users//gus//Documents//GUS//BAP//ERP//Codigo//Version12032014//ERP_APP//src//main//webapp//WEB-INF//reportes//";
            String path = "//E://PROYECTO_ERP//ERP_APP//target//ERP_APP-1.0//WEB-INF//Reportes//";
            String htmFile = null;


            htmFile = JasperRunManager.runReportToPdfFile(path + "reporte_comprobantes.jasper", parameters, ds);
            System.out.println("pdf=" + htmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        

    }
}
