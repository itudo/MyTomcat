import servlet.HttpServlet;
import servlet.HttpServletRequest;
import servlet.HttpServletResponse;
import servlet.HttpSession;

import java.io.PrintWriter;


/**
 * @author WangZhiJun
 */
public class User extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		PrintWriter out = response.getWriter();
		String jsession = request.getJsessionid();
		if(jsession!=null) {
			out.print("HTTP/1.1 200 OK\r\nSet-Cookie: JSESSIONID="+jsession+"\r\n\r\n");
		} else {
			out.print("HTTP/1.1 200 OK\r\n\r\n");
		}
		out.print("<html>");
		out.print("<body>");
		out.print("welcome! "+ username + "\r\n\r\n");
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
