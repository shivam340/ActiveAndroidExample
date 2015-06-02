package libraries.active_android.db_model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by shivam on 6/2/15.
 */

@Table(name = "student")
public class Student extends Model {

    @Column(name = "student_id", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    private int studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "College", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    College college;

    public Student() {
        super();
    }


    public Student(int studentId, String studentName, College college){
        super();
        this.studentId = studentId;
        this.studentName = studentName;
        this.college = college;
    }


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public static List<Student> getAllStudent() {
        return new Select().from(Student.class).execute();
    }

    public static Student getStudent(int studentId) {
        return new Select().from(Student.class).where("student_id = ?", studentId).executeSingle();
    }

}
