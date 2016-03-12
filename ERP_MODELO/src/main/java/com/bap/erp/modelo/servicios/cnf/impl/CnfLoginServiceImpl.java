package com.bap.erp.modelo.servicios.cnf.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParAdministrador;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.servicios.cnf.CnfLoginService;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CnfLoginServiceImpl extends GenericDaoImpl<ParAdministrador> implements CnfLoginService {

    public String encriptarContrasena(String login, String pass) {
        String llaveSimetrica1;        
        llaveSimetrica1 = obtieneClavePartidaEnDos(encriptarContrasenaMD5(login));        
        byte[] aError = null;
        SecretKeySpec key = new SecretKeySpec(llaveSimetrica1.getBytes(), "AES");
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] datosCifrados = cipher.doFinal(pass.getBytes()); //cadena = a texto a cifrar
            return new sun.misc.BASE64Encoder().encode(datosCifrados);
        } catch (Exception e) {
            return null;
        }
    }

    public String obtieneClavePartidaEnDos(String passEncriptado) {
        String claveNueva = "";
        for (int i = 0; i < passEncriptado.substring(0, 16).length(); i++) {
            claveNueva = claveNueva + passEncriptado.charAt(i);
        }
        return claveNueva;
    }

    public String encriptarContrasenaMD5(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(contrasena.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String();
    }

    public String desencriptaContrasena(String login, String datosCifrados) {
        String llaveSimetrica1;
        llaveSimetrica1 = obtieneClavePartidaEnDos(encriptarContrasenaMD5(login));
        SecretKeySpec key = new SecretKeySpec(llaveSimetrica1.getBytes(), "AES");
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] datosDecifrados = cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(datosCifrados));
            return new String(datosDecifrados);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean validaUsuario(String login, String pass) {
        String passEncriptado = encriptarContrasena(login, pass);
        List<ParAdministrador> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParAdministrador j "
                + "where j.fechaBaja is null "
                + "and j.tipoUsuario = 'ADM' "
                + "and j.usuario = '" + login + "' "
                + "and j.contrasenia = '" + passEncriptado + "' ");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public ParAdministrador findByUsuarioYPass(String usuario, String contrasenia) {
        List<ParAdministrador> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParAdministrador j "
                + "where j.fechaBaja is null "
                + "and j.usuario = '" + usuario + "' "
                + "and j.contrasenia = '" + encriptarContrasena(usuario, contrasenia) + "' ");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @Transactional(isolation= Isolation.DEFAULT, propagation= Propagation.REQUIRED)
    public ParAdministrador mergeParAdministrador(ParAdministrador parAdministrador) throws Exception{
        try {
            super.merge(parAdministrador);
            return parAdministrador;
        } catch (Exception e) {
            throw e;
        }
    }

    public String encuentraTipoUsuarioPorLogin(String login) {        
        List<ParAdministrador> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParAdministrador j "
                + "where j.fechaBaja is null "
                + "and j.usuario = '"+login+"'");
        if(!lista.isEmpty()){
            return lista.get(0).getTipoUsuario();
        }
        return null;
    }
}
