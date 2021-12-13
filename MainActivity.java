package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button on, off;
    TextView txt;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        on = (Button) findViewById(R.id.btnOn);
        off = (Button) findViewById(R.id.btnOff);
        txt = (TextView) findViewById(R.id.txt);
        img = (ImageView) findViewById(R.id.imageView);

        //firebase 연결
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");
        //시작하면off로 설정
        myRef.setValue("OFF");
        txt.setText(myRef.getKey());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ledState = (String) snapshot.getValue();
                txt.setText(ledState);
                if(ledState.equals("OFF")) {
                    img.setImageResource(R.drawable.lightoff);
                } else {
                    img.setImageResource(R.drawable.lighton);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue("ON");
                img.setImageResource(R.drawable.lighton);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue("OFF");
                img.setImageResource(R.drawable.lightoff);
            }
        });
    }
}
