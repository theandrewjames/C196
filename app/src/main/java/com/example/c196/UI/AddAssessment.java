package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
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
        String type = getIntent().getStringExtra("type");
        if(type == null) typeSpinner.setSelection(0);
        else if(type.equals("Performance")) typeSpinner.setSelection(0);
        else typeSpinner.setSelection(1);

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
    nameSpinner.setSelection(getIndex(nameSpinner, getIntent().getStringExtra("name")));
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
            new DatePickerDialog(AddAssessment.this, endDate, myCalendarStart.get(Calendar.YEAR),
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
        getMenuInflater().inflate(R.menu.assessment_detail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyAssessment:
                String dateFromScreen = editStart.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent = new Intent(AddAssessment.this, MyReceiver.class);
                intent.putExtra("key", "Start of Assessment: " + getIntent().getStringExtra("name"));
                PendingIntent sender = PendingIntent.getBroadcast(AddAssessment.this, MainActivity.numAlert++,  intent, 0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                dateFromScreen = editEnd.getText().toString();
                myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger=myDate.getTime();
                intent = new Intent(AddAssessment.this, MyReceiver.class);
                intent.putExtra("key", "End of Assessment: " + getIntent().getStringExtra("name"));
                sender = PendingIntent.getBroadcast(AddAssessment.this, MainActivity.numAlert++,  intent, 0);
                alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(AddAssessment.this, "Start & end date notifications set", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deleteAssessment:
                if(assessmentId == -1) {
                    Toast.makeText(AddAssessment.this, "Cannot delete a non-saved assessment", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<Assessments> allAssessments = repository.getAllAssessments();
                    List<Assessments> toDelete = new ArrayList<>();
                    for(Assessments assessment : allAssessments) {
                        if(assessment.getAssessmentId() == assessmentId) {
                            toDelete.add(assessment);
                            repository.delete(toDelete.get(0));
                            Toast.makeText(AddAssessment.this, "Course deleted", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
    public int getIndex(Spinner spinner, String name) {
        for(int i = 0;i < spinner.getCount();i++) {
            if(spinner.getItemAtPosition(i).toString().equals(name))
                return i;
        }
        return 0;
    }
    public int getCourseId(String name) {
        List<Courses> courses = repository.getAllCourses();
        for(Courses course : courses) {
            if(course.getCourseName().equals(name)) {
                return course.getCourseId();
            }
        }
        return -1;
    }
    public void saveButton(View view) {
        Assessments assessments;
        if(assessmentId == -1) {
          int newId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;
          assessments = new Assessments(newId, editNAme.getText().toString(), editStart.getText().toString(),
                  editEnd.getText().toString(), status, getCourseId(name));
          repository.insert(assessments);
          Toast.makeText(AddAssessment.this, "Assessment saved", Toast.LENGTH_SHORT).show();
        }
        else {
            assessments = new Assessments(assessmentId, editNAme.getText().toString(), editStart.getText().toString(),
                    editEnd.getText().toString(), status, getCourseId(name));
            repository.update(assessments);
            Toast.makeText(AddAssessment.this, "Assessment updated", Toast.LENGTH_SHORT).show();
        }
    }
}