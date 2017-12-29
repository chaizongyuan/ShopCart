package cn.et;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtils;

public class QueryGoodServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public QueryGoodServlet() {
		super();
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
		String goodName=request.getParameter("goodName");
		if(goodName==null){
			goodName="";
		}
		PrintWriter out = response.getWriter();
		out.println( "<style type=\"text/css\">table{"+
	          "width:100%;border:1px solid black"+  
	          "}"+
	          "th,td{"+
	          "border:1px solid black"+
	      "}</style>");
		out.println("<form action=\"QueryGoodServlet\"method=get>"+
				"<input type=\"text\" name=\"goodName\" value=\""+goodName+"\"/><input type=\"submit\" value=\"搜索\"/>"+
				"</form>");
		
		out.println("<table>");
		out.println("<tr>");
		out.println("<th>显示图片</th>");
		out.println("<th>商品名称</th>");
		out.println("<th>商品单价</th>");
		out.println("<th>商品位置</th>");
		out.println("<th>操作</th>");
		out.println("</tr>");
	
		
		//查询逻辑
		String sql="select * from GOODS where name like '%"+goodName+"%'";
		try{
			PreparedStatement pst=conn.prepareStatement(sql);
			ResultSet res=pst.executeQuery();
			while(res.next()){
				String id=res.getString("ID");
				String name=res.getString("NAME");
				String imagePath=res.getString("IMAGEPATH");
				double price=res.getDouble("PRICE");
				String stock=res.getString("STOCK");
				out.println("<tr>");		
				out.println("<td><img src=\""+request.getContextPath()+imagePath+"\"/></td>");
				out.println("<td>"+name+"</td>");
				out.println("<td>"+price+"</td>");
				out.println("<td>"+stock+"</td>");
				out.println("<td><input type='button' value='购物车' onclick=\"window.location='CartServlet?id="+id+"';\"></td>");
				out.println("</tr>");
			}
		
		}catch (SQLException e) {
			e.printStackTrace();
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

	public static  Connection conn=null;
	public void destroy(){
		try{
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void init() throws ServletException {
		try {
			conn=DBUtils.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
