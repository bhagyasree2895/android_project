package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

//    EditText fullName;
//    EditText gender;
//    EditText mobile;
//    EditText username;
//    EditText password;
//    EditText confirmPass;
//    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        fullName=findViewById(R.id.fullNameET);
//        gender=findViewById(R.id.genderET);
//        mobile=findViewById(R.id.mobileET);
//        username=findViewById(R.id.userIdET);
//        password=findViewById(R.id.passwordET);
//        confirmPass=findViewById(R.id.confirmPswdET);
//        signUp=findViewById(R.id.signInBTN);
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkDataEntered();
//            }
//        });
    }
    //SignIn action
    public void gotoSuccessfulSignUpActivityAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SuccessfulSignUpActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
//    void checkDataEntered(){
//        if(isEmpty(fullName)){
//            Toast t=Toast.makeText(this,"You must enter your full name!!!!!",Toast.LENGTH_SHORT);
//            t.show();
//        }
//    }
//    boolean isEmpty(EditText text){
//        CharSequence str=text.getText().toString();
//        return TextUtils.isEmpty(str);
//    }
}
