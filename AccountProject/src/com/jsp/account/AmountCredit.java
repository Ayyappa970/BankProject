package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/AmountCredit")
public class AmountCredit extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String amt=req.getParameter("am");
	double uamt=Double.parseDouble(amt);
	String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
	HttpSession session=req.getSession();
	String mob = (String) session.getAttribute("mb");
	String pass = (String) session.getAttribute("pas");
	Double damt=(Double) session.getAttribute("dat");
	resp.setContentType("text/html");
	PrintWriter writer=resp.getWriter();
	if (uamt>0) {
		if (uamt<=50000) {
			damt+=uamt;
			String update="update account set amount=? where mobilenum=? and password=?";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement psu=connection.prepareStatement(update);
				psu.setDouble(1, damt);
				psu.setString(2, mob);
				psu.setString(3, pass);
				int num=psu.executeUpdate();
				if (num!=0) {
					RequestDispatcher dispatcher=req.getRequestDispatcher("BankProject.html");
					dispatcher.include(req, resp);
					writer.println("<center><h1 style='margin-top:20px'>"+uamt+" Credited Successfully</h1></center>");
				} else {
					RequestDispatcher dispatcher=req.getRequestDispatcher("AmountCredit.html");
					dispatcher.include(req, resp);
					writer.println("Amount not Credited");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
				RequestDispatcher dispatcher=req.getRequestDispatcher("AmountCredit.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1>Please credit Amount under 50000 only</h1></center>");
			}
	}else {
			RequestDispatcher dispatcher=req.getRequestDispatcher("AmountCredit.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1>Enter Valid Amount</h1></center>");
		}
}
}
