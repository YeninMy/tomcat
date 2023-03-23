package tomcat;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Setter
@Getter
public class Student {
    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        addStToDb(Student.this);
    }
    public static void addStToDb(final Student student) {
        try {
            String sql = "INSERT INTO students (id, name, age) VALUES " +
                    "(?, ?, ?)";
            try (Connection conn = AbstractRepository.createCon();
                 PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setInt(3, student.getAge());
                int rows = preparedStatement.executeUpdate();
                System.out.println(student);
            }
        } catch (Exception ex) {
            updateLec(student.getId(), student);
            System.out.println(student);
        }
    }


    public static void updateLec(int id, Student student) {
        try {
            String sql = "UPDATE students SET id=?, name =?, age = ? WHERE id = ?";
            try (Connection conn = AbstractRepository.createCon();
                 PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setInt(3, student.getAge());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("Connection failed..." + ex);
        }
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
