package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.commons.entities.mappings.EntidadArbolPojo;
import com.bap.erp.commons.entities.mappings.EntidadPojo;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntMigracionExcelService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntEntidadesCliente {

    //private final static Log log = LogFactory.getLog(CntEntidadesCliente.class);
    public static void main(String args[]) {

        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//CONTA GIT//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\GIT erpContaMerged\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");
        CntMigracionExcelService cntMigracionExcelService = (CntMigracionExcelService) context.getBean("cntMigracionExcelService");
//
//<editor-fold defaultstate="collapsed" desc="otras pruebas">
//        try {
//            List<CntEntidad> list = cntObjetosService.cntEntidadesPorGrupoNivelList("CCOS");
//            for (CntEntidad cntObjetos : list) {
//                System.out.println("gestiones:::" + cntObjetos.getMascaraGenerada());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        CntEntidad entidad = new CntEntidad(14L);
//        entidad.setNivel(3);
//        List<CntEntidad> lista = cntEntidadesService.listaSuperiorGrupoDeObjetoSeleccionado((CntEntidad) cntEntidadesService.find(CntEntidad.class, 14L));
//        for (CntEntidad cntEntidad : lista) {
//            System.out.println("idgerarquia:::" + cntEntidad.getIdEntidad()+"------------------------------");
//            System.out.println("idpadres::::::::::"+ cntEntidad.getIdEntidadPadre()+"--------------------------");
//            System.out.println("descripción_ padres"+cntEntidad.getDescripcion()+"----------------------------");
//            System.out.println("id mascara"+cntEntidad.getCntMascara().getMascara()+"------------------------------");
//            System.out.println("nombre nivel"+cntEntidad.getMascaraGenerada()+"----------------------------------------");
//        }
//        int valor=4;
//        CntNivel cntnivel=cntEntidadesService.obtienedescripcionNivel(valor);
//        System.out.println("valor de descripcion"+valor+"////////");
//            System.out.println("descripcion"+cntnivel.getDescripcion()+"--------------------");
//            System.out.println("idnivel"+cntnivel.getIdNivel()+"------------------------------");
//
//        List<CntEntidad> list = cntObjetosService.listaSuperiorGrupoDeObjetoSeleccionado(co);
//        for (CntEntidad cntObjetos : list) {
//            System.out.println("gestiones:::" + cntObjetos.getMascaraGenerada());
//        }
//        CntEntidad entidad = new CntEntidad(1L);
//        entidad.setNivel(3);
//
//        if (cntEntidadesService.esNivel1oNivel2(entidad)) {
//            System.out.println("es de 1 o2 nivel");
//        } else {
//            System.out.println("no es");
//        }
//        cntEntidadesService.persistMasivoCuentasNivelDosAndUnoBaseMigrada("HENRRY GUZMAN");
//        List<CntEntidad> lista = cntEntidadesService.listaCuentasDeUltimoNivelPorDescripcion("BANCO NA");
//        for (CntEntidad cntEntidad : lista) {
//            System.out.println("las encontradas son:::" + cntEntidad.getDescripcion());
//        }
//        ObjectUtils.printObjectState((CntEntidad)cntEntidadesService.find(CntEntidad.class,1L));
//        System.out.println("NUEVA CUENTA GENERA NUMERO NIVEL:"+cntEntidadesService.generaNumeroSiguienteAutomatico((CntEntidad)cntEntidadesService.find(CntEntidad.class,1L), "N"));
//        System.out.println("NUEVA CUENTA GENERA NUMERO SUB NIVEL:"+cntEntidadesService.generaNumeroSiguienteAutomatico((CntEntidad)cntEntidadesService.find(CntEntidad.class,1L), "S"));
//        List<CntEntidad> lista = cntEntidadesService.listaCuentasParaRetencionesAndGrossingUp();
//
//        for (CntEntidad cntEntidad : lista) {
//            System.out.println("las encontradas son:::" + cntEntidad.getMascaraGenerada());
//        }
//        DecimalFormat formateador = new DecimalFormat("###,###.##");
//        BigDecimal numero = new BigDecimal("3546123015.26");
//
//        System.out.println(formateador.format(numero));
//        CntEntidad prueba=(CntEntidad)cntEntidadesService.find(CntEntidad.class,308L);
//        CntEntidad prueba = (CntEntidad) cntEntidadesService.find(CntEntidad.class, 308L);
//        System.out.println("id de la entidad  " + prueba.getIdEntidad() + "");
//        System.out.println("mascara de la entidad  " + prueba.getMascaraGenerada() + "");
//        System.out.println("la entidad no pertenece a centro de costos  " + cntEntidadesService.verificaEntidadCentroCosto(prueba) + "-----------------------------");
//        try {
//
//            System.out.println("ELEMENTO:" + cntEntidadesService.obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidad(prueba, 1000L));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////
//
//        List<CntConfiguracionCentrocosto> lista=cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(prueba);
//
//        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : lista) {
//            System.out.println("Entidad:"+cntConfiguracionCentrocosto.getIdCentroCosto().getDescripcion()+"  porcentaje:"+cntConfiguracionCentrocosto.getPorcentaje());
//        }
//
//        try {
//            List<CntEntidad> lista = cntEntidadesService.cntEntidadesParaCentrosDeCostoSoloNivelUnoFiltraList();
//            for (CntEntidad cntEntidad : lista) {
//                System.out.println("las encontradas son:::" + cntEntidad.getDescripcion());
//            }
//
//        } catch (Exception e) {
//        }
//        CntEntidad ccHijo = cntEntidadesService.find(CntEntidad.class, 313L);
//        List<CntEntidad> listaDePadre = cntEntidadesService.listaPadresDeCentroDeCosto(ccHijo);
//        System.out.println("la cuenta hija es:::."+ccHijo.getDescripcion());
//        for (CntEntidad cntEntidad : listaDePadre) {
//            System.out.println("id de los padres:::"+cntEntidad.getIdEntidad());
//        }
//        Boolean var=cntEntidadesService.verificaEntidadConCentroCosto(ccHijo);
//        System.out.println("el valor de la variable si posee centro de costo es "+var+"");
//        List<CntEntidad> lista = cntEntidadesService.cntEntidadesPorGrupoNivelList("PCTA");
//        for (CntEntidad cntEntidad : lista) {
//            System.out.println("mascara:::" + cntEntidad.getMascaraGenerada() + " descripcion:::" + cntEntidad.getDescripcion());
//        }
        //
//         CntEntidad prueba=(CntEntidad)cntEntidadesService.find(CntEntidad.class,727L);
//
//        Boolean variable =cntEntidadesService.verificaEntidadPadre(prueba);
//        System.out.println("el valor de la variable es "+variable+"*************");
        //Por Henrry Guzman
//        CntEntidad ccHijo = cntEntidadesService.find(CntEntidad.class, 313L);
//        cntEntidadesService.guardaCuentaGeneral(ccHijo, "COD");
//        CntEntidad cntEntidad1 = cntEntidadesService.find(CntEntidad.class, 1028L);
//        CntEntidad cntEntidad2 = cntEntidadesService.find(CntEntidad.class, 43L);
//        List<ParPlanCuentas> lista = cntEntidadesService.listaLasCuentasEnUnRango(cntEntidad1, cntEntidad2);
////       List<CntEntidad> lista = cntEntidadesService.listaLasCuentasEnUnRangoDeParPlanCuentas(cntEntidad1, cntEntidad2);
//        for (ParPlanCuentas cntEntidad : lista) {
//            System.out.println("id_entidad -------"+cntEntidad.getIdEntidad()+"-------la entidad es:::------- "+cntEntidad.getMascaraGenerada()+"----descripcion------"+cntEntidad.getDescripcion()+"///////////");
//        }
//
        try {
            
            CntEntidad cntEntidad3 = cntEntidadesService.cntEntidadPorMascara("1-4-3-01-2-08");
            
            System.out.println("el id y descripcion del" + cntEntidad3.getIdEntidad() + "----descripcion---------" + cntEntidad3.getDescripcion() + "");
        } catch (Exception e) {
        }

//     CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class,6L );
//        System.out.println("tiene o no::: "+cntEntidadesService.verificaEntidadCentroCosto(cuenta));
//
//        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 8L);
//        System.out.println("si existe un detalle comprobante con proyecto");
//        System.out.println("existe  una entidad con proyectos asignados en detCom::: " + cntEntidadesService.verificaProyectoDetalleComprobante());
//        System.out.println("si tiene auxilira la cuenta" + cntEntidadesService.verificaEntidadTieneAuxiliarAsignado(cuenta) + "///////////");
//        System.out.println("el proyecto ya tiene asignado parametros automaticos"+cntEntidadesService.verificaEntidadTieneParametrosAutomaticos()+"///////////");
//        CntEntidad cuenta1 = cntEntidadesService.find(CntEntidad.class, 1142L);
//        for (CntEntidad ce : cntEntidadesService.listaTodosLosDescendientes(cuenta1)) {
//            System.out.println("ID: "+ce.getIdEntidad()+" CUENTA: " + ce.getMascaraGenerada()+ " Descripcion: "+ce.getDescripcion());
//
//        }
//        System.out.println("//////////////////////////");
//        for (CntEntidad ce : cntEntidadesService.listaTodosLosDescendientes(cuenta1)) {
//            System.out.println("ID: "+ce.getIdEntidad()+" CUENTA: " + ce.getMascaraGenerada()+ " Descripcion: "+ce.getDescripcion());
//
//        }
////        System.out.println("el proyecto ya tiene asignado parametros automaticos"+cntEntidadesService.verificaEntidadTieneParametrosAutomaticos()+"///////////");
//
////        System.out.println("VERIFICA: "+cntEntidadesService.verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(cuenta1));
//                System.out.println("entro a modificar registro de parametros automaticos");
//
//        List<CntParametroAutomatico>listaparametros=cntEntidadesService.listaDeParametrosautomaticosmodificar();
//        for (CntParametroAutomatico cntParametroAutomatico : listaparametros) {
//            System.out.println("id "+cntParametroAutomatico.getIdParametroAutomatico()+"----------"+cntParametroAutomatico.getCntEntidad().getIdEntidad()+"-------------------------");
//        }
//
//        System.out.println("entro a modificar fecha");
//        cntEntidadesService.cambiafechabaja();
//
//         List<CntParametroAutomatico>listaparametros12=cntEntidadesService.listaDeParametrosautomaticosmodificar();
//        for (CntParametroAutomatico cntParametroAutomatico : listaparametros12) {
//            System.out.println("id "+cntParametroAutomatico.getIdParametroAutomatico()+"----------"+cntParametroAutomatico.getCntEntidad().getIdEntidad()+"-------------------------");
//        }
//         System.out.println("se modifico los parametros");
//
//        System.out.println("Mascar Nueva:"+cntEntidadesService.generaMascaraConElRegistroDelExcel("1-01-01-001"));
//        System.out.println("El nivel de la mascara es--- "+cntEntidadesService.generaNivelPorMascara("1-01-01-01"));
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 528L);
////        System.out.println("el id padre deberia ser:::"+cntEntidadesService.obtieneIdPadreParaBDMigrada(cntEntidad));
//        System.out.println("Elimina:::::");
//        cntEntidad.setFechaBaja(new Date());
//        cntEntidad.setUsuarioBaja("Henrry");
//        ObjectUtils.printObjectState(cntEntidad);
//        try {
////            cntEntidadesService.eliminaPlanDeCuentas(cntEntidad);
//            cntEntidadesService.eliminaCuentaConSuParametrosAutomatico(cntEntidad);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//
//        System.out.println("tiene Centro de costo: "+cntEntidadesService.verificaCentroCostosEntidades());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 47L );
//        try {
//
//        System.out.println("tiene CC "+cntEntidadesService.verificaEntidadCentroCosto(cuenta));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("..................................................");
//        List<ParCuentasGenerales> listap = parParametricasService.listaTodasLasCuentasGenerales();
//        for (ParCuentasGenerales l : listap) {
//            System.out.println("....."+l.getDescripcion()+"..val  "+l.getValor());
//        }
//
//        System.out.println("..................................................");
////        devuelveMascaraPorTipoCuenta
//        System.out.println("..VALOR ..."+parParametricasService.devuelveMascaraPorTipoCuenta("ACT"));
//</editor-fold>

//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 44L);
//        System.out.println(".---nivel es   " + cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad));
//
////        List<CntEntidad> lista = cntEntidadesService.listaPlanCuentasDescendente("EERR");
//        List<CntEntidad> lista = cntEntidadesService.listaPlanCuentasDescendente("BG");
//        for (CntEntidad lista1 : lista) {
//            System.out.println("la mascara es:::: " + lista1.getMascaraGenerada() + "...." + lista1.getNivel() + "..fecha baja : " + lista1.getFechaBaja());
//        }
////<editor-fold defaultstate="collapsed" desc="pruebas">
////        BigDecimal resultado = new BigDecimal(BigInteger.ZERO);
////        BigDecimal numero1 = new BigDecimal(30.00);
////        BigDecimal numero2 = new BigDecimal(40.00);
////        resultado = resultado.subtract(numero2);
////        System.out.println("el segundo resultado "+resultado);
////        resultado = resultado.add(numero1);
////        System.out.println("el primer resultado "+resultado);
////        CntEntidad cuentaInicial = cntEntidadesService.find(CntEntidad.class, 47L);
////        CntEntidad cuentaFinal = cntEntidadesService.find(CntEntidad.class, 47L);
////        System.out.println("resultado::: "+cntEntidadesService.esLaCuentaInicialInferiorALaFinalLibroMayor(cuentaInicial, cuentaFinal));
////        try {
////            List<CntEntidad> listaCuentas = cntEntidadesService.cntEntidadesPorGrupoNivelList("PCTA");
////            for (CntEntidad listaCuenta : listaCuentas) {
////                System.out.println("la mascara::" + listaCuenta.getMascaraGenerada());
////            }
////        } catch (Exception e) {
////
////            e.printStackTrace();
////        }
////        try {
////
////            String[] vector = cntMigracionExcelService.mascaraCereada("9-9-99-99");
////            for (int i = 0; i < vector.length; i++) {
////                System.out.println("dato" + vector[i]);
////
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        String mascara = "1-0-00-00";
////        String mascara = "1-0-00-00";
////        String[] vec = mascara.split("-", 4);
////        for (int i = 0; i < vec.length; i++) {
////            System.out.println("es-- "+vec[i]);
////
////        }
////        String test = mascara.substring(0, 1);
////        String test2 = mascara.substring(1, mascara.length());
////        System.out.println("test "+test+" ::: "+test2);
//        /*}try {
//         cntMigracionExcelService.asignaPadresAPlanDeCuentas();
//        
//         } catch (Exception e) {
//         e.printStackTrace();
//         }
//         try {
//         List<EntidadPojo> listaFinal = cntEntidadesService.getListaEntidadPojoByEntidad(null, 1L);
//         for (EntidadPojo entidadPojo : listaFinal) {
//         System.out.println("IdEntidadPojo:" + entidadPojo.getIdEntidadPojo());
//         System.out.println("Descripcion:" + entidadPojo.getDescripcion());
//         System.out.println("Mascara:" + entidadPojo.getMascara());
//         System.out.println("Tipo:" + entidadPojo.getTipo());
//         }
//         } catch (Exception e) {
//         e.printStackTrace();
//         }*/
////        try {
////            List<EntidadArbolPojo> listaFinal = cntEntidadesService.getListaEntidadArbolPojo();
////            for (EntidadArbolPojo entidadArbolPojo : listaFinal) {
////                System.out.println("IdEntidadArbolPojo:" + entidadArbolPojo.getIdEntidadPojo());
////                System.out.println("Descripcion:" + entidadArbolPojo.getDescripcion());
////                System.out.println("Mascara:" + entidadArbolPojo.getMascara());
////                System.out.println("Tipo:" + entidadArbolPojo.getTipo());
////                //  listamos la entidad Pojo
////                for (EntidadPojo entidadPojo : entidadArbolPojo.getChildren()) {
////                    System.out.println("IdEntidadPojo:" + entidadPojo.getIdEntidadPojo());
////                    System.out.println("Descripcion:" + entidadPojo.getDescripcion());
////                    System.out.println("Mascara:" + entidadPojo.getMascara());
////                    System.out.println("Tipo:" + entidadPojo.getTipo());
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        /*try {
//         List<EntidadPojo> listaFinal = cntEntidadesService.getListaEntidadPojoByEntidadRecursivo(null);
//         for (EntidadPojo entidadPojo : listaFinal) {
//         System.out.println("IdEntidadPojo:" + entidadPojo.getIdEntidadPojo());
//         System.out.println("Descripcion:" + entidadPojo.getDescripcion());
//         System.out.println("Mascara:" + entidadPojo.getMascara());
//         System.out.println("Tipo:" + entidadPojo.getTipo());
//         }
//         } catch (Exception e) {
//         e.printStackTrace();
//         }*/
//        /*try {
//         List<EntidadArbolPojo> listaFinal = cntEntidadesService.getListaEntidadArbolPojoRecursivo();
//         for (EntidadArbolPojo entidadArbolPojo : listaFinal) {
//         System.out.println("IdEntidadArbolPojo:" + entidadArbolPojo.getIdEntidadPojo());
//         System.out.println("Descripcion:" + entidadArbolPojo.getDescripcion());
//         System.out.println("Mascara:" + entidadArbolPojo.getMascara());
//         System.out.println("Tipo:" + entidadArbolPojo.getTipo());
//         //  listamos la entidad Pojo
//         for (EntidadPojo entidadPojo : entidadArbolPojo.getChildren()) {
//         System.out.println("IdEntidadPojo:" + entidadPojo.getIdEntidadPojo());
//         System.out.println("Descripcion:" + entidadPojo.getDescripcion());
//         System.out.println("Mascara:" + entidadPojo.getMascara());
//         System.out.println("Tipo:" + entidadPojo.getTipo());
//         }
//         }
//         } catch (Exception e) {
//         e.printStackTrace();
//         }*/
////        CntEntidad cuentaInicial = cntEntidadesService.find(CntEntidad.class, 47L);
////        CntEntidad cuentaFinal = cntEntidadesService.find(CntEntidad.class, 47L);
////        System.out.println("resultado::: "+cntEntidadesService.esLaCuentaInicialInferiorALaFinalLibroMayor(cuentaInicial, cuentaFinal));
////</editor-fold>
    }
}
