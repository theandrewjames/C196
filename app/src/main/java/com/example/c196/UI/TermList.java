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
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class TermList extends AppCompatActivity {
    List terms;
    Repository repo;
     TermAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerview);
        repo=new Repository(getApplication());
        terms=repo.getAllTerms();
        adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

    }
    public void AddTerm(View view) {
        Intent intent = new Intent(TermList.this, AddTerm.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshTerm:
                terms = repo.getAllTerms();
                recyclerView.setAdapter(adapter);
                adapter.setTerms(terms);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}