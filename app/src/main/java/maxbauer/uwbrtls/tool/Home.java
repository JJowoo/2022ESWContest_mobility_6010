package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import maxbauer.uwbrtls.tool.view.ViewImpl;


public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    float[] xpos = {0};
    float[] ypos = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button_carpay = findViewById(R.id.button_carpay);
        Button button_position = findViewById(R.id.button_position);
        Button button_more = findViewById(R.id.button_more);
        Button button_createkey = findViewById(R.id.create_key);
        Button button_connect = findViewById(R.id.connect);
        Button unlock = findViewById(R.id.unlock);
        TextView distanceText = findViewById(R.id.distance);

        //intent안의 uid값을 받아옴
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        final String[] unlock_status = {""};


        String[] xst = new String[1];
        String[] yst = new String[1];

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xst[0] = snapshot.child("position_x").getValue(String.class);
                yst[0] = snapshot.child("position_y").getValue(String.class);
                //Log.v("xst", xst[0]);
                Log.v("xst", xst[0]);
                xpos[0] = Float.parseFloat(xst[0]);
                ypos[0] = Float.parseFloat(yst[0]);
                //Log.v("xpos", String.valueOf(xpos[0]));
                float distance;
                distance =xpos[0] + ypos[0];
                distanceText.setText(distance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //distance = (float) Math.sqrt((float) Math.pow(xpos[0],2)+ (float) Math.pow(ypos[0],2));
        Log.v("xpos", String.valueOf(xpos[0]));
        Log.v("distance", distanceText.getText().toString());

        mDatabase.child("unlock").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unlock_status[0] = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        unlock.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(unlock_status[0].equals("0")&& Float.parseFloat(distanceText.getText().toString()) <5){
                    mDatabase.child("unlock").setValue("1");
                }
                else if(unlock_status[0].equals("1")&& Float.parseFloat(distanceText.getText().toString()) <5){
                    mDatabase.child("unlock").setValue("0");
                }
                else{
                    //send toast message "unlock failed"
                    Toast.makeText(Home.this, "거리가 너무 멉니다", Toast.LENGTH_SHORT).show();
                }

            }
        });






        button_connect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
//                Log.d("넘어감", uid);
                Intent intent = new Intent(getApplicationContext(), ViewImpl.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });


        button_carpay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_position.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SeatAdjust.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_more.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_createkey.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),CreateKey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

    }
}