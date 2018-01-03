import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class deleted1 extends HttpServlet implements SingleThreadModel
{
	String url;
	PrintWriter out;
	String check,user;
	RequestDispatcher rd;
	ServletContext context;		

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
   	{
		try
		{
			context=getServletContext();
			HttpSession session=request.getSession(true);
			check=(String)session.getAttribute("check");
			user=(String)session.getAttribute("user");
			System.out.println("Session id="+session.getId());
			if(check==null)
			{
				System.out.println("Error");
		 	      	response.sendRedirect("http://localhost:8080/Mail");	
			}

			if((request.getParameter("deleteall")+"").equals("Delete All"))
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/deleted_deleteall");	
			}
			else  if((request.getParameter("restoreall")+"").equals("Restore All"))
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/deleted_restoreall");	
			}
			else if(request.getParameter("rb")==null)
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/deleted_select");	
			}
			else if((request.getParameter("content")+"").equals("Content"))
			{
				url="/servlet/deleted_content";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);	
			}
			else if((request.getParameter("delete")+"").equals("Delete"))
			{
				
				url="/servlet/deleted_delete";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);
			}
			else if((request.getParameter("restore")+"").equals("Restore"))
			{
				url="/servlet/deleted_restore";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);	
			}
			
		}
		catch(Exception e)
		{
			System.out.print("error"+e.getMessage());
		}
	}
}