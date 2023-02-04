package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TermList(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());

    }

    public void CourseList(View view) {
        Intent intent = new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
    }

    public void AssessmentList(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentList.class);
        startActivity(intent);
        Repository repo = new Repository(getApplication());
        Assessments assessment = new Assessments(1, "Sample", "01/01/23", "01/01/23", "Objective", 13);
        repo.insert(assessment);
    }
}