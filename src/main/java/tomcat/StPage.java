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
        response.setContentType("text/html;");
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();

        // read the CSS file
        String css = CSSFileReader.read("C:\\Users\\Mukhailo\\IdeaProjects\\tomcathw\\src\\main\\java\\tomcat\\style.css");


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
        out.println("<!DOCTYPE html>");
        out.print("<html>");
        out.print("<head>");
        out.println("<meta charset='UTF-8'>");
        out.print("<title>Students</title>");
        out.println("<style>");
        out.print(css);
        out.println("</style>");

        out.print("</head>");
        out.print("<body>");
        out.println("<div class='container'>");
        out.print("<h1>Students</h1>");

        // Выводим форму с опцией выбора сортировки
        out.print("<form сlass='sort' method='get'>");
        out.print("<label>Sort by:</label>");
        out.print("<select name='sortOption'>");
        out.print("<option value='lastNameAsc'>Last Name (A-Z)</option>");
        out.print("<option value='lastNameDesc'>Last Name (Z-A)</option>");
        out.print("</select>");
        out.print("<input type='submit' value='Sort'>");
        out.print("</form>");

        // Выводим студентов
        out.print("<ul>");
        for (Student student : students) {
            out.print("<li>" + student.toString() + "</li>");
        }
        out.print("</ul>");

        // Выводим форму для добавления нового студента
        out.print("<form method='post'>");
        out.print("<label>First Name:</label>");
        out.print("<input type='text' name='firstName'><br>");
        out.print("<label>Last Name:</label>");
        out.print("<input type='text' name='lastName'><br>");
        out.print("<input type='submit' value='Add Student'>");
        out.print("</form>");
        out.print("<br>");
        // Добавляем кнопку возврата в главное меню
        out.print("<a href='" + contextPath + "/'>Main menu</a><br>");
        out.println("</div>");
        out.print("</body>");
        out.print("</html>");

        out.close();
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

