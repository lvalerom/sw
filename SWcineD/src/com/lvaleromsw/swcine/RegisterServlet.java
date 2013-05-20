package com.lvaleromsw.swcine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvaleromsw.swcine.dao.UserDAO;
import com.lvaleromsw.swcine.persistence.SimpleUser;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String name = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String repasswd = request.getParameter("repasswd");
		String email = request.getParameter("email");
		
		if(checkPass(passwd,repasswd)){
		
			SimpleUser user = new SimpleUser(name, passwd, email);
			
			UserDAO dao = UserDAO.getInstance();
			dao.createUser(user);
		}
		
		response.sendRedirect("/index.html");
	}
	
	private boolean checkPass(String pass, String repass){
		
		return pass.equals(repass);
	}
}