package tomcat;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    public static List<Lecture> getLectures() {
        List<Lecture> lectures = new ArrayList<>();
        try {
            String sql = "SELECT lecture_id, lecture_name, lecture_date FROM lectures";
            try (Connection conn = AbstractRepository.createCon();
                 Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("lecture_id");
                    String name = resultSet.getString("lecture_name");
                    LocalDate date = resultSet.getDate("lecture_date").toLocalDate();
                    Lecture l = new Lecture(id, name, date);
                    lectures.add(l);
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
        return lectures;
    }

    public static Lecture getLecById(int id) {
        Lecture lecture = new Lecture();
        try {
            String sql = "SELECT * FROM lectures WHERE id = ?";
            try (Connection conn = AbstractRepository.createCon();
                 PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                lecture.setId(id);
                lecture.setName(resultSet.getString("name"));
                lecture.setDate(resultSet.getDate("date").toLocalDate());

            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
        return lecture;
    }

    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT student_id, first_name, last_name FROM students";
            try (Connection conn = AbstractRepository.createCon();
                 Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("student_id");
                    String name = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Student s = new Student(id, name, lastName);
                    students.add(s);
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
        return students;
    }

    public static void addLecture(Lecture lecture) {
        try {
            String sql = "INSERT INTO lectures (lecture_name, lecture_date) VALUES (?, ?)";
            try (Connection conn = AbstractRepository.createCon();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, lecture.getName());
                statement.setDate(2, Date.valueOf(lecture.getDate()));
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Connection failed..." + ex);
        }
    }

    public static void addStudent(Student student) {
        try {
            String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
            try (Connection conn = AbstractRepository.createCon();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, student.getName());
                statement.setString(2, student.getLastName());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Connection failed..." + ex);
        }
    }
}
