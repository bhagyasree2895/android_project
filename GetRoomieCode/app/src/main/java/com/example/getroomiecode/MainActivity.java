package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.usernameET);
        pass=findViewById(R.id.passwordET);
    }

    //SignIn action
    public void gotoSignInAction(View v) {
        final String user=username.getText().toString();
        final String p=pass.getText().toString();
        if(user.length()==0){
            username.requestFocus();
            username.setError("Name field cannot be empty!!");
        }
        else if(p.length()<8&&!isValidPassword(p)){
            pass.requestFocus();
            pass.setError("Enter Valid Password with atleast 1 capital letter, 1 small letter, 1 number and a symbol");
        }
        else {
            try {
                Intent toOtherIntent = new Intent(this, SignInActivity.class);
                startActivity(toOtherIntent);

            } catch (Exception e) {
            }
        }
    }
    //SignIn action
    public void gotoSignUpAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SignUpActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}
