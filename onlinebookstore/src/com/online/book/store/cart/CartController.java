package com.online.book.store.cart;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.jfinal.core.Controller;
import com.online.book.store.model.Goods;
import com.online.book.store.model.Orders;

public class CartController extends Controller {

	HashMap<Goods, Integer> carts = new HashMap<Goods, Integer>();

	public void index() {
		render("cart.html");
	}
	
	public void buy(){};
	
	public void order(){
		Orders order=getModel(Orders.class);
 		order.setOrderDate(new Date());
		System.out.println(order);
		order.save();
	};

	public void add() {
		Goods good = Goods.dao.findById(getPara(0));
		//����ʱ��Ҫ��"��"
		
		if(good.getNum()>0){
		HashMap<Goods, Integer> carts_seession = getSessionAttr("carts");
		
  		if (carts_seession!=null && carts_seession.size()>0){
  			carts = carts_seession;
  		}
  		//��ӵ����ﳵ  �����漰�����ݿ���²��� ����ÿ��good���ǲ�һ����
  		//��˻�ʹ������ӵ�ʱ�� �����µ�Ŀ¼ �������Ҫʹ�ö���Ϊ���� ������дequals����
  		//Ҳ����ֱ����������Ϊ���� ����ÿ�ζ�����һ���ѯ����
 		if (carts.containsKey(good)) {
			System.out.println("------------  "+0);
			carts.put(good, carts.get(good)+1);
		}else{
			System.out.println("------------  "+1);
			carts.put(good, 1);
		}
		setSessionAttr("carts", carts);
		
		good.set("num", good.getNum()-1).update();
		//�������ݿ�����
		//Db.deleteById("orders", 22); ����ֱ�Ӳ������ݿ� �������ӳ��
		}
		
		if(getParaToInt(1)==1){
			render("cart.jsp");
		}else{
			forwardAction("/book");
		}
	}
	/*���û���ܼ򵥣�ֻ��Ҫ���� ehcache.xml �� jar ������ configPlugin�����һ�� : me.add(new EhCachePlugin())�Ϳ���ʹ���ˡ� JFinal ������Ҫ�����¼����÷���

	1��ʹ�� CacheInterceptor�� actoin ����ȫ�Զ�����

	    �����÷��ǳ���������վ��ҳ���Լ���վ����Ƶ�������ú�һ������Ĺ���ʱ�伴�ɡ����÷����Զ����������� action ������������ݡ�

	2��ʹ�� CacheKit �������ڳ����и���������

	    �����÷��ʺϸ���ϸ���ȵؿ�����Ҫ��������ݣ����磬�������Ҫ����ĳ action �е�ĳ�� List ���ݣ�����ʹ�� CacheKit������ʹ�����Ӽ� JFinal �ֲᡣ

	3��ʹ�� JFinal ActiveRecord �е� Model �� Db

	    �����÷����Խ� ActiveRecord �뻺��������ʹ�ã�����ʡ�����һ��ʹ�÷�ʽ���ŵ��Ǽ򵥷��㡢ʡ���룬ȱ���ǲ�ѯҵ����϶�����������

	    ���滺��Ķ����ʱ���뻺�棬�ÿ���������������÷��е�����

	    JFinal �����������򵥵Ĳ��ԣ������治����������ݣ���������Ҫ���ݲ�ͬ��ҵ��ͨ�� CacheKit �еķ�����������������ݡ�

	    ���ڶ���д�ٵ� web վ�㣬���ú��ʵ��Ĺ���ʱ�䣬���ʵ��ĵط���������ܷǳ���Ч���������ܡ�*/


	public void sub() {
		Goods good = Goods.dao.findById(getPara(0));
		HashMap<Goods, Integer> carts = getSessionAttr("carts");
		
		carts.put(good, carts.get(good) > 0 ? carts.get(good) - 1 : 0);
		//����޸��쳣��2�ַ���
		/*1.
		ConcurrentHashMap<Goods,Integer> con_carts=new ConcurrentHashMap<Goods, Integer>();
		con_carts.putAll(carts);
		Iterator<Goods> it=con_carts.keySet().iterator();
		while(it.hasNext()){
			Goods g=it.next();
			if(con_carts.get(g)==0)con_carts.remove(g);
		}
		carts.clear();
		carts.putAll(con_carts);*/
		
		//2.�õ���������ɾ��
		Iterator<Goods> it=carts.keySet().iterator();
		while(it.hasNext()){
			if (carts.get(it.next()) == 0)
				it.remove();
		}
		
	    good.setNum(good.getNum()+1);
	    good.update();
	    
		setSessionAttr("carts", carts);
		render("cart.jsp");
	}
}
