package tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/lec")
public class LecPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        String sortOption = request.getParameter("sortOption");
        String css = CSSFileReader.read("C:\\Users\\Mukhailo\\IdeaProjects\\tomcathw\\src\\main\\java\\tomcat\\style.css");
        // Получаем список лекций
        List<Lecture> lectures = DataBaseUtils.getLectures();

        // Сортируем лекции в соответствии с выбранной опцией
        if (sortOption != null) {
            if (sortOption.equals("date")) {
                lectures = lectures.stream().sorted(Comparator.comparing(Lecture::getDate)).collect(Collectors.toList());
            } else if (sortOption.equals("name")) {
                lectures = lectures.stream().sorted(Comparator.comparing(Lecture::getName)).collect(Collectors.toList());
            }
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Lectures</title>");
        out.println("<style>");
        out.println(css); // add the CSS content
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        // Выводим заголовок
        out.print("<h1>Lectures</h1>");

        // Выводим форму с опцией выбора сортировки
        out.print("<form method='get'>");
        out.print("<label>Sort by:</label>");
        out.print("<select name='sortOption'>");
        out.print("<option value='date'>Date</option>");
        out.print("<option value='name'>Name</option>");
        out.print("</select>");
        out.print("<input type='submit' value='Sort'>");
        out.print("</form>");

        // Выводим лекции
        out.print("<ul>");
        for (Lecture lecture : lectures) {
            out.print("<li>" + lecture.toString() + "</li>");
        }
        out.print("</ul>");

        // Выводим форму для добавления лекции
        out.print("<h2>Add lecture</h2>");
        out.print("<form method='post'>");
        out.print("<label>Name:</label>");
        out.print("<input type='text' name='name'><br>");
        out.print("<label>Date:</label>");
        out.print("<input type='date' name='date'><br>");
        out.print("<input type='submit' value='Add'>");
        out.print("</form>");
        out.print("<br>");
        // Добавляем кнопку возврата в главное меню
        out.print("<a href='" + contextPath + "/'>Main menu</a><br>");
        out.println("</div>"); // закрытие контейнера
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        LocalDate date = LocalDate.parse(request.getParameter("date"));

        Lecture lecture = new Lecture(name, date);
        DataBaseUtils.addLecture(lecture);

        response.sendRedirect(request.getContextPath() + "/lec");
    }
}

