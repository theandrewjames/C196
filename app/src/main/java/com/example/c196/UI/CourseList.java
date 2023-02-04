package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class CourseList extends AppCompatActivity {
    List courses;
    Repository repo;
    CourseAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerview2);
        repo=new Repository(getApplication());
        courses=repo.getAllCourses();
        adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public void AddCourse(View view) {
        Intent intent = new Intent(CourseList.this, AddCourse.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshCourseList:
                courses=repo.getAllCourses();
                recyclerView.setAdapter(adapter);
                adapter.setCourses(courses);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}