package com.example.firbasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class signup extends AppCompatActivity {

    private EditText msignupemail,msignuppassword;
    private RelativeLayout msignup;
    private TextView mgotologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Objects.requireNonNull(getSupportActionBar()).hide();
        msignupemail=findViewById(R.id.signupemail);
        msignuppassword=findViewById(R.id.signuppassword);
        msignup=findViewById(R.id.signup);
        mgotologin=findViewById(R.id.gotologin);

        firebaseAuth= FirebaseAuth.getInstance();
        
        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(signup.this,MainActivity.class);
                startActivity(intent);
            }
        });


        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail= msignupemail.getText().toString().trim();
                String password= msignuppassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your mail first",Toast.LENGTH_LONG).show();
                }else if( password.length()<7){
                    Toast.makeText(getApplicationContext(),"Enter Long Password",Toast.LENGTH_LONG).show();
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                                sendEmailVerification();
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }


        });

    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is Sent",Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this,MainActivity.class));
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Fail to sent email",Toast.LENGTH_LONG).show();
        }
    }
}