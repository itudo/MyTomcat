package server;

import servlet.HttpServlet;
import servlet.HttpServletRequest;
import servlet.HttpServletResponse;
import servlet.ServletContext;

/**
 * @description: 动态请求处理类
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class DynamicProcessor implements Processor {

	@Override 
	public void process(HttpServletRequest request, HttpServletResponse response){

		//1、假设请求的地址为  /hello.action  ->对应的是hello 这个servlet 类
		//从request中取出getRequestURI
		String uri = request.getRequestURI();
		//2、去掉 / 及action 两个部分 ，得到hello  ，就是servlet类名
		String servletName = uri.substring(1,uri.indexOf("."));
		//单实例实现  ->到Map中判断是否已经存在了这个servlet实例，如果有，则取出这个实例
		//利用反射来加载这个类的字节码  URLClassLoader 来加载class
		HttpServlet servlet = null;
		//没有servlet实例
		if(!ServletContext.servlets.containsKey(servletName)){
			try {
				Class c = Class.forName(servletName);
				//2、利用class实例化newInstance()->  servlet初始化
				Object obj = c.newInstance();
				//将servlet放入ServletContext中
				ServletContext.servlets.put(servletName, obj);
				if(obj instanceof HttpServlet){
				servlet = (HttpServlet)obj;
				servlet.init();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			servlet = (HttpServlet) ServletContext.servlets.get(servletName);
		}
		servlet.service(request, response);
		
	}

}


