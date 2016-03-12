package com.bap.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validaPassword")
public class ValidaPassword implements Validator {

    public static String NUMEROS = "0123456789";
    public static String MAYUSCULAS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public static String MINUSCULAS = "abcdefghijklmnñopqrstuvwxyz";
    public static String ESPECIALES = "ñÑ@&%$#?¡¿!()*[]{}";

    FacesMessage message = new FacesMessage();

    @Override
    public void validate(FacesContext fc, UIComponent component, Object value)
            throws ValidatorException {
//        String valor = (String) component.getAttributes().get("password");
        String valor = (String) value;
//        if (!value.equals(valor)) {
//            message.setSummary("La contrase\u00f1a no coincide ");
//            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(message);
//        } else {
        if (!validaRestricciones(valor, MAYUSCULAS) || !validaRestricciones(valor, MINUSCULAS)) {
            message.setSummary("Es necesario que la contrase\u00f1a contenga por lo menos una Letra.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        } else {
            if (!validaRestricciones(valor, NUMEROS)) {
                message.setSummary("Es necesario que la contrase\u00f1a contenga por lo menos un Numero.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            } else {
                if (!validaRestricciones(valor, ESPECIALES)) {
                    message.setSummary("Es necesario que la contrase\u00f1a contenga por lo menos un Caracter Especial.");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                } else {
                    if (valor.length() < 10) {
                        message.setSummary("Es necesario que la contrase\u00f1a tengan mayor a 9 digitos por su seguridad.");
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(message);
                    }
                }
            }
        }
//        }
    }

    public Boolean validaRestricciones(String password, String tipoDato) {
        Boolean valida = false;
        for (int i = 0; i < password.length(); i++) {
            if (tipoDato.indexOf(password.charAt(i), 0) != -1) {
                valida = true;
            }
        }
        return valida;
    }
}
