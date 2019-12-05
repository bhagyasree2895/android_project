package com.example.getroomiecode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText fullName;
    EditText mobile;
    EditText username;
    EditText pass;
    EditText confirmPass;
    //RadioGroup gender;
    RadioButton radioButtonGender;
    Button signUp;
    String genderStr;
    public static String object_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullName = findViewById(R.id.fullNameET);
        RadioGroup gender =  findViewById(R.id.RadioGender);

            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    radioButtonGender = findViewById(checkedId);
                    genderStr = radioButtonGender.getText().toString();
                }
            });
        mobile = findViewById(R.id.mobileET);
        username = findViewById(R.id.userIdET);
        pass = findViewById(R.id.passwordET);
        confirmPass = findViewById(R.id.confirmPswdET);
        signUp = findViewById(R.id.signUpBTN1);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
    }

    //SignIn action
    public void gotoSuccessfulSignUpActivityAction(View v) {
        //Fullname Field validation
        final String fName = fullName.getText().toString();
        final String mobNum = mobile.getText().toString();
        final String user = username.getText().toString();
        final String p = pass.getText().toString();
        final String Cpass = confirmPass.getText().toString();
        if (fName.length() == 0) {
            fullName.requestFocus();
            fullName.setError("Name field cannot be empty!!");
        } else if (!fName.matches("[a-zA-Z ]+")) {
            fullName.requestFocus();
            fullName.setError("ENTER ONLY ALPHABETICAL CHARACTER");
        }

        //Mobile Number validation

        else if (mobNum.length() == 0 || mobNum.length() < 10) {
            mobile.requestFocus();
            mobile.setError("Mobile Field is Empty/ too short");
        } else if (!Pattern.matches("[0-9]+", mobNum)) {
            mobile.setError("Mobile Field should contain only numerical values");
        }

        //UserId validation

        else if (user.length() == 0) {
            username.requestFocus();
            username.setError("UserName field cannot be empty!!");
        }

        //Password validation
        else if (p.length() < 8 && !isValidPassword(p)) {
            pass.requestFocus();
            pass.setError("Enter Valid Password with atleast 1 capital letter, 1 small letter, 1 number and a symbol");
        }

        //Confirm Password Validation
        else if (!(Cpass.equals(p))) {
            confirmPass.requestFocus();
            confirmPass.setError("Password and Confirm Password does not match");
        } else {
            try {
                Intent toOtherIntent = new Intent(this, SuccessfulSignUpActivity.class);
                startActivity(toOtherIntent);
            } catch (Exception e) {
            }
        }

        Log.d("Parse","Register Start");

        final ParseUser sing_up_user = new ParseUser();
        try {
        sing_up_user.put("Fullname", fullName.getText().toString());
        sing_up_user.put("Gender", genderStr);
        sing_up_user.put("Mobile", (mobile.getText().toString()));
        }
        catch(Exception e){}
        sing_up_user.setUsername(username.getText().toString());
        sing_up_user.setPassword(pass.getText().toString());
        sing_up_user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    object_id = sing_up_user.getObjectId();
                    alertDisplayer("Sucessful Signup","Successfully signed up " + username.getText().toString() + "!");
                    Toast.makeText(SignUpActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                    Log.d("Parse", "Register Success");
                } else {
                    ParseUser.logOut();
                    Toast.makeText(SignUpActivity.this, "Register Fail", Toast.LENGTH_LONG).show();
                    Log.d("Parse", "Register Fail " + e);
                }
                Log.d("Parse", "Register Finish");
            }
        });
    }
    public static boolean isValidPassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN="^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern=Pattern.compile(PASSWORD_PATTERN);
        matcher=pattern.matcher(password);
        return matcher.matches();
    }
    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(SignUpActivity.this, SuccessfulSignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
