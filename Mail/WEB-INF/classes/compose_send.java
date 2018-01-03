import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class compose_send extends HttpServlet implements SingleThreadModel
{
	RequestDispatcher rd;
	ServletContext context;
	String url1;
	String check;
	String url;
	Connection con;
	Statement stmt;
	ResultSet rs;
	String user;
	SimpleDateFormat dateFormat;
	Date date;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doPost(request,response);
	}	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			context=getServletContext();
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			date = new Date();
			HttpSession session=request.getSession(true);
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			url1="jdbc:mysql://localhost:3306/onlinemail?user=root&password=root";
			con=DriverManager.getConnection(url1);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("Select * from authentication");
			check=(String)session.getAttribute("check");
			user=(String)session.getAttribute("user");
			if(check==null)
			{
				System.out.println("Error");
		 	      	response.sendRedirect("http://localhost:8080/Mail");	
			}
			System.out.println("Session id="+session.getId());

			if((request.getParameter("back")+"").equals("  Back  "))
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/start");
			}
			else if((request.getParameter("send")+"").equals("  Send  "))
			{
				if(request.getParameter("to").equals(""))
				{
					url="/servlet/tonull";
					rd=context.getRequestDispatcher(url);
					rd.forward(request,response);
				}
				else if(request.getParameter("subject").equals(""))
				{
					url="/servlet/subjectnull";
					rd=context.getRequestDispatcher(url);
					rd.forward(request,response);
				}
				else if(request.getParameter("message").equals(""))
				{
					url="/servlet/messagenull";
					rd=context.getRequestDispatcher(url);
					rd.forward(request,response);
				}
				else
				{
					Boolean flag=false;
					while(rs.next())
					{
						if((rs.getString("Username")).equals(request.getParameter("to")))
						{	
							flag=true;
						}
					}
					if(flag==true)
					{
						stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						rs=stmt.executeQuery("Select * from inbox");
						rs.moveToInsertRow();
						rs.updateString("From",user);
						rs.updateString("Subject",request.getParameter("subject"));
						rs.updateString("Message",request.getParameter("message"));
						rs.updateString("Date",dateFormat.format(date));
						rs.updateString("Username",request.getParameter("to"));
						rs.insertRow();
						stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						rs=stmt.executeQuery("Select * from sent");
						rs.moveToInsertRow();
						rs.updateString("To",request.getParameter("to"));
						rs.updateString("Subject",request.getParameter("subject"));
						rs.updateString("Message",request.getParameter("message"));
						rs.updateString("Date",dateFormat.format(date));
						rs.updateString("Username",user);
						rs.insertRow();
						response.sendRedirect("http://localhost:8080/Mail/servlet/start1");
					}
					else
					{
						stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						rs=stmt.executeQuery("Select * from outbox");
						rs.moveToInsertRow();
						rs.updateString("To",request.getParameter("to"));
						rs.updateString("Subject",request.getParameter("subject"));
						rs.updateString("Message",request.getParameter("message"));
						rs.updateString("Date",dateFormat.format(date));
						rs.updateString("Username",user);
						rs.insertRow();
						response.sendRedirect("http://localhost:8080/Mail/servlet/start2");
					}
				}
			}
			else if((request.getParameter("save")+"").equals("  Save  "))
			{
				if((request.getParameter("to").equals(""))&&(request.getParameter("subject").equals(""))&&(request.getParameter("message").equals("")))
				{
					url="/servlet/savenull";
					rd=context.getRequestDispatcher(url);
					rd.forward(request,response);
				}
				else
				{
						stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						rs=stmt.executeQuery("Select * from drafts");
						rs.moveToInsertRow();
						rs.updateString("To",request.getParameter("to"));
						rs.updateString("Subject",request.getParameter("subject"));
						rs.updateString("Message",request.getParameter("message"));
						rs.updateString("Date",dateFormat.format(date));
						rs.updateString("Username",user);
						rs.insertRow();
						response.sendRedirect("http://localhost:8080/Mail/servlet/start3");
				}
			}
			
		}
		catch(Exception e)
		{
			System.out.print("error"+e.getMessage());
		}

	}

}