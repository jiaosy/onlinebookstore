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
		//传参时不要加"？"
		
		if(good.getNum()>0){
		HashMap<Goods, Integer> carts_seession = getSessionAttr("carts");
		
  		if (carts_seession!=null && carts_seession.size()>0){
  			carts = carts_seession;
  		}
  		//添加到购物车  由于涉及了数据库更新操作 所以每次good都是不一样的
  		//因此会使得再添加的时候 都是新的目录 所以如果要使用对象为主键 必须重写equals方法
  		//也可以直接以其主键为主键 不过每次都多了一遍查询操作
 		if (carts.containsKey(good)) {
			System.out.println("------------  "+0);
			carts.put(good, carts.get(good)+1);
		}else{
			System.out.println("------------  "+1);
			carts.put(good, 1);
		}
		setSessionAttr("carts", carts);
		
		good.set("num", good.getNum()-1).update();
		//更新数据库数据
		//Db.deleteById("orders", 22); 可以直接操作数据库 无需进行映射
		}
		
		if(getParaToInt(1)==1){
			render("cart.jsp");
		}else{
			forwardAction("/book");
		}
	}
	/*启用缓存很简单，只需要放入 ehcache.xml 与 jar 包并在 configPlugin中添加一句 : me.add(new EhCachePlugin())就可以使用了。 JFinal 缓存主要有如下几种用法：

	1：使用 CacheInterceptor对 actoin 进行全自动缓存

	    这种用法非常适用于网站首页，以及网站各大频道，设置好一个合理的过期时间即可。该用法会自动化缓存整个 action 所需的所有数据。

	2：使用 CacheKit 工具类在程序中辅助做缓存

	    这种用法适合更加细粒度地控制需要缓存的数据，例如，你仅仅需要缓存某 action 中的某个 List 数据，可以使用 CacheKit，具体使用例子见 JFinal 手册。

	3：使用 JFinal ActiveRecord 中的 Model 与 Db

	    这种用法可以将 ActiveRecord 与缓存结合起来使用，是最省代码的一种使用方式，优点是简单方便、省代码，缺点是查询业务耦合度有所提升。

	    被存缓存的对象何时存入缓存，得看具体情况，三种用法有点区别。

	    JFinal 缓存采用了最简单的策略，仅缓存不负责更新数据，开发者需要根据不同的业务通过 CacheKit 中的方法主动清除缓存数据。

	    对于读多写少的 web 站点，设置好适当的过期时间，在适当的地方清除缓存能非常有效地提升性能。*/


	public void sub() {
		Goods good = Goods.dao.findById(getPara(0));
		HashMap<Goods, Integer> carts = getSessionAttr("carts");
		
		carts.put(good, carts.get(good) > 0 ? carts.get(good) - 1 : 0);
		//解决修改异常的2种方法
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
		
		//2.用迭代器进行删除
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
