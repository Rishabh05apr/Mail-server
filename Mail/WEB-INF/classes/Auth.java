import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Auth extends HttpServlet implements SingleThreadModel
{
	String url;
	Connection con;
	Statement stmt;
	ResultSet rs;
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url="jdbc:mysql://localhost:3306/onlinemail?user=root&password=root";
			con=DriverManager.getConnection(url);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from authentication");
			Boolean flag=false;
			if(((request.getParameter("username")+"")!=null)&&((request.getParameter("password")+"")!=null))
			{
					while(rs.next())
					{
						if((rs.getString("Username").equals(request.getParameter("username")+""))&&(rs.getString("Password").equals(request.getParameter("password")+"")))
						{
								HttpSession session=request.getSession(true);
								System.out.println("Session id="+session.getId());
								session.setAttribute("check","true");
								session.setAttribute("user",request.getParameter("username"));
								response.sendRedirect("http://localhost:8080/Mail/servlet/start");	
						}
					}
					if(flag==false)
					{
						response.sendRedirect("http://localhost:8080/Mail/servlet/Auth1");	
					}
			}
			else
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/Auth1");	
			}
		}
		catch(Exception e)
		{}

	}
}