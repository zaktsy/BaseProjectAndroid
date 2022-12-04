package org.hse.baseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    private static final String EMPTY_STRING = "";
    private final static String USER_NAME_KEY = "userName";

    private TextView studentName;
    private TextView haveClass;
    private TextView subject;
    private TextView classroom;
    private TextView corp;
    private TextView teacher;
    private TextView current_time;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button timetableForDay = findViewById(R.id.timetable_for_day_button);
        Button timetableForWeek = findViewById(R.id.timetable_for_week_button);
        timetableForDay.setOnClickListener(v -> onTimetableButtonClicked(ScheduleType.DAY));
        timetableForWeek.setOnClickListener(v -> onTimetableButtonClicked(ScheduleType.WEEK));

        updateClassInfo(InitUtils.initData(this));
        current_time = findViewById(R.id.current_time);
        studentName = findViewById(R.id.student_textview);
        preferenceManager = new PreferenceManager(this);
        
        current_time.setText(InitUtils.iniTime());
        studentName.setText(getNameOrDefault());
        
        updateTimetableForNow();
    }

    private void updateTimetableForNow() {
    }

    private String getNameOrDefault() {
        return preferenceManager.getValue(USER_NAME_KEY, EMPTY_STRING);
    }

    private void updateClassInfo(@NonNull Map<String, String> classInfo) {
        haveClass = findViewById(R.id.have_class);
        haveClass.setText(classInfo.get("have_class"));

        subject = findViewById(R.id.subject);
        subject.setText(classInfo.get("subject"));

        classroom = findViewById(R.id.classroom);
        classroom.setText(classInfo.get("classroom"));

        corp = findViewById(R.id.corp);
        corp.setText(classInfo.get("corp"));

        teacher = findViewById(R.id.teacher);
        teacher.setText(classInfo.get("teacher"));
    }

    private void onTimetableButtonClicked(ScheduleType scheduleType){

        String name = studentName.getText().toString();
        if (name.equals(EMPTY_STRING)){
            Toast toast = new Toast(this);
            toast.setText(R.string.warning_null_name);
            toast.show();

            return;
        }

        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.NAME, name);
        intent.putExtra(ScheduleActivity.MODE, ScheduleMode.STUDENT);
        intent.putExtra(ScheduleActivity.TYPE, scheduleType);
        startActivity(intent);
    }
}