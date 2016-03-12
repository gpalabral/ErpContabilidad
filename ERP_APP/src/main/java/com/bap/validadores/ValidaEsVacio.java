package com.bap.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validaEsVacio")
public class ValidaEsVacio implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent c, Object value)
            throws ValidatorException {
        if (value != null) {
            String valor = (String) value;
            if (valor.isEmpty() || valor.length() == 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo " + c.getId() + " es obligatorio.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "El Campo " + c.getId() + " es obligatorio."));
        }
    }
}
