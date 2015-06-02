package libraries.active_android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import libraries.active_android.db_model.College;
import libraries.active_android.db_model.Student;


public class MainActivity extends ActionBarActivity {

    private int mStudentId = -1;
    private String mStudentName = null;
    private int mCollegeId = -1;
    private String mCollegeName = null;

    @InjectView(R.id.edt_college_id)
    protected EditText editTextCollegeId;
    @InjectView(R.id.edt_college_name)
    protected EditText editTextCollegeName;
    @InjectView(R.id.edt_student_id)
    protected EditText editTextStudentId;
    @InjectView(R.id.edt_student_name)
    protected EditText editTextStudentName;
    @InjectView(R.id.txt_status_message)
    protected TextView textViewStatusMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private boolean isValidData() {

        mStudentName = editTextStudentName.getText().toString();
        mCollegeName = editTextCollegeName.getText().toString();
        String sid = editTextStudentId.getText().toString();
        String cid = editTextCollegeId.getText().toString();

        Log.d("Student Id is ", "" + sid);
        Log.d("Student name is ", "" + mStudentName);
        Log.d("College Id is ", "" + cid);
        Log.d("College name is ", "" + mCollegeName);


        mStudentId = (sid == null ? -1 :
                (sid.trim().length() > 0 ? Integer.parseInt(sid) : -1));

        mCollegeId = (cid == null ? -1 :
                (cid.trim().length() > 0 ? Integer.parseInt(cid) : -1));

        Log.d("integer Student Id is ", "" + mStudentId);
        Log.d("integer College Id is ", "" + mCollegeId);


        if (mStudentId != -1 && mCollegeId != -1 && mStudentName != null && mCollegeName != null) {
            return true;
        }

        return false;
    }


    @OnClick(R.id.btn_add_record)
    public void insertRecord(View view) {

        if (isValidData()) {
            College college = new College(mCollegeId, mCollegeName);
            college.save();

            Student student = new Student();
            student.setStudentId(mStudentId);
            student.setStudentName(mStudentName);
            student.setCollege(college);
            student.save();

            student = new Student();
            student.setStudentId(mStudentId + 1);
            student.setStudentName(mStudentName + "_" + (mStudentId + 1));
            student.setCollege(college);
            student.save();

            student = new Student();
            student.setStudentId(mStudentId + 2);
            student.setStudentName(mStudentName + "_" + (mStudentId + 2));
            student.setCollege(college);
            student.save();

            textViewStatusMessage.setText("data saved successfully.");
            textViewStatusMessage.setTextColor(getResources().getColor(android.R.color
                    .holo_green_dark));
        } else {
            textViewStatusMessage.setText("InValid data.");
            textViewStatusMessage.setTextColor(getResources().getColor(android.R.color
                    .holo_red_dark));
        }

    }


    @OnClick(R.id.btn_edit_record)
    public void editRecord(View view) {

    }


    @OnClick(R.id.btn_select_student)
    public void selectStudentRecord(View view) {

        String sid = editTextStudentId.getText().toString();
        Log.d("in selection Student Id is ", "" + sid);

        mStudentId = (sid == null ? -1 :
                (sid.trim().length() > 0 ? Integer.parseInt(sid) : -1));

        Student student = Student.getStudent(mStudentId);

        if (student != null) {
            Log.d("id is ", "" + student.getId());
            Log.d("student id is ", "" + student.getStudentId());
            Log.d("student name is ", "" + student.getStudentName());
            Log.d("college id is ", "" + student.getCollege().getCollegeId());
            Log.d("college name is ", "" + student.getCollege().getCollegeName());

            validStatus("Select");
        } else {
            inValidStatus();
        }

    }


    @OnClick(R.id.btn_select_college)
    public void selectCollegeRecord(View view) {

        String cid = editTextCollegeId.getText().toString();
        Log.d("in selection College Id is ", "" + cid);

        mCollegeId = (cid == null ? -1 :
                (cid.trim().length() > 0 ? Integer.parseInt(cid) : -1));

        College college = College.getCollege(mCollegeId);

        if (college != null) {
            Log.d("id is ", "" + college.getId());
            Log.d("college id is ", "" + college.getCollegeId());
            Log.d("college name is ", "" + college.getCollegeName());
            Log.d("there are  ", "" + college.getAllStudent().size() + " students in this college");

            validStatus("Select");
        } else {
            inValidStatus();
        }

    }

    @OnClick(R.id.btn_delete_student)
    public void deleteStudentRecord(View view) {

        String sid = editTextStudentId.getText().toString();
        Log.d("Student Id is ", "" + sid);

        mStudentId = (sid == null ? -1 :
                (sid.trim().length() > 0 ? Integer.parseInt(sid) : -1));

        // Deleting student
        Student student = Student.getStudent(mStudentId);

        if (student != null) {
            student.delete();
            validStatus("Delete");

        } else {
            inValidStatus();
        }
    }


    @OnClick(R.id.btn_delete_college)
    public void deleteCollegeRecord(View view) {

        String cid = editTextCollegeId.getText().toString();
        Log.d("College Id is ", "" + cid);

        mCollegeId = (cid == null ? -1 :
                (cid.trim().length() > 0 ? Integer.parseInt(cid) : -1));

        // Deleting student
        College college = College.getCollege(mCollegeId);

        if (college != null) {
            college.delete();
            validStatus("Delete");
        } else {
            inValidStatus();
        }
    }

    private void inValidStatus() {
        textViewStatusMessage.setText("invalid data.");
        textViewStatusMessage.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
    }

    private void validStatus(String operationName) {
        textViewStatusMessage.setText(operationName + " operation done successfully.");
        textViewStatusMessage.setTextColor(getResources().getColor(android.R.color
                .holo_green_dark));
    }

}
