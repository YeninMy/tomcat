package tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class MainPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;");
        PrintWriter printWriter = response.getWriter();
        String contextPath = request.getContextPath();

        printWriter.print("<h1>Online School info</h1>");
        printWriter.print("<ul>");
        printWriter.print("<li><a href=\"" + contextPath + "/lec\">Lectures</a></li>");
        printWriter.print("<li><a href=\"" + contextPath + "/students\">Students</a></li>");
//        printWriter.print("<li><a href=\"" + contextPath + "/t\">Teachers</a></li>");
//        printWriter.print("<li><a href=\"" + contextPath + "/m\">Materials</a></li>");
        printWriter.print("</ul>");

        printWriter.close();
    }
}
