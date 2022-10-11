package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SeatAdjust extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_adjust);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        Button button_carpay = findViewById(R.id.button_carpay);
        Button button_home = findViewById(R.id.button_home);
        Button button_more = findViewById(R.id.button_more);
        Button seat1 = findViewById(R.id.seat1);
        Button seat2 = findViewById(R.id.seat2);
        Button seat3 = findViewById(R.id.seat3);
        Button seat4 = findViewById(R.id.seat4);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String x = snapshot.child("position_x").getValue(String.class);
                String y = snapshot.child("position_y").getValue(String.class);
                float xpos = Float.parseFloat(x);
                float ypos = Float.parseFloat(y);
                seat1.setEnabled(false);
                seat2.setEnabled(false);
                if(xpos > -1 && xpos < 1 && ypos > -0.3 && ypos < 0.2){
                    seat1.setEnabled(true);
                }
                else if(xpos > -1 && xpos < 1 && ypos >= 0.2 && ypos < 0.6){
                    seat2.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        button_carpay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Home.class);
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
    }
}