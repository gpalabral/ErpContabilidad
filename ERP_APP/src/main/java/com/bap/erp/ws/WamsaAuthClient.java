package com.bap.erp.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Component;

@Component
public class WamsaAuthClient {
    
    private final String cpanelServer="http://localhost:8080/WAMSA_APP/wamsaws/auth/post";
    
    public boolean login(String username,String password) {
        System.out.println("Llamando al WS:::::::");
        Client client = Client.create();
        WebResource webResource = client.resource(cpanelServer);
        
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("username", username);
        formData.add("password",password);
        
        
        try {
            ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code: " + response.getStatus());
            }
            String stringResponse = response.getEntity(String.class);
            System.out.println("stringResponse::: " + stringResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    
}
