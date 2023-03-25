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
        String contextPath = request.getContextPath();
        response.setContentType("text/html;");
        PrintWriter printWriter = response.getWriter();

        String sortOption = request.getParameter("sortOption");

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

        // Выводим заголовок
        printWriter.print("<h1>Lectures</h1>");

        // Выводим форму с опцией выбора сортировки
        printWriter.print("<form method='get'>");
        printWriter.print("<label>Sort by:</label>");
        printWriter.print("<select name='sortOption'>");
        printWriter.print("<option value='date'>Date</option>");
        printWriter.print("<option value='name'>Name</option>");
        printWriter.print("</select>");
        printWriter.print("<input type='submit' value='Sort'>");
        printWriter.print("</form>");

        // Выводим лекции
        for (Lecture lecture : lectures) {
            printWriter.print(lecture.toString() + "<br>");
        }

        // Выводим форму для добавления лекции
        printWriter.print("<h2>Add lecture</h2>");
        printWriter.print("<form method='post'>");
        printWriter.print("<label>Name:</label>");
        printWriter.print("<input type='text' name='name'><br>");
        printWriter.print("<label>Date:</label>");
        printWriter.print("<input type='date' name='date'><br>");
        printWriter.print("<input type='submit' value='Add'>");
        printWriter.print("</form>");

        // Добавляем кнопку возврата в главное меню
        printWriter.print("<a href='" + contextPath + "/'>Main menu</a><br>");

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        LocalDate date = LocalDate.parse(request.getParameter("date"));

        Lecture lecture = new Lecture(name, date);
        DataBaseUtils.addLecture(lecture);

        response.sendRedirect(request.getContextPath() + "/lec");
    }
}

