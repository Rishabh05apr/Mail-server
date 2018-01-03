import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class drafts1 extends HttpServlet implements SingleThreadModel
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
				response.sendRedirect("http://localhost:8080/Mail/servlet/drafts_deleteall");	
			}
			else if(request.getParameter("rb")==null)
			{
				response.sendRedirect("http://localhost:8080/Mail/servlet/drafts_select");	
			}
			else if((request.getParameter("content")+"").equals("Content"))
			{
				url="/servlet/drafts_content";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);	
			}
			else if((request.getParameter("delete")+"").equals("Delete"))
			{
				
				url="/servlet/drafts_delete";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);
			}
			else if((request.getParameter("edit")+"").equals("Edit"))
			{
				url="/servlet/drafts_edit";
				rd=context.getRequestDispatcher(url);
				rd.forward(request,response);	
			}
			else if((request.getParameter("forward")+"").equals("Forward"))
			{
				url="/servlet/drafts_forward";
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