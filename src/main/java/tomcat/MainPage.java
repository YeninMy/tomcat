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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        // read the CSS file
        String css = CSSFileReader.read("C:\\Users\\Mukhailo\\IdeaProjects\\tomcathw\\src\\main\\java\\tomcat\\style.css");

        // add the HTML content
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Online School info</title>");
        out.println("<style>");
        out.println(css); // add the CSS content
        out.println("ul { list-style-type: none; }"); // add the new style to remove bullet points
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>"); // создание контейнера
        out.println("<h1>Online School info</h1>");
        out.println("<ul>");
        out.println("<li><a href='" + contextPath + "/lec'>Lectures</a></li>");
        out.println("<li><a href='" + contextPath + "/students'>Students</a></li>");
        out.println("</ul>");
        out.println("</div>"); // закрытие контейнера
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

}