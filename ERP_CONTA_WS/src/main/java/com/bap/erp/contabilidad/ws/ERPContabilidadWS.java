package com.bap.erp.contabilidad.ws;

import com.bap.erp.commons.entities.mappings.CalculosTributarios;
import com.bap.erp.commons.entities.mappings.Comprobante;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.pojo.CntCuentaPlanCuentasPojo;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.ws.pojo.Entidad;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/contabilidad")
@Api(value = "contabilidad", description = "WS for BAP - Contabilidad")
public class ERPContabilidadWS {

    @Autowired
    private CntEntidadesService cntEntidadesService;

    @Autowired
    private CntMascarasService cntMascarasService;

    @Autowired
    private CntTipoCambioService cntTipoCambioService;

    @Autowired
    private CntComprobantesService cntComprobantesService;

    @Autowired
    private CntFacturacionService cntFacturacionService;

    @GET
    @Path("/planctas")
    @ApiOperation(value = "Devuelve el plan de cuentas.")
    @Produces(MediaType.APPLICATION_JSON)
    public Response planctas(
            @ApiParam(value = "gruponivel", required = true) @QueryParam("gruponivel") String gruponivel,
            @ApiParam(value = "token", required = true) @QueryParam("token") String token) {

        System.out.println("cntEntidadesService::: " + cntEntidadesService);
        int status = 200;
        List<Entidad> plan = new ArrayList<Entidad>();
        try {
            List<CntEntidad> planCtas = cntEntidadesService.cntEntidadesPorGrupoNivelList(gruponivel);

            CntMascara cntMascara = cntMascarasService.obtieneMascarasPorGrrupoNivel(gruponivel);

            for (CntEntidad planCta : planCtas) {
                plan.add(entityWrapper(planCta, cntMascara.getTamanioNivel()));
            }

        } catch (Exception e) {
            status = 400;
            e.printStackTrace();
        }
        return Response.status(status).entity(plan).build();
    }

    private Entidad entityWrapper(CntEntidad cntEntidad, int tamanioNivel) {
        //Invertimos el orden de los niveles.
        int nivel = tamanioNivel - cntEntidad.getNivel() + 1;
        return new Entidad(cntEntidad.getIdEntidad(), cntEntidad.getIdEntidadPadre(), cntEntidad.getDescripcion(), cntEntidad.getMascaraGenerada(), nivel);
    }

    @GET
    @Path("/tipoCambio")
    @ApiOperation(value = "devuelve tipoCambio por fecha DD/MM/YYYY")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoDeCAmbioPorFecha(@QueryParam("fecha") String fecha) {
        int status = 200;
        CntTipoCambio tipoCambios = new CntTipoCambio();
        try {
            tipoCambios = cntTipoCambioService.devuelveCntTipoDeCambioWSParaCxP(fecha);
        } catch (Exception e) {
            status = 400;
            e.printStackTrace();
        }
        return Response.status(status).entity(tipoCambios).build();
    }

    @POST
    @Path("/getComprobante")
    @ApiOperation(value = "devuelve Comprobante con calculos Automaticos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getComprobante(@ApiParam(value = "calculosTributarios", required = true) CalculosTributarios calculosTributarios) {
        System.out.println("entra al WS de compro");
        calculosTributarios = cntComprobantesService.calculosTributarios(calculosTributarios);
        return Response.status(200).entity(calculosTributarios).build();
    }

    @GET
    @Path("/getById/{idEntidad}")
    @ApiOperation(value = "get CntEntidad By Id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCntEntidadById(@PathParam("idEntidad") String idEntidad) {
        CntCuentaPlanCuentasPojo cntCuentaPlanCuentasPojo = new CntCuentaPlanCuentasPojo();
        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, Long.parseLong(idEntidad));
        if (cntEntidad != null) {
            cntCuentaPlanCuentasPojo.setIdEntidad(cntEntidad.getIdEntidad());
            cntCuentaPlanCuentasPojo.setDescripcion(cntEntidad.getDescripcion());
            cntCuentaPlanCuentasPojo.setMascara(cntEntidad.getMascaraGenerada());
        }
        return Response.status(200).entity(cntCuentaPlanCuentasPojo).build();
    }

    @POST
    @Path("/saveComprobante")
    @ApiOperation(value = "saving CntComprobante with CPP info")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveComprobante(@ApiParam(value = "cntComprobantePojo", required = true) Comprobante comprobante) {
        try {
            CntComprobante cntComprobante = cntComprobantesService.guardaComprobanteCxP(comprobante);
            comprobante.setIdCntComprobantePojo(cntComprobante.getIdComprobante());
            return Response.status(200).entity(comprobante).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e).build();
        }
    }

    @GET
    @Path("/getNumeroFactura/{idDosificacion}")
    @ApiOperation(value = "get the next number of Factura")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumeroFactura(@PathParam("idDosificacion") Long idDosificacion) {
        try {
            Long numero = cntFacturacionService.generaNumeroDeFactura(idDosificacion);
            return Response.status(200).entity(numero).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e).build();
        }
    }

}
