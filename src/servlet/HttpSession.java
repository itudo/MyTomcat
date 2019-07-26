package servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: HttpSession
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class HttpSession {
	private Map<String,Object> map = new HashMap<String,Object>();
	private String id;
	
	HttpSession(String id) {
		super();
		this.id = id;
	}


	public String getId() {
		return id;
	}

	public void setAttribute(String name,Object value) {
		this.map.put(name, value);
	}
	
	public Object getAttribute(String key) {
		return map.get(key);
	}
	
	public void removeAttribute(String name) {
		this.map.remove(name);
	}
}
