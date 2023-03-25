package tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/add-student")
public class AddStudentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;");
        PrintWriter printWriter = response.getWriter();
        // Выводим форму для ввода данных о новом студенте
        printWriter.print("<h1>Add Student</h1>");
        printWriter.print("<form method='post'>");
        printWriter.print("<label>Name:</label>");
        printWriter.print("<input type='text' name='name'><br>");
        printWriter.print("<label>Lastname:</label>");
        printWriter.print("<input type='text' name='lastname'><br>");
        printWriter.print("<input type='submit' value='Add'>");
        printWriter.print("</form>");
        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем данные о новом студенте из запроса
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        Student student = new Student(name, lastname);
        // Добавляем нового студента в базу данных
        DataBaseUtils.addStudent(student);
        // Перенаправляем пользователя на страницу со списком студентов
        response.sendRedirect(request.getContextPath() + "/students");
    }
}