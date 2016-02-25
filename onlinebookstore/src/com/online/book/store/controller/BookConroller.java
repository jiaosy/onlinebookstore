package com.online.book.store.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;
import com.online.book.store.model.Goods;

/**
 * 锟斤拷锟斤拷锟介籍锟斤拷息 锟斤拷要锟角癸拷锟斤拷员锟侥癸拷锟斤拷
 * 
 * @author Administrator
 *
 */
public class BookConroller extends Controller {
	public void index() {
		// Goods.dao.paginate(getPara(0,1), 10, "");
		setAttr("books", Goods.dao.find("SELECT * FROM goods"));
		render("book.html");
	}
	
	public void managebook(){
		setAttr("books", Goods.dao.find("select * from goods"));
	}
	public void add() {// 转锟斤拷锟斤拷榧筹拷锟�
	}

	public void update() {// 锟睫革拷
		Goods g = Goods.dao.findById(getPara());
		setAttr("book", g);
	}

	public void save() {// 锟斤拷锟斤拷 锟斤拷锟睫革拷
		// 锟较达拷锟侥硷拷
		// 锟斤拷锟斤拷路锟斤拷
		// String filePath = "C://upload";
		// 锟斤拷锟斤拷锟铰凤拷锟�
		String filePath = PathKit.getWebRootPath() + "\\upload";
		System.out.println(filePath);// 锟斤拷锟斤拷锟斤拷锟较达拷锟侥硷拷锟斤拷锟铰凤拷锟�
		File uploadPath = new File(filePath);
		System.out.println(2);
		// 锟斤拷锟斤拷募锟斤拷锟斤拷欠锟斤拷锟斤拷 锟斤拷锟斤拷锟斤拷 锟斤拷锟斤拷一锟斤拷
		if (!uploadPath.exists()) {
			System.out.println(3);
			uploadPath.mkdir();
		}
		// 锟侥硷拷锟斤拷锟斤拷锟斤拷锟�3M
		int fileMaxSize = 3 * 1024 * 1024;
		// 锟斤拷锟斤拷募锟斤拷锟斤拷锟�
		// 锟侥硷拷锟斤拷
		//String fileName = null;
		// 锟较达拷锟侥硷拷锟斤拷
		//int fileCount = 0;
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟�
		// RandomFileRenamePolicy rfrp = new RandomFileRenamePolicy();
		// 锟较达拷锟侥硷拷
		MultipartRequest mulit = new MultipartRequest(getRequest(), "\\upload",
				fileMaxSize, "UTF-8");// 取锟斤拷锟较达拷锟侥硷拷
		//要锟斤拷锟斤拷锟斤拷榧�
		String sname = mulit.getParameter("sname");
		String sprice= mulit.getParameter("sprice");
		String sid = mulit.getParameter("sid");
		String num =mulit.getParameter("num");
		Goods g=new Goods();
		g.setSname(sname);
		g.setSprice(sprice);
		g.setSid(Integer.parseInt(sid));
		g.setNum(Integer.parseInt(num));
		System.out.println(getPara());
		
		if(getPara().equals("update")){
			System.out.println(1);
			g.update();
		}else{
			System.out.println(2);
			g.save();
		}

		List<UploadFile> filesname =  mulit.getFiles() ;// 取锟斤拷锟较达拷锟斤拷锟斤拷锟斤拷锟侥硷拷(锟洁当锟节憋拷识)
		FileWriter fw=null;
		FileReader fr=null;
		try {
			File f=filesname.get(0).getFile();
			fr=new FileReader(f);
			fw=new FileWriter("/upload");
			char[] buff=new char[1024];
			while(fr.read(buff)!=-1){
				fw.append(new String(buff, 0, buff.length));
 			}
			f.renameTo(new File(sid));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		redirect("/book/managebook");
	}

	public void edit(){
		
	}
	public void delete() {
		Goods.dao.deleteById(getParaToInt());
		redirect("/book/managebook");
	}
}
