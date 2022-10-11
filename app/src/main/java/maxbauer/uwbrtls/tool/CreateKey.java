package maxbauer.uwbrtls.tool;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateKey extends AppCompatActivity {

    TextView dateRentalText, timeRentalText, dateReturnText, timeReturnText,selectVehicle;
    Calendar calendar;
    String result = "";
    Button Vselect;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_key);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dateRentalText = findViewById(R.id.date_rental_text);
        timeRentalText = findViewById(R.id.time_rental_text);

        dateReturnText = findViewById(R.id.date_return_text);
        timeReturnText = findViewById(R.id.time_return_text);
        selectVehicle = findViewById(R.id.select_vehicle_text);

        Vselect = findViewById(R.id.button_select_vehicle);



        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        final Long today = MaterialDatePicker.todayInUtcMilliseconds();

        //uid
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        final ArrayAdapter<String> carnum= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        //대여 날짜 버튼
        Button button_rental_date = findViewById(R.id.button_rental_date);
        button_rental_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Date Picker")
                        .setSelection(today).build();   //오늘 날짜 셋팅
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Date date = new Date();
                        date.setTime(selection);
                        String dateString = simpleDateFormat.format(date);
                        result=result+dateString;
                        dateRentalText.setText(dateString);

                    }
                });
            }
        });

        //대여 시각 버튼
        Button button_rental_time = findViewById(R.id.button_rental_time);
        button_rental_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateKey.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar ,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "오전";
                        if(selectedHour >12){
                            selectedHour -= 12;
                            state = "오후";
                        }
                        result=result+state+" "+selectedHour+"시 "+selectedMinute+"분";
                        timeRentalText.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle(("Select Time"));
                mTimePicker.show();
            }
        });

        //반납 날짜 버튼
        Button button_return_date = findViewById(R.id.button_return_date);
        button_return_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Date Picker")
                        .setSelection(today).build();   //오늘 날짜 셋팅
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                //확인버튼
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Date date = new Date();
                        date.setTime(selection);

                        String dateString = simpleDateFormat.format(date);
                        result=result+dateString;
                        dateReturnText.setText(dateString);
                    }
                });
            }
        });

        //반납 시각 버튼
        Button button_return_time = findViewById(R.id.button_return_time);
        button_return_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateKey.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "오전";
                        if(selectedHour >12){
                            selectedHour -= 12;
                            state = "오후";
                        }
                        result= result+state + "/" + selectedHour + "/" + selectedMinute + "/";
                        timeReturnText.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false);
                mTimePicker.setTitle(("Select Time"));
                mTimePicker.show();
            }
        });

        mDatabase.child(uid).child("등록차량").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    carnum.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final String[] vehicle = new String[1];

        Vselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert= new AlertDialog.Builder(CreateKey.this);
                alert.setTitle("차량 선택");
                alert.setAdapter(carnum,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        vehicle[0] = carnum.getItem(which);
                        Log.v("vehicle",vehicle[0]);
                        selectVehicle.setText(vehicle[0]);
                    }
                });
                alert.show();
            }
        });



        Button button_create_key = findViewById(R.id.button_create_key);
        button_create_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("key").child(vehicle[0]).setValue(result);
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("uid",uid);
                //toast로 생성완료 메세지
                Toast.makeText(CreateKey.this, "키 생성 완료", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}