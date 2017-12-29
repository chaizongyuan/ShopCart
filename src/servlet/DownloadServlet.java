package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadServlet extends HttpServlet {
	static String SAVE_DIR="F:/myfile";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		//�ļ����ص������
		OutputStream os = response.getOutputStream();
		String fileName = "servlet�γ�.txt";
		if (request.getParameter("fileName")!=null) {
			fileName = request.getParameter("fileName");
		}
		//������������ص��ļ���
		response.setHeader("Location",URLEncoder.encode(fileName,"UTF-8"));
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
		//���ļ���д��OutputStream
		FileInputStream fis=new FileInputStream(SAVE_DIR+"/"+fileName);
		byte[] bt = new byte[fis.available()];
		fis.read(bt);
		fis.close();
		os.write(bt);
		os.close();
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
