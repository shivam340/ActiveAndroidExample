package libraries.active_android.db_model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by shivam on 6/2/15.
 */
@Table(name = "College")
public class College extends Model{

    @Column(name = "college_id", unique = true)
    private int collegeId;
    @Column(name = "college_name")
    private String collegeName;

    // active android requires default constructor.
    public College() {
        super();
    }

    public College(int collegeId, String collegeName) {
        super();
        this.collegeId = collegeId;
        this.collegeName =collegeName;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public static List<College> getAllCollegeInfo() {
        return new Select().from(College.class).execute();
    }

    public static College getCollege(int collegeId) {
        return new Select().from(College.class).where("college_id = ?", collegeId).executeSingle();
    }


    // Used to return items from another table based on the foreign key
    public List<Student> getAllStudent() {
        return getMany(Student.class, "College");
    }



}