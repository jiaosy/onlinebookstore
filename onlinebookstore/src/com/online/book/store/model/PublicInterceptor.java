package com.online.book.store.model;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.render.FreeMarkerRender;

import freemarker.ext.servlet.HttpSessionHashModel;

public class PublicInterceptor implements Interceptor {
     
    public void intercept(Invocation ai) {
        //向freemarker中传jsp的内置对象
        Controller c = ai.getController();
        c.setAttr("request", c.getRequest());
        c.setAttr("response", c.getResponse());
        c.setAttr("session", new HttpSessionHashModel(c.getSession(), FreeMarkerRender.getConfiguration().getObjectWrapper()));
        ai.invoke();
    }

 
 
}
