package com.example.love_dogs.posts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.love_dogs.R;
import com.example.love_dogs.login.User;
import com.example.love_dogs.volunteers.VolunteerPost;

import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity {

    EditText date_time_in;
    EditText location;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);
        setTitle("Create Event");

        user = User.getCurrentUser(this);
        if(user == null){
            return;
        }

        date_time_in = findViewById(R.id.pdate);
        date_time_in.setInputType(InputType.TYPE_NULL);
        date_time_in.setFocusable(false);

        date_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_in);
            }
        });

//        location = findViewById(R.id.paddress);
//        location.setInputType(InputType.TYPE_NULL);
//        location.setFocusable(false);
//
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateTimeDialog(location);
//            }
//        });
    }
    private void showSetLocationDialog(EditText location){
    }
    private void showDateTimeDialog(EditText date_time_in){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        date_time_in.setText(VolunteerPost.simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(CreatePostActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(CreatePostActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
        Log.d("mylog", "done");
    }
    public void onPostButton(View button){
        Intent intent = new Intent(this, ViewPostActivity.class);
        //Bundle bundle = new Bundle();

        TextView title = findViewById(R.id.ptitle);
        TextView date = findViewById(R.id.pdate);
        TextView location = findViewById(R.id.plocation);
        TextView body = findViewById(R.id.dp_body);
    
        VolunteerPost post = new VolunteerPost();
        post.UpdatePost(title.getText().toString(), user.user_name, user.uid, date.getText().toString(),
                location.getText().toString(), body.getText().toString());

//        bundle.putString("title", title.getText().toString());
//        bundle.putString("date", date.getText().toString());
//        bundle.putString("location", location.getText().toString());
//        bundle.putString("context", body.getText().toString());

        //intent.putExtra("name", name.getText().toString());
//        intent.putExtras(bundle);
        post.push();

        VolunteerPost.current = post;
        startActivity(intent);
    }
}