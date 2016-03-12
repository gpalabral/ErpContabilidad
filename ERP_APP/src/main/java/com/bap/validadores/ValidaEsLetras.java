package com.bap.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validaEsLetras")
public class ValidaEsLetras implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent c, Object value)
            throws ValidatorException {
        String valor = (String) value;
          if(valor.matches("[a-z]*")){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe ingresar solo Letras.", null));
        }
    }
}
