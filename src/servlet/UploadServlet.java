package servlet;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	static String SAVE_DIR="F:/myfile";
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

		response.setContentType("text/html");
		String myName = request.getParameter("myName");
		System.out.println("---"+myName);
		//接受文件
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//判断是否是文件上传请求
		if(isMultipart){
			//用于创建解析文件上传的工厂类
			DiskFileItemFactory factory=new DiskFileItemFactory();
			//默认上传文件  暂时存放在临时目录 System.getProperty("java.io.tmpdir")
			//也有可能是tomcat/temp目录
			//用于从请求中解析出文件
			ServletFileUpload upload = new ServletFileUpload(factory); 
			//解决文件名中文乱码问题
			upload.setHeaderEncoding("UTF-8");
			try {
				//解析到底是文本输入还是文件
				List<FileItem> items = upload.parseRequest(request);
				for(FileItem fi:items){
					//fi.isFormField()=true表示输入的文本框false文件
					if(fi.isFormField()){
						System.out.println(fi.getFieldName()+"=>"+fi.getString());
					}else{
						//获取文件名
						String fileName = fi.getName();
						InputStream is = fi.getInputStream();
						String destPath = SAVE_DIR+"/"+fileName;
						FileOutputStream fis=new FileOutputStream(destPath);
					    byte[] bt = new byte[1024];
					    int readN=-1;
					    while((readN=is.read(bt))!=-1){
					    	fis.write(bt,0,readN);
					    }
					    fis.close();
					    is.close();
					}
				}
				System.out.println(items.size());
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
		}
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
