package servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @description: HttpServletResponse
 * @author: WangZhiJun
 * @create: 2019-07-26 09:44
 **/
public class HttpServletResponse {
	private HttpServletRequest request; 
	private	OutputStream oos;
	
	private String webRoot = System.getProperty("user.dir")+File.separator+"static";
	public HttpServletResponse(HttpServletRequest request, OutputStream oos) {	
		this.request=request;
		this.oos = oos;
	}
	public PrintWriter getWriter(){
        return new PrintWriter(this.oos);
	}

	/**
	 * 响应文件输出HTML，JPEG，gif
	 * 响应码404,500,200
	 */
	public void sendRedirect() {
		int code = 200;
		//1.取出request中的uri
		String uri = this.request.getRequestURI();
		File file;
		String fileName;
		if(uri.endsWith("/")){
			fileName = uri+"index.html";
		}else{
			fileName = uri;
		}
		file = new File(webRoot,fileName);
		//2.拼装file 查看是否存在
		//不存在则404
		if(!file.exists()){
			file = new File(webRoot,"404.html");
			code = 404;
		}
		//3、存在，找到这个文件，读出来
		//4、发送文件响应，不同的文件返回不同类型
        if(file.getName().endsWith(".jpg")){
            send(file,"application/x-jpg",code);
        }else if(file.getName().endsWith(".jpe")||file.getName().endsWith(".jpeg")){
            send(file,"image/jpeg",code);
        }else if(file.getName().endsWith(".gif")){
            send(file,"image/gif",code);
        }else if(file.getName().endsWith(".css")){
            send(file,"text/css",code);
        }else if(file.getName().endsWith(".js")){
            send(file,"application/x-javascript",code);
        }else if(file.getName().endsWith(".swf")){
            send(file,"application/x-shockwave-flash",code);
        }else{
            send(file,"text/html",code);
        }
	}

    /**
     * 返回响应流
     */
	private void send(File file, String contentType, int code) {
		try {
			String responseHeader = genProtocol(file.length(),contentType,code);
			byte[] bs = readFile(file);
			
			this.oos.write(responseHeader.getBytes());
			this.oos.flush();
			this.oos.write(bs);
			this.oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				this.oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 读取文件
     */
	private byte[] readFile(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
			byte[] bs = new byte[1024];
			int length;
			while((length = fis.read(bs,0,bs.length))!=-1){
				baos.write(bs, 0, length);
				baos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
                fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

    /**
     * 拼接响应协议
     */
	private String genProtocol(long length, String contentType, int code) {
		String result = "HTTP/1.1 "+code+" OK\r\n";
		result+="Server: myTomcat\r\n";
		result+="Content-Type: "+contentType+";charset=utf-8\r\n"; 
		result+="Content-Length: "+length+"\r\n";
		result+="Date: "+new Date()+"\r\n"; 
		result+="\r\n";
		return result;
	}
}
