package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssessment extends AppCompatActivity {
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Repository repository;
    int assessmentId;
    TextView editNAme;
    TextView editStart;
    TextView editEnd;
    SimpleDateFormat sdf;
    Spinner typeSpinner;
    Spinner nameSpinner;
    String status;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        assessmentId = getIntent().getIntExtra("assessmentID", -1);
        editNAme= findViewById(R.id.assessmentTitleEditText);
        editNAme.setText(getIntent().getStringExtra("name"));
        typeSpinner=(Spinner) findViewById(R.id.assessmentTypeSpinner);
        List<String> types = new ArrayList<>();
        types.add("Performance");
        types.add("Objective");
        ArrayAdapter<String> types1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        types1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(types1);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = typeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        List<Courses> allCourses = repository.getAllCourses();
        List<String> names = new ArrayList<>();
        for(Courses course : allCourses) {
           names.add(course.getCourseName());
    }
    ArrayAdapter<String> namesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
    namesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    nameSpinner=(Spinner) findViewById(R.id.CourseSpinner);
    nameSpinner.setAdapter(namesArrayAdapter);
    nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            name = nameSpinner.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
    editStart = findViewById(R.id.editAssessmentStart);
    editEnd = findViewById(R.id.editAssessmentEnd);
    String myFormat = "MM/dd/yy";
    sdf = new SimpleDateFormat(myFormat, Locale.US);
    editStart.setText(getIntent().getStringExtra("start"));
    editEnd.setText(getIntent().getStringExtra("end"));
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
            new DatePickerDialog(AddAssessment.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
            new DatePickerDialog(AddAssessment.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
        getMenuInflater().inflate(R.menu.menu_assessmentlist, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            /*case R.id.refreshCourseList:
                courses=repo.getAllCourses();
                recyclerView.setAdapter(adapter);
                adapter.setCourses(courses);
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }
    public void saveButton(View view) {
    }
}