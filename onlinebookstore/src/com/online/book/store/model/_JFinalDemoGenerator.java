package com.online.book.store.model;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;
 
public class _JFinalDemoGenerator {
	
	public static DataSource getDataSource() {
 		C3p0Plugin c3p0Plugin =new C3p0Plugin("jdbc:mysql://127.0.0.1/online_shopping?characterEncoding=utf8", "root", "123");
		c3p0Plugin.start();
		return c3p0Plugin.getDataSource();
	}
	
	public static void main(String[] args) {
 		String baseModelPackageName = "com.online.book.store.model.base";
 		String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/online/book/store/model/base";
		
 		String modelPackageName = "com.online.book.store.model";
 		String modelOutputDir = baseModelOutputDir + "/..";
		
 		Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
 		gernerator.addExcludedTable("adv");
 		gernerator.setGenerateDaoInModel(true);
 		gernerator.setGenerateDataDictionary(false);
 		gernerator.setRemovedTableNamePrefixes("t_");
 		gernerator.generate();
	}
}




