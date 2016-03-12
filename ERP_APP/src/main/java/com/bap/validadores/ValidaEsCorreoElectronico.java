package com.bap.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validaEsCorreoElectronico")
public class ValidaEsCorreoElectronico implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent c, Object value)
            throws ValidatorException {
        String valor = (String) value;
        if (!valor.isEmpty() || valor.length() != 0) {
            if (!isEmail(valor)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor del campo " + c.getId() + " es incorrecto.", null));
            }
        }else{
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo "+c.getId()+" es obligatorio.", null));
        }
    }

    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        mat = pat.matcher(correo);
        if (mat.find()) {            
            return true;
        } else {
            return false;
        }
    }
}
