package maxbauer.uwbrtls.tool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import maxbauer.uwbrtls.tool.R;


public class VehicleManager extends AppCompatActivity {
    private FirebaseAuth mAuth;     //
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_manager);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance(); //

        TextView max_num = (TextView) findViewById(R.id.max_num);
        ListView listview = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> vehicle = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //email값을 받아옴
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");

        /*
        mDatabase.child(uid).child("등록차량").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String carNum = dataSnapshot.getKey();
                    String vehicleType = dataSnapshot.child("차종").getValue(String.class);
                    String maxNum = dataSnapshot.child("최대승차인원").getValue(String.class);
                    Log.d("carNum", carNum);
                    Log.d("vehicleType", vehicleType);
                    Log.d("maxNum", maxNum);
                    car_num.setText(carNum);
                    vehicle_type.setText(vehicleType);
                    max_num.setText(maxNum);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });*/

        mDatabase.child(uid).child("등록차량").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String carNum = dataSnapshot.getKey();
                    vehicle.add(carNum);
                }
                listview.setAdapter(vehicle);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        //리스트에서 선택한거
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String carNum = (String) parent.getItemAtPosition(position);
            }
        });*/




    }
}