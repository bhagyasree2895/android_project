package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseInstallation;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.usernameET);
        pass=findViewById(R.id.passwordET);
        TextView frgtPwd=findViewById(R.id.frgtPwdTV);
        frgtPwd.setPaintFlags(frgtPwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    //SignIn action
    public void gotoSignInAction(View v) {
        final String user=username.getText().toString();
        final String p=pass.getText().toString();
        if(user.length()==0){
            username.requestFocus();
            username.setError("Name field cannot be empty!!");
        }

        else if(p.length()<9&&!isValidPassword(p)){
            pass.requestFocus();
            pass.setError("Enter Valid Password with atleast min 8 Chars with atleast 1 capital letter, 1 small letter, 1 number and a symbol");
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
