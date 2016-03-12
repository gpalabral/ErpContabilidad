package com.bap.erp.vista.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacesUtils {

    public static ServletContext getServletContext() {
        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

    public static ExternalContext getExternalContext() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext();
    }

    public static HttpSession getHttpSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    public static Object getManagedBean(String beanName) {

        return getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());
    }

    public static void resetManagedBean(String beanName) {
        getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
    }

    private static Application getApplication() {
        ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        return appFactory.getApplication();
    }

    private static ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static HttpServletResponse getServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    private static Object getElValue(String el) {
        return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    public static Object executeMethodBacking(String backing,String metodo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object bean = FacesUtils.getBean(backing);        
        Method method = bean.getClass().getMethod(metodo);
        return method.invoke(bean);
    }

    public static Object getBean(String beanName) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        Application jsfApp = fctx.getApplication();
        ExpressionFactory exprFactory = jsfApp.getExpressionFactory();
        return exprFactory.createValueExpression(elctx, "#{" + beanName + "}", Object.class).getValue(elctx);
    }
}
