package tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/add-lecture")
public class AddLectureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры запроса
        String lectureName = request.getParameter("lectureName");
        LocalDate lectureDate = LocalDate.parse(request.getParameter("lectureDate"));
        // Создаем новую лекцию и добавляем в базу данных
        Lecture lecture = new Lecture(0, lectureName, lectureDate);
        DataBaseUtils.addLecture(lecture);
        // Перенаправляем на страницу со списком лекций
        response.sendRedirect(request.getContextPath() + "/lec");
    }
}