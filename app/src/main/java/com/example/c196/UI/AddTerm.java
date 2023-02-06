package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTerm extends AppCompatActivity {
    EditText editName;
    EditText editStart;
    EditText editEnd;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;
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
        String myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStart.setText(getIntent().getStringExtra("start"));
        editEnd.setText(getIntent().getStringExtra("end"));
        repository=new Repository(getApplication());
        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStart.getText().toString();
                if(info.equals(""))info= "02/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerm.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEnd.getText().toString();
                if(info.equals(""))info= "02/10/23";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerm.this, endDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        endDate=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }
    public void updateLabelStart() { editStart.setText(sdf.format(myCalendarStart.getTime()));}
    public void updateLabelEnd() { editEnd.setText(sdf.format(myCalendarStart.getTime()));}
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_detail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTerm:
                if(termId == -1) {
                    Toast.makeText(AddTerm.this, "Cannot delete a non-saved term", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<Courses> courses = repository.getAllCourses();
                    int count = 0;
                    for(Courses course: courses) {
                        if(course.getTermId() == termId) count++;
                    }
                    if(count > 0) Toast.makeText(AddTerm.this, "This term has " + count + " saved courses.Please delete courses first", Toast.LENGTH_SHORT).show();
                    else {
                        List<Terms> terms = repository.getAllTerms();
                        List<Terms> termToDelete = new ArrayList<>();
                        for(Terms term : terms) {
                            if(term.getTermId() == termId){
                                termToDelete.add(term);
                                repository.delete(termToDelete.get(0));
                                Toast.makeText(AddTerm.this, "Term deleted", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveButton(View view) {
        Terms terms;
        if(termId == -1) {
            int newId = repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermId() + 1;
            terms = new Terms(newId, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            Toast.makeText(AddTerm.this, "Term saved", Toast.LENGTH_SHORT).show();
            repository.insert(terms);
        }
        else {
            terms = new Terms(termId, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            Toast.makeText(AddTerm.this, "Term updated", Toast.LENGTH_SHORT).show();
            repository.update(terms);
        }
    }
}