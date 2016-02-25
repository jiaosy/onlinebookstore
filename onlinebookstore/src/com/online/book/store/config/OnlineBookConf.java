package com.online.book.store.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.online.book.store.cart.CartController;
import com.online.book.store.controller.BookConroller;
import com.online.book.store.controller.UserinfoConroller;
import com.online.book.store.model.PublicInterceptor;
import com.online.book.store.model._MappingKit;

public class OnlineBookConf extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		//me.setViewType(ViewType.JSP);
		//me.setError404View("404�������");
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/book", BookConroller.class);
		me.add("/userinfo", UserinfoConroller.class);
		me.add("/shop", CartController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
 		 C3p0Plugin c3p0Plugin =new C3p0Plugin("jdbc:mysql://127.0.0.1/online_shopping?characterEncoding=utf8", "root", "123");
		 me.add(c3p0Plugin);
		
	 	/*MysqlDataSource md=new MysqlDataSource();
		md.setUrl("jdbc:mysql://127.0.0.1/online_shopping?characterEncoding=utf8");
		md.setUser("root");
		md.setPassword("123");*/
		
		
		ActiveRecordPlugin acr=new ActiveRecordPlugin(c3p0Plugin);
		//acr.setShowSql(true);//���sql���
		//acr.setDialect(new OracleDialect());//���÷���
		me.add(acr);
		_MappingKit.mapping(acr);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new PublicInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		
	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
