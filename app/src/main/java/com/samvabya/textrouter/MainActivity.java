package com.samvabya.textrouter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView ussr, t;
    Button b;
    EditText e;
    String username, MUX = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ussr = findViewById(R.id.ussr);
        t = findViewById(R.id.tv1);
        e = findViewById(R.id.editTextTextPersonName);
        b = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("drt");
        DatabaseReference refwrt = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MUX += snapshot.getValue().toString() + "\n";
                t.setText(MUX);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ussr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = e.getText().toString();
                if (!a.isEmpty()){
                    ondrt wrt = new ondrt(username+": "+a);
                    refwrt.setValue(wrt);
                    e.setText("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        else  {
            username = user.getEmail().toString().substring(0, user.getEmail().toString().lastIndexOf('@'));
            ussr.setText(username);
        }
    }
}