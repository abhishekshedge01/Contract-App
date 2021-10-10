package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login_button;
    ProgressDialog progressDialoglogin;
    FirebaseAuth firebaseAuth;
    TextView signup_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        signup_tv=findViewById(R.id.login_signup_textview);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        firebaseAuth=FirebaseAuth.getInstance();
        login_button=findViewById(R.id.login_login_button);
        progressDialoglogin=new ProgressDialog(this);

        signup_tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login()
    {
        String str1=email.getText().toString();
        String str2=password.getText().toString();

        if (TextUtils.isEmpty(str1)) {
            email.setText("Mail not entered");
            return;
        }

        if (!checkUser(str1)) {
            email.setError("invalid email format");
            return;
        }

        if (TextUtils.isEmpty(str2)) {
            password.setText("Password not entered");
            return;
        }

        if (str2.length()<8) {
            password.setError("Password length <8");
            return;
        }

        progressDialoglogin.setMessage("Processing...");
        progressDialoglogin.setCanceledOnTouchOutside(false);
        progressDialoglogin.show();

        firebaseAuth.signInWithEmailAndPassword(str1,str2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(getApplicationContext(),ContractList.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Login.this,"Wrong Email or password entered",Toast.LENGTH_LONG).show();
                }
                progressDialoglogin.dismiss();
            }
        });
    }
    private boolean checkUser(String target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}