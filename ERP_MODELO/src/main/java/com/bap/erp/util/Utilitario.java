/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.util;

import java.security.Security;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Jonathan
 */
public class Utilitario {

    public Utilitario() {
        this.caracterDecodificacion = "UTF-8";
        this.base64Encoder = new BASE64Encoder();
        this.base64Decoder = new BASE64Decoder();
        this.algoritmoEncriptacion = "";
        this.inicializarDatos_CIPHER();
    }

    public static int differenceBetweenDates(Date fechaMayor, Date fechaMenor) {
        GregorianCalendar date1 = new GregorianCalendar();
        date1.setTime(fechaMenor);
        GregorianCalendar date2 = new GregorianCalendar();
        date2.setTime(fechaMayor);
        if (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)) {
            return date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR);
        } else {
            int diasAnyo = date1.isLeapYear(date1.get(Calendar.YEAR)) ? 366 : 365;
            int rangoAnyos = date2.get(Calendar.YEAR) - date1.get(Calendar.YEAR);
            return (rangoAnyos * diasAnyo) + (date2.get(Calendar.DAY_OF_YEAR) - date1.get(Calendar.DAY_OF_YEAR));
        }
    }
    // <editor-fold defaultstate="collapsed" desc="ENCRIPTA Y DESENCRIPTA MODIFICADOR">
    private String algoritmoEncriptacion;
    private Cipher encriptacionCipher;
    private Cipher desencryptacionCipher;
    private String caracterDecodificacion;
    private BASE64Encoder base64Encoder;
    private BASE64Decoder base64Decoder;

    public void inicializarDatos_CIPHER() {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());

            final byte[] keyBytes = {
                0x21, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                0x09, 0x0a, 0x3b, 0x0c, 0x0d, 0x4e, 0x0f, 0x10,
                0x01, 0x02, 0x03, 0x04, 0x15, 0x06, 0x07, 0x08,};

            final byte[] ivBytes = {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07
            };

            this.algoritmoEncriptacion = "DESede";
            SecretKey key = new SecretKeySpec(keyBytes, this.algoritmoEncriptacion);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);


            this.algoritmoEncriptacion = "SunJCE";
            //Inicializando Encriptacion 'Cipher'.
            this.encriptacionCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
            this.encriptacionCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key, iv);

            //Inicializando Desencriptacion 'Cipher'.
            this.desencryptacionCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding", "SunJCE");
            this.desencryptacionCipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encriptarCIPHER(String cadena) {

        String datoEncriptado = null;

        try {
            byte[] cadenaArrayBytes = cadena.getBytes(this.caracterDecodificacion);
            byte[] cadenaEncriptada = this.encriptacionCipher.doFinal(cadenaArrayBytes);
            datoEncriptado = this.base64Encoder.encode(cadenaEncriptada);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datoEncriptado;
    }

    public String desencriptarCIPHER(String cadenaEncryptada) {

        String datoDesenCriptado = null;

        try {
            byte[] cadenaArrayBytes = this.base64Decoder.decodeBuffer(cadenaEncryptada);
            byte[] cadenaEncriptada = this.desencryptacionCipher.doFinal(cadenaArrayBytes);
            datoDesenCriptado = new String(cadenaEncriptada, this.caracterDecodificacion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datoDesenCriptado;
    }
    // </editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="PARA CONVERTIR NUMEROS EN LITERAL">
    private final String[] UNIDADES = {"", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
    private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
        "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
        "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
    private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
        "setecientos ", "ochocientos ", "novecientos "};
    
    
    public String Convertir(String numero, boolean mayusculas) {
        String literal = "";
        String parte_decimal;
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        numero = numero.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if (numero.indexOf(",") == -1) {
            numero = numero + ",000000";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
            //se divide el numero 0000000,00 -> entero y decimal
            String Num[] = numero.split(",");
            //de da formato al numero decimal
            parte_decimal = Num[1] + "/100 Bolivianos.";
            //se convierte el numero a literal
            if (Integer.parseInt(Num[0]) == 0) {//si el valor es cero
                literal = "cero ";
            } else if (Integer.parseInt(Num[0]) > 999999) {//si es millon
                literal = getMillones(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 999) {//si es miles
                literal = getMiles(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 99) {//si es centena
                literal = getCentenas(Num[0]);
            } else if (Integer.parseInt(Num[0]) > 9) {//si es decena
                literal = getDecenas(Num[0]);
            } else {//sino unidades -> 9
                literal = getUnidades(Num[0]);
            }
            //devuelve el resultado en mayusculas o minusculas
            if (mayusculas) {
                return (literal + parte_decimal).toUpperCase();
            } else {
                return (literal + parte_decimal);
            }
        } else {//error, no se puede convertir
            return literal = null;
        }
    }
    
    /*
     * funciones para convertir los numeros a literales
     */
    private String getUnidades(String numero) {// 1 - 9
        //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
        String num = numero.substring(numero.length() - 1);
        return UNIDADES[Integer.parseInt(num)];
    }
    
    private String getDecenas(String num) {// 99
        int n = Integer.parseInt(num);
        if (n < 10) {//para casos como -> 01 - 09
            return getUnidades(num);
        } else if (n > 19) {//para 20...99
            String u = getUnidades(num);
            if (u.equals("")) { //para 20,30,40,50,60,70,80,90
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
            } else {
                return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
            }
        } else {//numeros entre 11 y 19
            return DECENAS[n - 10];
        }
    }
    
    private String getCentenas(String num) {// 999 o 099
        if (Integer.parseInt(num) > 99) {//es centena
            if (Integer.parseInt(num) == 100) {//caso especial
                return " cien ";
            } else {
                return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
            }
        } else {//por Ej. 099
            //se quita el 0 antes de convertir a decenas
            return getDecenas(Integer.parseInt(num) + "");
        }
    }
    
    private String getMiles(String numero) {// 999 999
        //obtiene las centenas
        String c = numero.substring(numero.length() - 3);
        //obtiene los miles
        String m = numero.substring(0, numero.length() - 3);
        String n = "";
        //se comprueba que miles tenga valor entero
        if (Integer.parseInt(m) > 0) {
            n = getCentenas(m);
            return n + "mil " + getCentenas(c);
        } else {
            return "" + getCentenas(c);
        }
        
    }
    
    private String getMillones(String numero) { //000 000 000
        //se obtiene los miles
        String miles = numero.substring(numero.length() - 6);
        //se obtiene los millones
        String millon = numero.substring(0, numero.length() - 6);
        String n = "";
        if (millon.length() > 1) {
            n = getCentenas(millon) + "millones ";
        } else {
            n = getUnidades(millon) + "millon ";
        }
        return n + getMiles(miles);
    }
    //</editor-fold>

}
