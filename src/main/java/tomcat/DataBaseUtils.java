package tomcat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    public static String getLecturesInString() {
        String l = "";
        for (String lecture : getLectures()) {
            l = l + lecture + "\n";
        }
        return l;
    }

    public static List<String> getLectures() {
        List<String> lectures = new ArrayList<>();
        try {
            String sql = "SELECT * FROM lectures";
            try (Connection conn = AbstractRepository.createCon();
                 Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    String lectureString = "Lecture [id=" + id + ", name=" + name + ", date=" + date + "]";
                    lectures.add(lectureString);
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
        return lectures;
    }

    public static int getCountLectures() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM lectures";
            try (Connection conn = AbstractRepository.createCon();
                 Statement statement = conn.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
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

    public static List<String> getStudents() {
        List<String> students = new ArrayList<>();
        try {
            String sql = "SELECT * FROM students";
            try (Connection conn = AbstractRepository.createCon();
                 Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String studentsString = "Student [id=" + id + ", name=" + name + ", age=" + age + "]";
                    students.add(studentsString);
                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
        return students;
    }

}
