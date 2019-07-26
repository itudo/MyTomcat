package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @description: HttpServletRequest
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class HttpServletRequest {

    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求地址
     */
	private String requestURI;
	
	private String jsessionid = null;

    /**
     * 请求体
     */
	private String content;
    /**
     * 请求头
     */
	private Map<String,String> headers = new Hashtable<>();
    /**
     * 请求参数
     */
	private Map<String,String> parameter = new Hashtable<>();
    /**
     * 请求cookie信息
     */
	private List<Cookie> cookies = new ArrayList<>();
	
	
	public String getJsessionid() {
		return jsessionid;
	}
	public List<Cookie> getCookies() {
		return cookies;
	}
	public String getParameter(String key) {
		return parameter.get(key);
	}

	public HttpServletRequest(InputStream iis) {
        //一次性读完所有请求信息
		StringBuilder sb = new StringBuilder();
		int length = -1;
		byte[] bs = new byte[100*1024];
		try {
			length = iis.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取客户请求异常");
        }
		//将bs中的字节数据转为char
		for(int i = 0;i<length;i++){
			sb.append((char)bs[i]);
		}
		content = sb.toString();
		parseProtocol();
	}
	
	public HttpSession getSession() {
		HttpSession session;
		//判读jsessionid是否存在，
		//不存在 ，则创建一个httpSession存起来再返回
		if(jsessionid==null|| !ServletContext.sessions.containsKey(jsessionid)) {
			
			jsessionid = UUID.randomUUID().toString();
			session = new HttpSession(jsessionid);
			ServletContext.sessions.put(jsessionid, session);
		} else {
			//存在则取出httpSession返回
			session = ServletContext.sessions.get(jsessionid);
		}
		return session;
	}

    /**解析协议
     * 从iis 中取出请求头，请求实体，解析数据存到属性中
     */
    private void parseProtocol() {
		String[] ss = content.split(" ");
		this.method = ss[0];
		this.requestURI = ss[1];
		
		//解析参数，存到parameter的map中
		parseParameter();
		//解析出header存好
		//取出协议中的 Cookie：xxxx  ,如果有则说明已经生成过Cookie  没有则表明是第一次请求，要生成Cookie编号
		parseHeader();
		//从headers中取cookie
		parseCookie();
		//从cookie中取出jsessionid
		jsessionid = parseJSessionId();
	}

	private String parseJSessionId() {
		if(cookies!=null&&cookies.size()>0) {
			for(Cookie c:cookies) {
				if("JSESSIONID".equals(c.getName())) {
					return c.getValue();
				}
			}
		} 
		return null;
	}

    /**
     * headers中取出cookie，然后在解析出cookie对象存在cookies中
     */
	private void parseCookie() {
		if(headers==null&&headers.size()<=0){
			return;
		}
		//从headers中取出键为cookie的 
		String cookieValue = headers.get("Cookie");
		if(cookieValue == null || cookieValue.length()<=0) {
			return;
		}
		String[] cvs = cookieValue.split(": ");
		if(cvs.length > 0) {
			for(String cv:cvs) {
				String[] str = cv.split("=");
				if(str.length > 0) {
					String key = str[0];
					String value = str[1];
					Cookie c = new Cookie(key,value);
					cookies.add(c);
				}
			}
		}
	}
	private void parseHeader() {
        //请求头
		String[] parts = this.content.split("\r\n\r\n");
        //GET /请求地址 HTTP/1.1
		String[] headerss = parts[0].split("\r\n");
			for(int i = 1;i<headerss.length;i++){
				String[] headPair = headerss[i].split(": ");
                //Host: localhost:8888     Connection: keep-alive ...
				headers.put(headPair[0], headPair[1]);
			}
	}
	public String getHeader(String name){
		return headers.get(name);
	}

    /**
     * 取参数
     */
	private void parseParameter() {
		//requestURI: user.action?name=z&password=a
		int index = this.requestURI.indexOf("?");
		//有?的话
		if(index>=1){
			String[] pairs = this.requestURI.substring(index+1).split("&");
			for(String p:pairs){
				String[] po = p.split("=");
				parameter.put(po[0], po[1]);
			}
		}
		if(this.method.equals("POST")){
			String[] parts = this.content.split("\r\n\r\n");
			String entity = parts[1];
			String[] pairs = entity.split("&");
			for(String p:pairs){
				String[] po = p.split("=");
				parameter.put(po[0], po[1]);
			}
		}
	}
	public String getMethod() {
		return method;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	
	
}
