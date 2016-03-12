package com.bap.erp.ws;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.ws.pojo.Entidad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//@Path("/contabilidad")
public class ERPContabilidadWS {

    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntTipoCambioService cntTipoCambioService;

    @GET
    @Path("/planctas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response planctas(@QueryParam("gruponivel") String gruponivel) {

        int status = 200;
        List<Entidad> plan = new ArrayList<Entidad>();
        try {
            List<CntEntidad> planCtas = cntEntidadesService.cntEntidadesPorGrupoNivelList(gruponivel);

            for (CntEntidad planCta : planCtas) {
                plan.add(entityWrapper(planCta));
            }

        } catch (Exception e) {
            status = 400;
            e.printStackTrace();
        }
        return Response.status(status).entity(plan).build();
    }

    private Entidad entityWrapper(CntEntidad cntEntidad) {
        return new Entidad(cntEntidad.getIdEntidad(), cntEntidad.getIdEntidadPadre(), cntEntidad.getDescripcion(), cntEntidad.getMascaraGenerada());
    }

    @GET
    @Path("/tipoDeCambioPorFecha")
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

}
