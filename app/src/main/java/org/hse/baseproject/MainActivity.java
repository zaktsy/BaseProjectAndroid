package org.hse.baseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forStudentsButton = findViewById(R.id.tmtblstudents);
        Button forTeachersButton = findViewById(R.id.tmtblTeachers);
        Button settingsButton = findViewById(R.id.settings);

        forStudentsButton.setOnClickListener(this::showStudentActivity);

        forTeachersButton.setOnClickListener(this::showTeacherActivity);

        settingsButton.setOnClickListener(this::showSettingsActivity);
    }

    private void showTeacherActivity(View view) {
        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }

    private void showStudentActivity(View view) {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    private void showSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}