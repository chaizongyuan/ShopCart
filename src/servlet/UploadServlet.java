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
		//�����ļ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//�ж��Ƿ����ļ��ϴ�����
		if(isMultipart){
			//���ڴ��������ļ��ϴ��Ĺ�����
			DiskFileItemFactory factory=new DiskFileItemFactory();
			//Ĭ���ϴ��ļ�  ��ʱ�������ʱĿ¼ System.getProperty("java.io.tmpdir")
			//Ҳ�п�����tomcat/tempĿ¼
			//���ڴ������н������ļ�
			ServletFileUpload upload = new ServletFileUpload(factory); 
			//����ļ���������������
			upload.setHeaderEncoding("UTF-8");
			try {
				//�����������ı����뻹���ļ�
				List<FileItem> items = upload.parseRequest(request);
				for(FileItem fi:items){
					//fi.isFormField()=true��ʾ������ı���false�ļ�
					if(fi.isFormField()){
						System.out.println(fi.getFieldName()+"=>"+fi.getString());
					}else{
						//��ȡ�ļ���
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