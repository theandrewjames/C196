package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

public class AddTerm extends AppCompatActivity {
    EditText editName;
    EditText editStart;
    EditText editEnd;
    String name;
    int termId;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editName=findViewById(R.id.editTermName);
        editStart=findViewById(R.id.editStartDate);
        editEnd=findViewById(R.id.editEndDate);
        editEnd=findViewById(R.id.editEndDate);
        termId=getIntent().getIntExtra("id", -1);
        editName.setText(getIntent().getStringExtra("title"));
        editStart.setText(getIntent().getStringExtra("start"));
        editEnd.setText(getIntent().getStringExtra("end"));
        repository=new Repository(getApplication());
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_detail, menu);
        return true;
    }
    public void saveButton(View view) {
        Terms terms;
        if(termId == -1) {
            int newId = repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermId() + 1;
            terms = new Terms(newId, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.insert(terms);
        }
        else {
            terms = new Terms(termId, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.update(terms);
        }
    }
}