package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    Button button_carpay,button_home,button_position,btn_logout,btn_cardSetting,btn_Vsetting,btn_postionreset, button_more, btn_license;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        button_carpay = findViewById(R.id.button_carpay);
        button_home = findViewById(R.id.button_home);
        button_position = findViewById(R.id.button_position);


        btn_logout = findViewById(R.id.button_logout);
        btn_cardSetting = findViewById(R.id.button_carpay);
        btn_Vsetting = findViewById(R.id.button_carsetting);
        btn_license = findViewById(R.id.button_AddLicense);


        //uid값을 받아옴
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        //if intent has license, then show message with toast
        if(intent.hasExtra("license")){
            String license = intent.getStringExtra("license");
            Toast.makeText(getApplicationContext(),license, Toast.LENGTH_SHORT).show();
        }

        //btn_logout to go login page with "logout" message
        btn_logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),login.class);
                intent.putExtra("logout","로그아웃 되었습니다.");
                startActivity(intent);
            }
        });


        //btn_Vsetting to go vehicle setting page
        btn_Vsetting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),VehicleManager.class);
                //email값을 넘겨줌
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

        //btn_license to go license setting page
        btn_license.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AddLicense.class);
                //email값을 넘겨줌
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


    }
}