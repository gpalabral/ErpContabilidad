package com.bap.erp.modelo.servicios.cnf;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnf.ParAdministrador;

public interface CnfLoginService extends GenericDao<ParAdministrador> {

    String encriptarContrasena(String login, String pass);

    String desencriptaContrasena(String login, String datosCifrados);

    String encriptarContrasenaMD5(String contrasena);      
    
    Boolean validaUsuario(String login, String pass);
    
    ParAdministrador findByUsuarioYPass(String usuario, String contrasenia);
    
    ParAdministrador mergeParAdministrador(ParAdministrador parAdministrador) throws Exception;
    
    String encuentraTipoUsuarioPorLogin(String login);
    
}
