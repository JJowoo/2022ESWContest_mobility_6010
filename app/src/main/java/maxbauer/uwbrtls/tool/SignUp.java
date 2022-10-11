package maxbauer.uwbrtls.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;     //
    private DatabaseReference mDatabase;

    /*




    mDatabase = FirebaseDatabase.getInstance().getReference();

        test.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name=((EditText)findViewById(R.id.editText_name)).getText().toString();
            mDatabase.child(name).child("아이디").setValue("ㅂㅈㄷ");
            mDatabase.child("재현").child("이이디").setValue("ㅁㄴㅇ");
        }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.activity_sign_up);  //
        findViewById(R.id.btn_signup).setOnClickListener(onClickListener);
        mAuth = FirebaseAuth.getInstance(); //
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }




    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_signup:
                    signUp();
                    break;
            }
        }
    };


    private void signUp(){
        String name=((EditText)findViewById(R.id.editText_name)).getText().toString();
        final String email=((EditText)findViewById(R.id.editText_email)).getText().toString();
        String password=((EditText)findViewById(R.id.editText_password)).getText().toString();
        String password_confirm=((EditText)findViewById(R.id.editText_password_confirm)).getText().toString();

        if(email.length()>0 && password.length()>0 && password_confirm.length()>0){
            if(password.equals(password_confirm)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid=null;
                                    if(user!=null)  {
                                        uid = user.getUid();
                                    }
                                    mDatabase.child(uid).child("email").setValue(email);
                                    Toast.makeText(SignUp.this, "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                                } else {
                                    if(task.getException().toString() !=null){
                                        Toast.makeText(SignUp.this, "회원가입에 실패했습니다." ,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(SignUp.this, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(SignUp.this, "아아디와 비밀번호를 확인해주세요." ,Toast.LENGTH_SHORT).show();
        }
    }
}