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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class AddCourse extends AppCompatActivity {
    EditText editName;
    EditText editStart;
    EditText editEnd;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText instructorName;
    EditText phone;
    EditText email;
    RadioGroup statusRadio;
    String status;
    EditText note;
    Repository repository;
    String myFormat;
    String selectedTerm;
    SimpleDateFormat sdf;
    Spinner termSpinner;
    int courseID;
    int termId;
    String termName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        courseID=getIntent().getIntExtra("id", -1);
        termName = getIntent().getStringExtra("name");
        termSpinner = (Spinner) findViewById(R.id.termSpinner);
        List<Terms> terms = repository.getAllTerms();
        List<String> termNames = new ArrayList<>();
        for(Terms term : terms) {
            termNames.add(term.getTermName());
        }
        termId = getIntent().getIntExtra("termID", -1);
        ArrayAdapter<String> termArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termNames);
        termArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(termArrayAdapter);
        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTerm = termSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        termSpinner.setSelection(getIndex(termSpinner, termName));
        editName=findViewById(R.id.courseNameEditText);
        editStart=findViewById(R.id.editCourseStart);
        editEnd=findViewById(R.id.editCourseEnd);
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
                new DatePickerDialog(AddCourse.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AddCourse.this, endDate, myCalendarStart.get(Calendar.YEAR),
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
        instructorName=findViewById(R.id.instructorEditText);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        statusRadio=findViewById(R.id.statusRadioGroup);
        note=findViewById(R.id.noteEdit);
        editName.setText(getIntent().getStringExtra("name"));
        status=getIntent().getStringExtra("status");
        if(status == null) statusRadio.check(R.id.statusPlan);
        else if(status.equals("In Progress")) statusRadio.check(R.id.statusProgress);
        else if(status.equals("Completed")) statusRadio.check(R.id.statusComplete);
        else if(status.equals("Dropped")) statusRadio.check(R.id.statusDropped);
        else statusRadio.check(R.id.statusPlan);
        instructorName.setText(getIntent().getStringExtra("instructor"));
        phone.setText(getIntent().getStringExtra("phone"));
        email.setText(getIntent().getStringExtra("email"));
        note.setText(getIntent().getStringExtra("note"));
    }
    public void updateLabelStart() { editStart.setText(sdf.format(myCalendarStart.getTime()));}
    public void updateLabelEnd() { editEnd.setText(sdf.format(myCalendarStart.getTime()));}
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_detail, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,  getIntent().getStringExtra("note"));
                sendIntent.putExtra(Intent.EXTRA_TITLE, "message title");
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notify:
                String dateFromScreen = editStart.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger=myDate.getTime();
                Intent intent = new Intent(AddCourse.this, MyReceiver.class);
                intent.putExtra("key", "Start of course: " + getIntent().getStringExtra("name"));
                PendingIntent sender = PendingIntent.getBroadcast(AddCourse.this, MainActivity.numAlert++,  intent, 0);
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
                intent = new Intent(AddCourse.this, MyReceiver.class);
                intent.putExtra("key", "End of course: " + getIntent().getStringExtra("name"));
                sender = PendingIntent.getBroadcast(AddCourse.this, MainActivity.numAlert++,  intent, 0);
                alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(AddCourse.this, "Start & end date notifications set", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deleteCourse:
                if(courseID == -1) {
                    Toast.makeText(AddCourse.this, "Cannot delete a non-saved course", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<Courses> courses = repository.getAllCourses();
                    List<Courses> courseToDelete = new ArrayList<>();
                    for (Courses courses1 : courses) {
                        if (courses1.getCourseId() == courseID)
                            courseToDelete.add(courses1);
                        repository.delete(courseToDelete.get(0));
                        Toast.makeText(AddCourse.this, "Course deleted", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public int getIndex(Spinner spinner, String termName) {
        for(int i = 0;i < spinner.getCount();i++) {
            if(spinner.getItemAtPosition(i).toString().equals(termName))
                return i;
        }
        return 0;
    }
    public int getTermId(String name) {
        List<Terms> terms = repository.getAllTerms();
        for(Terms term : terms) {
            if(term.getTermName().equals(name)) {
                return term.getTermId();
            }
        }
         return -1;
    }
    public void saveButton(View view) {
        Courses courses;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.statusRadioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton button = (RadioButton) findViewById(selectedId);
        if(courseID == -1) {
            int newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
            courses = new Courses(newId, editName.getText().toString(), button.getText().toString(),
                    editStart.getText().toString(), editEnd.getText().toString(), getTermId(selectedTerm), instructorName.getText().toString(),
                    email.getText().toString(), phone.getText().toString(),note.getText().toString());
            repository.insert(courses);
            Toast.makeText(AddCourse.this, "Course saved", Toast.LENGTH_SHORT).show();

        }
        else {
            courses = new Courses(courseID, editName.getText().toString(), button.getText().toString(),
                    editStart.getText().toString(), editEnd.getText().toString(), getTermId(selectedTerm), instructorName.getText().toString(),
                    email.getText().toString(), phone.getText().toString(),note.getText().toString());
            repository.update(courses);
            Toast.makeText(AddCourse.this, "Course updated", Toast.LENGTH_SHORT).show();

        }
    }
}