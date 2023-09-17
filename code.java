package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servlet/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve the stored captcha code from the session
        String codes = req.getSession().getAttribute("codes").toString();
        
        // Retrieve the user-entered code from the request parameter
        String mycode = req.getParameter("checkcode");
        
        // Set the character encoding of the response to UTF-8
        resp.setCharacterEncoding("utf-8");
        
        // Create a PrintWriter to send the response back to the client
        PrintWriter out = resp.getWriter();
        
        // Compare the user-entered code with the code from the session
        if (mycode.toUpperCase().equals(codes)) {
            // If the codes match (ignoring case), print "验证码输入正确！"
            out.println("验证码输入正确！");
        } else {
            // If the codes do not match, print "验证码输入错误！"
            out.println("验证码输入错误！");
        }
        
        // Flush and close the PrintWriter to send the response
        out.flush();
        out.close();
    }
}
