package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLicense extends AppCompatActivity {
    private DatabaseReference mDatabase;

    EditText license_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_license);
        //mdatabase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");

        Button register = findViewById(R.id.register);
        license_num = findViewById(R.id.licence_number);
        final String license_num_out = license_num.getText().toString();

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                intent.putExtra("uid",uid);
                if(license_num_out!=null){
                    mDatabase.child(uid).child("license").setValue("true");
                    intent.putExtra("license","등록되었습니다");
                }
                startActivity(intent);
            }
        });
    }
}