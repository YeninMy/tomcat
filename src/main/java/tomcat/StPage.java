package tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/students")
public class StPage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        response.setContentType("text/html;");
        PrintWriter printWriter = response.getWriter();

        String sortOption = request.getParameter("sortOption");

        // Получаем список студентов
        List<Student> students = DataBaseUtils.getStudents();

        // Сортируем студентов в соответствии с выбранной опцией
        if (sortOption != null) {
            if (sortOption.equals("lastNameAsc")) {
                students = students.stream().sorted(Comparator.comparing(Student::getLastName)).collect(Collectors.toList());
            } else if (sortOption.equals("lastNameDesc")) {
                students = students.stream().sorted(Comparator.comparing(Student::getLastName).reversed()).collect(Collectors.toList());
            }
        }

        // Выводим заголовок
        printWriter.print("<h1>Students</h1>");

        // Выводим форму с опцией выбора сортировки
        printWriter.print("<form method='get'>");
        printWriter.print("<label>Sort by:</label>");
        printWriter.print("<select name='sortOption'>");
        printWriter.print("<option value='lastNameAsc'>Last Name (A-Z)</option>");
        printWriter.print("<option value='lastNameDesc'>Last Name (Z-A)</option>");
        printWriter.print("</select>");
        printWriter.print("<input type='submit' value='Sort'>");
        printWriter.print("</form>");

        // Выводим студентов
        for (Student student : students) {
            printWriter.print(student.toString() + "<br>");
        }

        // Выводим форму для добавления нового студента
        printWriter.print("<form method='post'>");
        printWriter.print("<label>First Name:</label>");
        printWriter.print("<input type='text' name='firstName'><br>");
        printWriter.print("<label>Last Name:</label>");
        printWriter.print("<input type='text' name='lastName'><br>");
        printWriter.print("<input type='submit' value='Add Student'>");
        printWriter.print("</form>");

        // Добавляем кнопку возврата в главное меню
        printWriter.print("<a href='" + contextPath + "/'>Main menu</a><br>");

        printWriter.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // Создаем нового студента и добавляем его в базу данных
        Student student = new Student(firstName, lastName);
        DataBaseUtils.addStudent(student);

        // Перенаправляем пользователя на страницу со списком студентов
        response.sendRedirect(request.getContextPath() + "/students");
    }
}
