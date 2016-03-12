package com.bap.erp.modelo.admin.servicio.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.admin.AdmUsuarios;
import com.bap.erp.modelo.admin.servicio.AdmUsuariosService;
import com.bap.erp.modelo.entidades.cnf.ParParametrosActiveDirectory;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.enums.EnumEstadosUsuario;
import com.bap.erp.modelo.enums.EnumTipoUsuario;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import java.util.*;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AdmUsuariosServiceImpl extends GenericDaoImpl<AdmUsuarios> implements AdmUsuariosService {

    @Autowired
    private ParParametricasService parParametricasService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AdmUsuarios persistAdmUsuarios(AdmUsuarios admUsuarios) throws Exception {
        try {
            super.persist(admUsuarios);
        } catch (Exception e) {
            throw e;
        }
        return admUsuarios;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AdmUsuarios mergeAdmUsuarios(AdmUsuarios admUsuarios) throws Exception {
        try {
            super.merge(admUsuarios);
        } catch (Exception e) {
            throw e;
        }
        return admUsuarios;
    }

    public AdmUsuarios verificaUsuario(AdmUsuarios admUsuarios) {
        AdmUsuarios au = null;
        try {
            Map umap = authenticate(admUsuarios.getLogin(), admUsuarios.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return au;
    }

    public Boolean verificaUsuarioActiveDirectory(AdmUsuarios admUsuarios) throws Exception {
        try {
            Map umap = authenticate(admUsuarios.getLogin(), admUsuarios.getPassword());
            return umap != null;

        } catch (Exception e) {
            throw e;
        }
    }

//    private String domain = "bmsc.com";
//    private String ldapHost = "ldap://10.20.30.89";
//    private String searchBase = "dc=bmsc,dc=com";
    private String domain = "";
    private String ldapHost = "";
    private String searchBase = "";

    public void cargaParametros() {
        ldapHost = "";
        domain = "";
        searchBase = "";
        List<ParValor> list = parParametricasService.getParametrosActiDirList();
        ParParametrosActiveDirectory activeDirectoryHOST = (ParParametrosActiveDirectory) parParametricasService.find(ParParametrosActiveDirectory.class, "HOST");
        ldapHost = "ldap://" + activeDirectoryHOST.getValor();
        ParParametrosActiveDirectory activeDirectoryDOM = (ParParametrosActiveDirectory) parParametricasService.find(ParParametrosActiveDirectory.class, "DOM");
        domain = activeDirectoryDOM.getValor();
        StringTokenizer st = new StringTokenizer(domain, ".");
        while (st.hasMoreTokens()) {
            if (!searchBase.equals("")) {
                searchBase = searchBase + ",";
            }
            searchBase = searchBase + "dc=" + st.nextToken();
        }
    }

    public Map authenticate(String user, String pass) {
        cargaParametros();
        System.out.println("===domain " + domain);
        System.out.println("===ldapHost " + ldapHost);
        System.out.println("===searchBase " + searchBase);
        String returnedAtts[] = {
            "givenName",
            "sn",
            "mail",
            "userPrincipalName",
            "sAMAccountName",
            "displayName",
            "userAccountControl",
            "uniCodepwd",
            "objectclass",
            "user",
            "group",
            "description",
            "physicalDeliveryOfficeName",
            "telephoneNumber",
            "company",
            "member",
            "distinguishedName",
            "webPage"};
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";

        //Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);

        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
        env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
        env.put(Context.SECURITY_CREDENTIALS, pass);
        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            //Search objects in GC using filters
            NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                System.out.println("los atributos son::::" + attrs);
                Map amap = null;
                if (attrs != null) {
                    amap = new HashMap();
                    NamingEnumeration ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        //System.out.println("id: " + attr.getID() + "\t\t\t\t value: " + attr.get());
                        amap.put(attr.getID(), attr.get());
                    }
                    ne.close();
                }
                System.out.println("El mapa tiene:::");
                return amap;
            }
        } catch (NamingException ex) {
            //ex.printStackTrace();
        }
        return null;
    }

    public AdmUsuarios encuentraUsuario(AdmUsuarios admUsuarios, Boolean activaBusquedaPassword) throws Exception {
        try {
            AdmUsuarios au;
            String consulta = "";
            if (activaBusquedaPassword) {
                consulta = "and h.password='" + admUsuarios.getPassword() + "'";
            }
            au = (AdmUsuarios) DataAccessUtils.uniqueResult(hibernateTemplate.find(""
                    + "select h from AdmUsuarios h "
                    + "where h.fechaBaja is null "
                    + "and h.estadoUsuario = '" + EnumEstadosUsuario.VIGENTE.getEstado() + "' "
                    + "and h.login='" + admUsuarios.getLogin() + "' " + consulta));
            return au != null ? au : new AdmUsuarios();
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaExistenciaUsuarioBD(AdmUsuarios admUsuarios) throws Exception {
        try {
            Boolean esActiveDirectory = verificaUsuarioActiveDirectory(admUsuarios);
            AdmUsuarios usuarios = encuentraUsuario(admUsuarios, false);
            if (usuarios.getIdUsuario() != null) {
                if (esActiveDirectory) {
                    return true;
                } else {
                    return usuarios.getTipoUsuario().equals(EnumTipoUsuario.ADMINISTRADOR.getCodigo()) ? encuentraUsuario(admUsuarios, true).getIdUsuario() != null : false;
                }
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<AdmUsuarios> listaUsuarios() throws Exception {
        try {
            List<AdmUsuarios> listaUsuario = hibernateTemplate.find(""
                    + "select h from AdmUsuarios h "
                    + "where h.fechaBaja is null "
                    + "and h.estadoUsuario = '" + EnumEstadosUsuario.VIGENTE.getEstado() + "'");
            return listaUsuario.isEmpty() ? Collections.EMPTY_LIST : listaUsuario;
        } catch (Exception e) {
            throw e;
        }
    }

    public AdmUsuarios encuentraUsuarioLogueo(AdmUsuarios admUsuarios) throws Exception {
        try {
            AdmUsuarios au;
            au = (AdmUsuarios) DataAccessUtils.uniqueResult(hibernateTemplate.find(""
                    + "select h from AdmUsuarios h "
                    + "where h.fechaBaja is null "
                    + "and (h.estadoUsuario = '" + EnumEstadosUsuario.VIGENTE.getEstado() + "' or h.estadoUsuario = '" + EnumEstadosUsuario.BLOQUEADO.getEstado() + "') "
                    + "and h.login='" + admUsuarios.getLogin() + "'"));
            return au != null ? au : new AdmUsuarios();
        } catch (Exception e) {
            throw e;
        }
    }

}
