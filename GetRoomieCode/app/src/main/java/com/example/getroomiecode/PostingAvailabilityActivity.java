package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;

public class PostingAvailabilityActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMG=1;
    EditText aptType;
    EditText availability;
    EditText address;
    EditText pincode;
    EditText mobile;
    Bitmap selectedImage;
    ParseObject room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_availability);
        aptType=findViewById(R.id.aptTypeET);
        availability=findViewById(R.id.availabilityET);
        address=findViewById(R.id.addressET);
        pincode=findViewById(R.id.pincodeET);
        mobile=findViewById(R.id.mobileET);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
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

        String picturePath = PreferenceManager.getDefaultSharedPreferences(this).getString("picturePath", "");
        if (!picturePath.equals("")) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView11);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage= BitmapFactory.decodeStream(imageStream);
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
        final String addr=address.getText().toString();
        final String pin=pincode.getText().toString();
        final String mobl=mobile.getText().toString();
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
        else if(addr.length()==0){
            address.requestFocus();
            address.setError("Address Field is empty");
        }
        else if(addr.length()>50){
            address.requestFocus();
            address.setError("Address length is too long: Max 50 Chars");
        }
        else if(pin.length()==0){
            pincode.requestFocus();
            pincode.setError("PinCode Field is empty");
        }
        else if(pin.length()>7){
            pincode.requestFocus();
            pincode.setError("PinCode length is too long: Max 6 Chars");
        }
        else if(mobl.length()==0){
            mobile.requestFocus();
            mobile.setError("Mobile Field is empty");
        }
        else if (!Pattern.matches("[0-9]+",mobl)){
            mobile.setError("Mobile Field should contain only numerical values");
        }
        else if(mobl.length()!=10){
            mobile.requestFocus();
            mobile.setError("Mobile Number length must be 10");
        }
        else {
            try {
                Intent toOtherIntent = new Intent(this, SubmitActivity.class);
                startActivity(toOtherIntent);
            } catch (Exception e) {
            }
        }
        room= new ParseObject("Room");
        room.put("AptType",apt);
        room.put("Availability",avail);
        room.put("Address",addr);
        room.put("Pincode",pin);
        room.put("MobileOrEmail",mobl);
//        Bitmap imageBitmap = selectedImage;
////        // Locate the image in res >
////
////        Bitmap bitmap = BitmapFactory.decodeFile("picturePath");
////        // Convert it to byte
//       Byte[] stream = new Byte();
//        // Compress image to lower quality scale 1 - 100
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100,ByteArrayOutputStream(stre));
//
//        Object image = null;
//        try {
//            String path = null;
//            image = imageBitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Create the ParseFile
//        ParseFile file = new ParseFile("picturePath", (byte[]) image);
//        // Upload the image into Parse Cloud
//        file.saveInBackground();
//
//        // Create a New Class called "ImageUpload" in Parse
//        //ParseObject imgupload = new ParseObject("Image");
//
//        // Create a column named "ImageName" and set the string
//        room.put("Image", "picturePath");
//
//
//        // Create a column named "ImageFile" and insert the image
//        room.put("ImageFile", file);

        // Create the class and the columns
        //imgupload.saveInBackground();

        // Show a simple toast message



//        files.saveInBackground(new SaveCallback() {
//
//            @Override
//            public void done(ParseException exception) {
//                if (exception == null) {
//                    room.put("<parse-column-name>", files);
//                    ParseUser.getCurrentUser().saveInBackground();
//                }
//            }
//        });

        //I think parse has similar support If not this
        //room.put("Roomphoto",imageBitmap);
        //room.saveInBackground();
        Log.d("Parse","Room Object saved pc "+ room.getObjectId());
        room.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getApplicationContext(),"Save "+e,Toast.LENGTH_LONG).show();
                Log.d("Parse","Room Object saved cb "+ room.getObjectId());
                Log.d("Parse","Room Saved at "+ room.getCreatedAt());

            }
        });
        Log.d("Parse","Room Object saved ac "+ room.getObjectId());
    }
}

