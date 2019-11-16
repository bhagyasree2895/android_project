package com.example.getroomiecode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.parse.GetCallback;
import com.parse.LogInCallback;
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
    public static String object_id = null;

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
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
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
            final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
            dlg.setTitle("Please, wait a moment.");
            dlg.setMessage("Logging in...");
            dlg.show();

            ParseUser.logInInBackground(
                    username.getText().toString(),
                    pass.getText().toString(),
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                dlg.dismiss();
                                Toast.makeText(MainActivity.this, "Sucessful Login", Toast.LENGTH_LONG).show();
                                object_id = parseUser.getObjectId();
                                 try {
                                    Intent toOtherIntent = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(toOtherIntent);
                                } catch (Exception e1) {
                                }
                                alertDisplayer("Sucessful Login","Welcome back " + username.getText().toString() + "!");

                            } else {
                                dlg.dismiss();
                                ParseUser.logOut();
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //username= username.getText().toString();
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        intent.putExtra("email", username.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
