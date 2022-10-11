package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddVehicle extends AppCompatActivity {
    private FirebaseAuth mAuth;     //
    private DatabaseReference mDatabase;

    EditText carNum,vehicleType,maxNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        mAuth = FirebaseAuth.getInstance(); //
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button register =(Button) findViewById(R.id.register);

        //intent안의 email값을 받아옴
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carNum = (EditText) findViewById(R.id.vehicle_num);
                vehicleType = (EditText) findViewById(R.id.vehicleType);
                maxNum = (EditText) findViewById(R.id.max_num);
                String maxNumStr = maxNum.getText().toString();
                String carNumStr = carNum.getText().toString();
                String vehicleTypeStr = vehicleType.getText().toString();


                Log.d("carNumStr",carNumStr);
                Log.d("carNum", carNum.toString());
                Log.d("vehicleType", vehicleType.toString());
                Log.d("maxNum", maxNum.toString());

                //데이터베이스에 저장
//                Log.d("차종", mDatabase.child(uid).child("등록차량").child(String.valueOf(carNum)).child("차종").setValue(vehicleType));
                mDatabase.child(uid).child("등록차량").child(carNumStr).child("차종").setValue(vehicleTypeStr);
                mDatabase.child(uid).child("등록차량").child(carNumStr).child("최대승차인원").setValue(maxNumStr);

                //mainpage로 이동
                Intent intent = new Intent(getApplicationContext(),Mainpage.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }


        });

    }
}