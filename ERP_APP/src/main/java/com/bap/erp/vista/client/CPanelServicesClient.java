/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CPanelServicesClient {

    @Resource(name = "cpanelProperties")
    private Properties cpanelProperties;
    @Value("${cpanel_server}")
    private String cpanelServer = "localhost:8080/CPANEL_APP";
//    private String cpanelServer="localhost:8080/wamsa";
    
    @PostConstruct
    public void init() {
        System.out.println("CPanelServicesClient::::::: cpanelProperties = " + cpanelProperties + "   cpanelServer = " + cpanelServer);
    }

//    public AdmUsuarios login(String user, String password) {
//        System.out.println("el usuario es" + user);
//        System.out.println("el pass es" + password);
//
//        Client client = Client.create();
//
//        WebResource webResource = client.resource("http://" + cpanelServer + "/rest/auth/post");
//
//        String input = "{\"userName\":\"" + user + "\",\"password\":\"" + password + "\"}";
//
//        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
//
//        if (response.getStatus() != 200) {
//            throw new RuntimeException("Failed : HTTP error code: " + response.getStatus());
//        }
//        System.out.println("Output from Server .... \n" + response.getType());
//        String stringResponse = response.getEntity(String.class);
//        System.out.println(stringResponse);
//
//        AdmUsuarios admUsuarios = (AdmUsuarios) jsonToObject(stringResponse, AdmUsuarios.class);
//        return admUsuarios;
//
//
//    }

    private Serializable jsonToObject(String jsonString, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        Serializable instance = null;

        try {
            // read from file, convert it to user class
            instance = (Serializable) mapper.readValue(jsonString, clazz);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public Boolean verificaConexion(String ipConexion) throws Exception {
        System.out.println("IP: " + ipConexion);
        Client client = Client.create();
        WebResource webResource = client.resource("http://" + ipConexion + ":8080/CPANEL_APP/rest/auth/post");
        System.out.println("TEST 1:" + webResource);
        String input = "{\"userName\":\"admin\",\"password\":\"admin\"}";
        try {
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

            System.out.println("TEST 2:" + response);
            if (response.getStatus() == 200) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }

    }
}
