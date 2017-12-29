package cn.et;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CartServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CartServlet() {
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

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id=request.getParameter("id");
		String id1=request.getParameter("id1");
		HttpSession session=request.getSession();
		if(id1!=null){
			session.setAttribute(id1,(Integer)session.getAttribute(id1)- 1);
			if((Integer)session.getAttribute(id1)<=0){
				session.removeAttribute(id1);
			}
		}else {
					
			if (session.getAttribute(id)==null) {
				session.setAttribute(id, 1);
			}else{
				session.setAttribute(id,(Integer)session.getAttribute(id)+ 1);
			}
		}
		out.println("<a href='QueryGoodServlet'>继续购物</a>");
		out.println("购物数据:");
		out.println( "<style type=\"text/css\">table{"+
		          "width:100%;border:1px solid black"+  
		          "}"+
		          "th,td{"+
		          "border:1px solid black"+
		      "}</style>");
		
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>id</th>");
		out.println("<th>购买数量</th>");
		out.println("<th>显示图片</th>");
		out.println("<th>商品名称</th>");
		out.println("<th>商品单价</th>");
		out.println("<th>商品位置</th>");
		out.println("<th>操作</th>");
		out.println("</tr>");
		
		Enumeration em=session.getAttributeNames();
		while (em.hasMoreElements()) {
			String name=em.nextElement().toString();
			String sql="select * from GOODS where id="+name;
			String value=session.getAttribute(name).toString();
			Connection conn=QueryGoodServlet.conn;
			PreparedStatement pst;
			
			try {
				pst = conn.prepareStatement(sql);
				ResultSet res=pst.executeQuery();
				res.next();
				out.println("<tr>");
				out.println("<td>"+name+"</td>");
				out.println("<td>"+value+"</td>");
				out.println("<td><img src='"+request.getContextPath()+res.getString("IMAGEPATH")+"'></td>");
				out.println("<td>"+res.getString("NAME")+"</td>");
				out.println("<td>"+res.getString("PRICE")+"</td>");
				out.println("<td>"+res.getString("STOCK")+"</td>");
				out.println("<td><input type='button' value='+' onclick=\"window.location='CartServlet?id="+name+"';\"><input type='button' value='-' onclick=\"window.location='CartServlet?id1="+name+"';\"></td>");
				out.println("</tr>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		out.println("</table>");
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
