package maxbauer.uwbrtls.tool;

import android.content.Intent;
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

public class login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    EditText editText_email,editText_Password;
    Button button_login;
    Button sign_up_button;
    Button button3;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = findViewById(R.id.editText_email);
        editText_Password = findViewById(R.id.editText_Password);
        button_login = findViewById(R.id.button_login);
        sign_up_button = findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();


        //if intent is not null, then get the string from the intent and show it in the toast
        if(getIntent().getStringExtra("logout") != null){
            Toast.makeText(this, getIntent().getStringExtra("logout"), Toast.LENGTH_SHORT).show();
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_email.getText().toString().equals("") && !editText_Password.getText().toString().equals("")) {
                    loginUser(editText_email.getText().toString(), editText_Password.getText().toString());
                } else {
                    Toast.makeText(login.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String uid=user.getUid();
                    Intent intent = new Intent(login.this, Mainpage.class);
                    intent.putExtra("uid", uid);

                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };
        //button3누르면 home으로 이동 (임시)
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mainpage.class);
                intent.putExtra("uid", "d4qPsAkTnHPK7Ze8sEVxQPBhGxF2");
                startActivity(intent);
            }
        });

    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        } else {
                            // 로그인 실패
                            Toast.makeText(login.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}