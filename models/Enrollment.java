package models;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private int enrollId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    public Enrollment() {}

    public Enrollment(Student student, int courseId, LocalDate enrollDate) {
        this.student = student;
        this.courseId = courseId;
        this.enrollDate = enrollDate;
    }

    public int getEnrollId()                        { return enrollId; }
    public void setEnrollId(int enrollId)           { this.enrollId = enrollId; }
    public Student getStudent()                     { return student; }
    public void setStudent(Student student)         { this.student = student; }
    public int getCourseId()                        { return courseId; }
    public void setCourseId(int courseId)           { this.courseId = courseId; }
    public LocalDate getEnrollDate()                { return enrollDate; }
    public void setEnrollDate(LocalDate enrollDate) { this.enrollDate = enrollDate; }
}