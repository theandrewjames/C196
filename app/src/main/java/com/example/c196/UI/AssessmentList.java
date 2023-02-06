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
import com.example.c196.R;

import java.util.List;

public class AssessmentList extends AppCompatActivity {
    List assessments;
    Repository repo;
    AssessmentAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerview3);
        repo=new Repository(getApplication());
        assessments = repo.getAllAssessments();
        adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentlist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshCourseList:
                assessments = repo.getAllAssessments();
                recyclerView.setAdapter(adapter);
                adapter.setAssessments(assessments);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void AddAssessment(View view) {
        Intent intent = new Intent(AssessmentList.this, AddAssessment.class);
        startActivity(intent);
    }
}