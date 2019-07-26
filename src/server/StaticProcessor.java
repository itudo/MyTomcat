package server;

import servlet.HttpServletRequest;
import servlet.HttpServletResponse;

/**
 * @description: 静态处理类
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class StaticProcessor implements Processor {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) {
		response.sendRedirect();
	}

}
