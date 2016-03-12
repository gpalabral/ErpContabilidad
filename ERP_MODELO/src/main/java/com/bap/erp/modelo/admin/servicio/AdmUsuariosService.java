package com.bap.erp.modelo.admin.servicio;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.admin.AdmUsuarios;
import java.util.List;

public interface AdmUsuariosService extends GenericDao<AdmUsuarios> {

    AdmUsuarios persistAdmUsuarios(AdmUsuarios admUsuarios) throws Exception;

    AdmUsuarios mergeAdmUsuarios(AdmUsuarios admUsuarios) throws Exception;

    Boolean verificaUsuarioActiveDirectory(AdmUsuarios admUsuarios) throws Exception;

    AdmUsuarios encuentraUsuario(AdmUsuarios admUsuarios, Boolean activaBusquedaPassword) throws Exception;

    Boolean verificaExistenciaUsuarioBD(AdmUsuarios admUsuarios) throws Exception;

    List<AdmUsuarios> listaUsuarios() throws Exception;

    AdmUsuarios encuentraUsuarioLogueo(AdmUsuarios admUsuarios) throws Exception;

}
