package tomcat;


import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractRepository {
    @SneakyThrows
    public static Connection createCon() {
        String url = "jdbc:postgresql://localhost:5432/online_school";
        String username = "postgres";
        String password = "root";
        Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

}