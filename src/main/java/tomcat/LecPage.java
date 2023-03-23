package tomcat;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lec")
public class LecPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;");
        PrintWriter printWriter = response.getWriter();
        for (String lecture : DataBaseUtils.getLectures()) {
            printWriter.print(lecture + "<br>");
        }
        printWriter.close();
    }
}