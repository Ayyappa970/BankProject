package com.jsp.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/Otp1")
public class Otp1 extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String eotp=req.getParameter("o");
	int otp=Integer.parseInt(eotp);
	HttpSession session=req.getSession();
	Integer gotp = (Integer) session.getAttribute("otp");
	String accNum = (String) session.getAttribute("ac");
	String fname = (String) session.getAttribute("fn");
	String lname = (String) session.getAttribute("ln");
	String faname = (String) session.getAttribute("fna");
	String mob = (String) session.getAttribute("mb");
	String pnum = (String) session.getAttribute("pn");
	String anum = (String) session.getAttribute("an");
	String add = (String) session.getAttribute("ad");
	String gen = (String) session.getAttribute("g");
	double at=(Double) session.getAttribute("ammt");
	PrintWriter writer=resp.getWriter();
	resp.setContentType("text/html");
	if (otp==gotp) {
		writer.println("<body style='background-image: linear-gradient(90deg,green,red)'>");
		writer.println("<center><h1>Account Details</h1>");
		writer.println("<table style='border:2px solid black;border-collapse:collapse'><tr><th style='border:2px solid black'>Account Number</th>");
		writer.println("<th style='border:2px solid black'>First Name</th>");
		writer.println("<th style='border:2px solid black'>Last Name</th>");
		writer.println("<th style='border:2px solid black'>Father Name</th>");
		writer.println("<th style='border:2px solid black'>Mobile Number</th>");
		writer.println("<th style='border:2px solid black'>Pan Number</th>");
		writer.println("<th style='border:2px solid black'>Aadhar Number</th>");
		writer.println("<th style='border:2px solid black'>Address</th>");
		writer.println("<th style='border:2px solid black'>Gender</th>");
		writer.println("<th style='border:2px solid black'>Amount</th></tr>");
		writer.println("<tr style='text-align:center'><td style='border:2px solid black'>"+accNum.substring(0,3)+"XXXXX"+accNum.substring(8, 10)+"</td>");
		writer.println("<td style='border:2px solid black'>"+fname+"</td>");
		writer.println("<td style='border:2px solid black'>"+lname+"</td>");
		writer.println("<td style='border:2px solid black'>"+faname+"</td>");
		writer.println("<td style='border:2px solid black'>"+mob.substring(0, 2)+"XXXXXXX"+mob.substring(mob.length()-1, mob.length())+"</td>");
		writer.println("<td style='border:2px solid black'>"+pnum.substring(0, 1)+"XXXXXXXX"+pnum.substring(pnum.length()-1, pnum.length())+"</td>");
		writer.println("<td style='border:2px solid black'>"+anum.substring(0, 3)+"XXXXXXX"+anum.substring(10, 12)+"</td>");
		writer.println("<td style='border:2px solid black'>"+add+"</td>");
		writer.println("<td style='border:2px solid black'>"+gen+"</td>");
		writer.println("<td style='border:2px solid black'>"+at+"</td></tr></table></center></body>");
	} else {
		RequestDispatcher dispatcher=req.getRequestDispatcher("Otp1.html");
		dispatcher.include(req, resp);
		writer.println("<center><h2>Enter valid Otp</h2></center>");
	}
}
}
