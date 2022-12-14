package org.hse.baseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class TeacherActivity extends AppCompatActivity {

    private Spinner groupsSpinner;
    private List<Group> groups;
    private TextView haveClass;
    private TextView subject;
    private TextView classroom;
    private TextView corp;
    private TextView teacher;
    private TextView current_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        updateClassInfo(InitUtils.initData(this));
        current_time = findViewById(R.id.current_time);

        current_time.setText(InitUtils.iniTime());
        groups = InitUtils.initGroups();

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupsSpinner.setAdapter(adapter);
        groupsSpinner.setOnItemSelectedListener(new GroupsSpinnerListener(this));
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
}