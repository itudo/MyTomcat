package server;

import servlet.HttpServletRequest;
import servlet.HttpServletResponse;

/**
 * @description: 处理接口
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public interface Processor {
    /** 处理请求，给出响应
     * @param request 请求
     * @param response 响应
     */
	void process(HttpServletRequest request, HttpServletResponse response);
}
