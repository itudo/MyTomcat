package servlet;

/** httpServlet针对http协议
 *  GET /HTTP/1.1
 * @description: 简单Cookie类
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public abstract class HttpServlet implements Servlet {
	@Override
	public void init(){}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {}

	/** 从request中取出method，判断是get还是post，调用doGet，doPost
	 * @param request 请求
	 * @param response 响应
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		if("GET".equals(method)){
			doGet(request,response);
		}else if("POST".equals(method)){
			doPost(request,response);
		}
		//还有 HEAD  TRACE DELETE PUT等方法
	}	
}

