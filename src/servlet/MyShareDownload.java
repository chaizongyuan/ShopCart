package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyShareDownload extends HttpServlet {
	static String SAVE_DIR="F:/myfile";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.print(" <form action=\"UploadServlet\" method=\"post\""+
		        "enctype=\"multipart/form-data\">"+
		        "姓名:<input type=\"text\" name=\"myName\" size=\"50\" /><br/>"+               
				"<input type=\"file\" name=\"myFile\" size=\"50\" /><br />"+
				"<input type=\"file\" name=\"myFile1\" size=\"50\" /><br /><br />"+
				"<input type=\"submit\" value=\"上传文件\" />"+
			"</form>");
		
		//获取文件路径
		File file=new File(SAVE_DIR);
		//获取文件集
		File[] files=file.listFiles();
		//遍历文件集
		for (java.io.File tfile:files) {
			out.println("<a href='DownloadServlet?fileName="+tfile.getName()+"'>"+tfile.getName()+"</a><br/>");
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
