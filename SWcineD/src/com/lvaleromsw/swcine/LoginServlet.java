package com.lvaleromsw.swcine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lvaleromsw.swcine.dao.UserDAO;
import com.lvaleromsw.swcine.persistence.MyUser;
import com.lvaleromsw.swcine.persistence.SimpleUser;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String redirect = "/index.jsp";
		try{
		HttpSession sesion = request.getSession(true);
		String name = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String url = (String) sesion.getAttribute("url");
			
			UserDAO dao = UserDAO.getInstance();
			//SimpleUser user = dao.getUser(name,passwd);
			MyUser user = dao.getUser(name,passwd);
			if(user == null){
				System.out.println("usuario no valido");
			}else{
				sesion = request.getSession(true);
				sesion.setAttribute("username",user.getName());
				
				if(user.isAdmin()) sesion.setAttribute("admin","true");
				
				System.out.println("login correcto");
				//System.out.println(url);
				redirect = url;
			}
		}catch(Exception e){
			System.out.println("Error login");
		}finally{
			response.sendRedirect(redirect);
		}
	}
}
