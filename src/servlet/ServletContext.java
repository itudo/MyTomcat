package servlet;

import java.util.Hashtable;
import java.util.Map;

/**
 * @description: Servlet上下文
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class ServletContext {
    /**
     * servlet容器：存储servlet实例
     */
	public static Map<String,Object> servlets = new Hashtable<String,Object>();
    /**
     * 存储session信息
     */
	static Map<String,HttpSession> sessions = new Hashtable<>();
}
