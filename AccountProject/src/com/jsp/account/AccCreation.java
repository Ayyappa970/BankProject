package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/AccCreation")
public class AccCreation extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String accName=req.getParameter("fn");
	String accLName=req.getParameter("ln");
	String accFName=req.getParameter("fna");
	String mob=req.getParameter("mb");
	String pan=req.getParameter("pn");
	String adhar=req.getParameter("an");
	String add=req.getParameter("as");
	String gen=req.getParameter("a");
//	String accNum="9"+mob.substring(4, 8)+pan.substring(5, 8)+adhar.substring(6, 10);
	String accNum="1"+mob.substring(6,7)+mob.substring(3,4)+mob.substring(1,2)+adhar.substring(4, 5)+adhar.substring(6, 7)+adhar.substring(1, 2)+adhar.substring(3, 4)+adhar.substring(6,7)+adhar.substring(5,6);
	String pass=req.getParameter("ps");
	double am=0.0;
	PrintWriter writer=resp.getWriter();
	resp.setContentType("text/html");
	String url="jdbc:mysql://localhost:3306/teca43?user=root&password=12345";
	String insert="insert into account values (?,?,?,?,?,?,?,?,?,?,?)";
	if (accName.length()>1 && accLName.length()>1 && accFName.length()>1 && mob.length()==10 && pan.length()==10 && adhar.length()==12 && add.length()>1 && gen!=null) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(insert);
			ps.setString(1, accNum);
			ps.setString(2, accName.toUpperCase());
			ps.setString(3, accLName.toUpperCase());
			ps.setString(4, accFName.toUpperCase());
			ps.setString(5, mob);
			ps.setString(6, pan.toUpperCase());
			ps.setString(7, adhar);
			ps.setString(8, add.toUpperCase());
			ps.setString(9, gen.toUpperCase());
			ps.setDouble(10, am);
			ps.setString(11, pass.toUpperCase());
			try {
				int result=ps.executeUpdate();
				if (result!=0) {
					RequestDispatcher dispatcher=req.getRequestDispatcher("BankProject.html");
					dispatcher.include(req, resp);
					writer.println("<body style='background-image: linear-gradient(90deg,green,red)'><center><h1 style='margin-top:20px'>Your Account was Created</h1></center></body>");
				}
			} catch (Exception e) {
				RequestDispatcher dispatcher=req.getRequestDispatcher("AccCreation.html");
				dispatcher.include(req, resp);
				writer.println("<center><h2>Mobile Number already have an Account</h2></center>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else {
		RequestDispatcher dispatcher=req.getRequestDispatcher("AccCreation.html");
		dispatcher.include(req, resp);
		writer.println("<center><h2>Please enter proper details</h2></center>");
	}
	}
}
