import servlet.HttpServlet;
import servlet.HttpServletRequest;
import servlet.HttpServletResponse;
import servlet.HttpSession;
import java.io.PrintWriter;


/**
 * @author WangZhiJun
 */
public class Login extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+"\t"+password);
		
		System.out.println(request.getCookies());
		
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		
		PrintWriter out = response.getWriter();
		String jsession = request.getJsessionid();
		if(jsession!=null) {
			out.print("HTTP/1.1 200 OK\r\nSet-Cookie: JSESSIONID="+jsession+"\r\n\r\n");
		} else {
			out.print("HTTP/1.1 200 OK\r\n\r\n");
		}
		out.print("<html>");
		out.print("<body>");
		out.print("login success!   \r\n");
		out.print("sessionId:" + jsession);
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		doPost(request,response);
	}
	
}
