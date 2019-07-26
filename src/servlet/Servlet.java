package servlet;

/**
 * @description: Servlet
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public interface Servlet {

    /**
     * servlet初始化
     */
	void init();
    /**
     * servlet业务处理
     */
	void service(HttpServletRequest request, HttpServletResponse response);
}
