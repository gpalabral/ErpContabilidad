package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntConfiguracionCentroCostoCliente {

    public static void main(String args[]) throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntComprobantesService cntComprobantesService = (CntComprobantesService) context.getBean("cntComprobantesService");
        CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
        CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService = (CntConfiguracionCentroCostoService) context.getBean("cntConfiguracionCentroCostoService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");
        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");

//        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 308L);
//        List<CntConfiguracionCentrocosto> lista = cntConfiguracionCentroCostoService.listaCentrosDeCostoPorDefinicion(cuenta);

//        List<CntConfiguracionCentrocosto> lista = cntConfiguracionCentroCostoService.listaCentrosDeCostoAlternativa("1");
//        
//        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : lista) {
//            System.out.println("Los centros son:::"+cntConfiguracionCentrocosto.getIdConfiguracionCentrocosto());
//        }

//                CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 4130L);

//        List<CntConfiguracionCentrocosto>lista=cntConfiguracionCentroCostoService.listaConbinadaCentrosDeCostoConConfiguracionDeCentrosDeCostoList(cntDetalleComprobante);

        ParRecetasDistribucionCentroCosto recetasDistribucionCentroCosto = (ParRecetasDistribucionCentroCosto) parParametricasService.find(ParRecetasDistribucionCentroCosto.class, "1");
        System.out.println("CODIGO HENRRY:"+recetasDistribucionCentroCosto.getCodigo());
        List<CntConfiguracionCentrocosto> lista = cntConfiguracionCentroCostoService.cargaListadoConfiguracionParaRecetasDesdeUnPlanCuentas(recetasDistribucionCentroCosto);

        for (CntConfiguracionCentrocosto ccc : lista) {
            System.out.println("Codigo:" + ccc.getIdCentroCosto().getMascaraGenerada() + "CC:" + ccc.getIdCentroCosto().getDescripcion());
        }
        

    }
}
