package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent); //인트로 실행 후 바로 MainActivity로 넘어감.
                finish();
            }
        },1000); //1초 후 인트로 실행
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}