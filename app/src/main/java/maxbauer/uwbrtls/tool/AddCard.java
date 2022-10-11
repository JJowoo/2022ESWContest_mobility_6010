package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCard extends AppCompatActivity  {
    EditText owner_name,card_num, exp_date, cvd_num;
    TextView card_num_view,card_exp_date_view, card_name_view,card_cvd_view;
    EditText owner_name_out,card_num_out, exp_date_out, cvd_num_out;
    Button register_card;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        //mdatabase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");


        card_num_view =findViewById(R.id.card_num_view);
        card_exp_date_view =findViewById(R.id.card_exp_date_view);
        card_name_view =findViewById(R.id.card_name_view);
        card_cvd_view =findViewById(R.id.card_cvd_view);
        register_card = findViewById(R.id.register);

        owner_name=findViewById(R.id.owner_name);
        card_num=findViewById(R.id.card_num);
        exp_date=findViewById(R.id.exp_date);
        cvd_num=findViewById(R.id.cvd_num);

        owner_name_out=(EditText) findViewById(R.id.owner_name);
        card_num_out=(EditText) findViewById(R.id.card_num);
        exp_date_out=(EditText) findViewById(R.id.exp_date);
        cvd_num_out=(EditText) findViewById(R.id.cvd_num);

        owner_name_out.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_name_view.setText(s.toString());

            }
        });
        card_num_out.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_num_view.setText(s.toString());

            }
        });
        exp_date_out.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_exp_date_view.setText(s.toString());

            }
        });
        cvd_num_out.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                card_cvd_view.setText(s.toString());

            }
        });

        register_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCard.this, Mainpage.class);
                intent.putExtra("uid",uid);

                //데이터베이스에 업로드
                String owner_name = owner_name_out.getText().toString();
                String card_num = card_num_out.getText().toString();
                String exp_date = exp_date_out.getText().toString();
                String cvd_num = cvd_num_out.getText().toString();

                mDatabase.child(uid).child("결제카드").child(card_num).child("owner_name").setValue(owner_name);
                mDatabase.child(uid).child("결제카드").child(card_num).child("exp_date").setValue(exp_date);
                mDatabase.child(uid).child("결제카드").child(card_num).child("cvd_num").setValue(cvd_num);

                startActivity(intent);
            }
        });

    }





}