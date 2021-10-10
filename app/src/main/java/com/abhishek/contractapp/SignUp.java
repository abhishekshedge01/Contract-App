package com.abhishek.contractapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText fullname, email, password, confirm_password;
    TextView login_tv;
    Button signup_button;
    ProgressDialog progressDialogsignup;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        fullname = findViewById(R.id.signup_fullname);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirm_password = findViewById(R.id.signup_confirm_password);
        login_tv = findViewById(R.id.signup_login_textview);
        signup_button = findViewById(R.id.signup_signup_button);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialogsignup=new ProgressDialog(this);

        signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Register()
    {
        String string_fullname = fullname.getText().toString();
        String string_email = email.getText().toString();
        String string_password = password.getText().toString();
        String string_confirmpassword = confirm_password.getText().toString();

        if (TextUtils.isEmpty(string_fullname))
        {
            fullname.setError("enter your name");
            return;
        }

        if (TextUtils.isEmpty(string_email))
        {
            email.setError("enter your mail");
            return;
        }

        if (!checkUser(string_email)) {
            email.setError("invalid email format");
            return;
        }

        if (TextUtils.isEmpty(string_password))
        {
            password.setError("enter your password");
            return;
        }

        if (!TextUtils.isEmpty(string_password))
        {
            if (string_password.length() < 8) {
                password.setError("password length <8");
                return;
            }
        }

        if (TextUtils.isEmpty(string_confirmpassword))
        {
            confirm_password.setError("enter your password");
            return;
        }

        if(!string_password.equals(string_confirmpassword))
        {
            confirm_password.setError("password not similar to above field");
            return;
        }
        progressDialogsignup.setMessage("Processing...");
        progressDialogsignup.setCanceledOnTouchOutside(false);
        progressDialogsignup.show();

        firebaseAuth.createUserWithEmailAndPassword(string_email,string_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                     Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                     Intent intent=new Intent(getApplicationContext(),ContractList.class);
                     startActivity(intent);
                     finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Account Already Exists!",Toast.LENGTH_SHORT).show();
                }
                progressDialogsignup.dismiss();
            }
        });
    }

    private boolean checkUser(String target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}