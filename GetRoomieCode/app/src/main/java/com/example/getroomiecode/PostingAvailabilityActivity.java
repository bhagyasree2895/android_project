package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class PostingAvailabilityActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMG=1;
    EditText aptType;
    EditText availability;
    EditText address;
    EditText pincode;
    EditText mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_availability);
        aptType=findViewById(R.id.aptTypeET);
        availability=findViewById(R.id.availabilityET);
        address=findViewById(R.id.addressET);
        pincode=findViewById(R.id.pincodeET);
        mobile=findViewById(R.id.mobileET);

    }
    public void gotohomePage(View v) {
        try {
            Intent toOtherIntent = new Intent(this,SignInActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotouploadimage(View v) {
        try {
            //Intent toOtherIntent = new Intent(this,UploadImageActivity.class);
            //startActivity(toOtherIntent);
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

        } catch (Exception e) {

        }

    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        ImageView imageView11=findViewById(R.id.imageView11);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView11.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PostingAvailabilityActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(PostingAvailabilityActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    public void onSubmit(View v) {
        final String apt=aptType.getText().toString();
        final String avail=availability.getText().toString();
        if(apt.length()==0){
            aptType.requestFocus();
            aptType.setError("AptType field cannot be empty!!");
        }
        else if(avail.length()==0){
            availability.requestFocus();
            availability.setError("Availability Field is Empty");
        }
        else if (!Pattern.matches("[0-9]+",avail)){
            availability.setError("Availability Field should contain only numerical values");
        }
        else if(avail.length()>2){
            availability.requestFocus();
            availability.setError("Availability Field is too Long");
        }


        else {
            try {
                Intent toOtherIntent = new Intent(this, SubmitActivity.class);
                startActivity(toOtherIntent);

            } catch (Exception e) {
            }
        }

    }
}
