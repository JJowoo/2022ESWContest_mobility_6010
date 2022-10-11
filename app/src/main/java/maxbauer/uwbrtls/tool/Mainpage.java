package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Mainpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        //intent안의 uid값을 받아옴
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");



        Button button_home = findViewById(R.id.button_home);
        Button button_position = findViewById(R.id.button_position);
        Button button_more = findViewById(R.id.button_more);
        Button button_addcard = findViewById(R.id.button_addcard);
        Button button_addcar = findViewById(R.id.button_addcar);
        TextView uidresult = findViewById(R.id.login_result);

        //uid값을 텍스트뷰에 출력
        uidresult.setText(uid);

        button_home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),Home.class);
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
                //email값을 넘겨줌
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_addcard.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AddCard.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        button_addcar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AddVehicle.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
    }
}