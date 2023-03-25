package tomcat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Lecture {
    private int id;
    private String name;
    private LocalDate date;

    public Lecture() {
    }

    public Lecture(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public Lecture(int id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}