package server;

import servlet.HttpServletRequest;
import servlet.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description: 处理接口
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class ServerService implements Runnable{
	private Socket s;

    ServerService(Socket s) {
		super();
		this.s = s;
	}

	@Override
	public void run() {
        InputStream iis;
        OutputStream oos;
        try {
			iis = s.getInputStream();
			oos = s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端"+this.s.getRemoteSocketAddress()+"掉线");
			return;
		}
		//http是一个基于请求与响应的协议
		//1、创建一个请求对象
		HttpServletRequest request = new HttpServletRequest(iis);
		//2、创建一个响应对象
		HttpServletResponse response = new HttpServletResponse(request, oos);
        String uri = request.getRequestURI();
		//判断是动态的还是静态的请求
		Processor processor;
		//如果是动态的请求，则创建DynamicProcessor
        //未简化时间，此处认为请求信息里有.action为动态请求
		if(uri.indexOf(".action")>0){
			processor = new DynamicProcessor();
		}else{
			//如果是静态的请求，则创建StaticProcessor
			processor = new StaticProcessor();
		}
		processor.process(request, response);
		try {
			//http是无状态的短连接
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
