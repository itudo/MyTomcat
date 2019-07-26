package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: Tomcat服务器启动类
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class MyTomcat {
	
	public static void main(String[] args) {
		MyTomcat kc = new MyTomcat();
		kc.startServer();
	}
	private void startServer() {
		System.out.println("服务器准备启动");
		//TODO:在这里加入一个xml解析模块，读取server.xml,读取port信息
		int port = getServerPort();
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			System.out.println("服务器启动成功，监听："+ss.getLocalPort()+"端口");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(port+"被占用，服务器启动失败");
			//TODO:自动选定另一个tcp的空闲端口
			System.exit(0);
		}

		while(true){
			Socket s=null;
			try {
				s = ss.accept();
				System.out.println("客户端"+s.getRemoteSocketAddress()+"联结上来了");
				//启动线程来处理这个客户端的请求与响应
				ServerService server = new ServerService(s);
				//TODO:不要显式创建线程，使用线程池
				Thread t = new Thread(server);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
                System.out.println("客户端"+s.getRemoteSocketAddress()+"掉线了");
			}
		}
	}
	private int getServerPort() {
		return 8888;
	}
}
