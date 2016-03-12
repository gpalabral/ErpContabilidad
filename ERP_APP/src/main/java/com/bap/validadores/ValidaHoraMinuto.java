package com.bap.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "validaHoraMinuto")
public class ValidaHoraMinuto implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent c, Object value)
            throws ValidatorException {

        if (value != null) {
            String valor = (String) value;
            if (valor.length() != 0) {
                String[] a = valor.split(":");
                int H = Integer.parseInt(a[0]);
                int M = Integer.parseInt(a[1]);
                if (H >= 0 && H < 24) {
                    if (M < 0 || M > 60) {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor Minuto incorrecto, no existe el valor " + M + " en Minutos.", null));
                    }
                } else {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor Hora incorrecta, no existe el valor " + H + " en Horas.", null));
                }
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo " + c.getId() + " es obligatorio, para el registro.", null));
            }
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Campo " + c.getId() + " es obligatorio, para el registro.", null));
        }
    }
}
