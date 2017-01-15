package com.example.user.redpocket_androidmanage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText accountText;
    private EditText passWordText;
    private FirebaseAuth mfirebaseAuth;
    private Button buttonLogin;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mfirebaseAuth = FirebaseAuth.getInstance();
        accountText = (EditText) findViewById(R.id.editText);
        passWordText = (EditText) findViewById(R.id.editText2);
        buttonLogin = (Button) findViewById(R.id.button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eMailLogin();
               checkManager();
               finish ();

            }
        });


    }

    private void eMailLogin() {
         String email = "manager@gmail.com";
         String password = "manager@gmail.com";


        mfirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.w("TAG", "signInWithEmail:failed", task.getException());
                }
            }
        });

    }

    private void checkManager() {
        final String account = accountText.getText().toString();
        final String acpassword = passWordText.getText().toString();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("admins");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (account.equalsIgnoreCase(dataSnapshot.child("account").getValue().toString()) && acpassword.equalsIgnoreCase(dataSnapshot.child("password").getValue().toString())) {

                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}




