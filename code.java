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





import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private LoginServlet servlet;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new LoginServlet();
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);

        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoGet_CorrectCode() throws Exception {
        when(request.getParameter("checkcode")).thenReturn("ABC"); // User-entered code
        when(session.getAttribute("codes")).thenReturn("ABC"); // Correct captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入正确！", responseContent);
    }

    @Test
    public void testDoGet_IncorrectCode() throws Exception {
        when(request.getParameter("checkcode")).thenReturn("XYZ"); // User-entered code
        when(session.getAttribute("codes")).thenReturn("ABC"); // Correct captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入错误！", responseContent);
    }

    @Test
    public void testDoGet_NullSessionAttribute() throws Exception {
        when(request.getParameter("checkcode")).thenReturn("ABC"); // User-entered code
        when(session.getAttribute("codes")).thenReturn(null); // No stored captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入错误！", responseContent);
    }

    @Test
    public void testDoGet_EmptyUserEnteredCode() throws Exception {
        when(request.getParameter("checkcode")).thenReturn(""); // Empty user-entered code
        when(session.getAttribute("codes")).thenReturn("ABC"); // Correct captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入错误！", responseContent);
    }

    @Test
    public void testDoGet_MissingUserEnteredCode() throws Exception {
        when(request.getParameter("checkcode")).thenReturn(null); // Missing user-entered code
        when(session.getAttribute("codes")).thenReturn("ABC"); // Correct captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入错误！", responseContent);
    }

    @Test
    public void testDoGet_CaseInsensitiveCode() throws Exception {
        when(request.getParameter("checkcode")).thenReturn("abc"); // User-entered code in different case
        when(session.getAttribute("codes")).thenReturn("ABC"); // Correct captcha code

        servlet.doGet(request, response);
        writer.flush();

        String responseContent = stringWriter.toString().trim();
        assertEquals("验证码输入正确！", responseContent);
    }
}

