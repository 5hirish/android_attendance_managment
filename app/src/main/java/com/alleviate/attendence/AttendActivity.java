package com.alleviate.attendence;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendActivity extends AppCompatActivity {

    String sem,subj,dat,teach;
    int sem_position,subject_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);
        setTitle("Attendance");

        final SharedPreferences sf = PreferenceManager.getDefaultSharedPreferences(this);
        final int total = sf.getInt("Total",0);

        Calendar c = Calendar.getInstance();

        DateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
        dat = f1.format(c.getTime());
        //Toast.makeText(getApplicationContext(),dat,Toast.LENGTH_LONG).show();



        Spinner semester = (Spinner)findViewById(R.id.semester);
        Spinner teacher = (Spinner)findViewById(R.id.teacher);
        final Spinner subject = (Spinner)findViewById(R.id.subject);
        final Spinner date = (Spinner)findViewById(R.id.attend_date);

        Button enter = (Button)findViewById(R.id.enter);

        ArrayAdapter<CharSequence> adapter_teh = ArrayAdapter.createFromResource(AttendActivity.this, R.array.teacher, android.R.layout.simple_spinner_item);
        adapter_teh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacher.setAdapter(adapter_teh);

        teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                teach = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                teach = adapterView.getItemAtPosition(0).toString();
            }
        });


        ArrayAdapter<CharSequence> adapter_sem = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapter_sem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(adapter_sem);

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                sem = adapterView.getItemAtPosition(position).toString();
                sem_position = position;

                switch (sem_position){
                    case 0:
                        subject_array = R.array.sem1_subject;
                        break;
                    case 1:
                        subject_array = R.array.sem2_subject;
                        break;
                    case 2:
                        subject_array = R.array.sem3_subject;
                        break;
                    case 3:
                        subject_array = R.array.sem4_subject;
                        break;
                    default:
                        subject_array = R.array.sem1_subject;
                        break;
                }

                ArrayAdapter<CharSequence> adapter_sub = ArrayAdapter.createFromResource(AttendActivity.this, subject_array, android.R.layout.simple_spinner_item);
                adapter_sub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subject.setAdapter(adapter_sub);

                subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        subj = adapterView.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        subj = adapterView.getItemAtPosition(0).toString();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sem = adapterView.getItemAtPosition(0).toString();
                sem_position = 0;
            }
        });

        ArrayAdapter<CharSequence> adapter_date = ArrayAdapter.createFromResource(this, R.array.date, android.R.layout.simple_spinner_item);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(adapter_date);

        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Calendar cal = Calendar.getInstance();

                String attend_date = adapterView.getItemAtPosition(position).toString();

                if(attend_date.equals("Select date:")){
                    DatePickerDialog mdate = new DatePickerDialog(AttendActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedy, int selectedm, int selectedd) {
                            selectedm = selectedm + 1;
                            DateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");

                            dat = selectedd+"/"+selectedm+"/"+selectedy;
                            Date d = null;
                            try {
                                d = f1.parse(dat);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dat = f1.format(d);

                            //Toast.makeText(getApplicationContext(),dat,Toast.LENGTH_LONG).show();
                        }
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                    mdate.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);            //Minimum date is today
                    mdate.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                sf.edit().putInt("Total",total+1).commit();

                Intent in = new Intent(AttendActivity.this,CheckActivity.class);
                in.putExtra("Semester",sem);
                in.putExtra("Subject",subj);
                in.putExtra("Date",dat);
                startActivity(in);
            }
        });

    }
}
