package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class PostingAvailabilityActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMG=1;
    EditText aptType;
    EditText availability;
    EditText address;
    EditText costET;
    EditText mobile;
   // Bitmap selectedImage;
    ParseObject room;
    ImageView addimage;
    Button submit;
    Button addimgbutton;
    private static ParseObject imgupload;
    private String image_object_id;
    private static final int RESULT_LOAD=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_availability);
        aptType=findViewById(R.id.aptTypeET);
        availability=findViewById(R.id.availabilityET);
        address=findViewById(R.id.addressET);
        costET =findViewById(R.id.priceET);
        mobile=findViewById(R.id.mobileET);
        addimgbutton=findViewById(R.id.uploadimageBTN);
        //roomImage=findViewById(R.id.imageView11);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        addimage = findViewById(R.id.imageView11);
        addimgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });
    }
    public void gotohomePage(View v) {
        try {
            Intent toOtherIntent = new Intent(this,SignInActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, RESULT_LOAD);
        }
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        // ImageView imageView11=findViewById(R.id.imageView11);

//        String picturePath = PreferenceManager.getDefaultSharedPreferences(this).getString("picturePath", "");
//        if (!picturePath.equals("")) {
//            ImageView imageView = (ImageView) findViewById(R.id.imageView11);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }

        switch (reqCode) {
            case RESULT_LOAD:
                if (resultCode == RESULT_OK) {
                   /* Uri selectedImage = data.getData();
                    String[] filePathColumn1 = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn1, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex= cursor.getColumnIndex(filePathColumn1[0]);
                    String picturepath1 = cursor.getString(columnIndex);
                    Bitmap bitmap = BitmapFactory.decodeFile(picturepath1);*/
                    Bundle extras = data.getExtras();
                    if (extras != null && extras.get("data") != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        addimage.setImageBitmap(imageBitmap);
                        uploadPictureToParse(imageBitmap);
                    }

                }
        }
    }
        private void uploadPictureToParse(Bitmap path){
            ParseFile file = null;
            try {
                file = new ParseFile("picturePath", readInFile(getFileFromBitMap(path).getPath()));
                Log.d("check","check picture path"+ readInFile(getFileFromBitMap(path).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Upload the image into Parse Cloud
            file.saveInBackground();
            submit=findViewById(R.id.submitBTN);
            // Create a New Class called "ImageUpload" in Parse
            imgupload = new ParseObject("Image");

            // Create a column named "ImageName" and set the string
            imgupload.put("Image", "picturePath");


            // Create a column named "ImageFile" and insert the image
            imgupload.put("ImageFile", file);

            // Create the class and the columns
            imgupload.saveInBackground();

            submit.setEnabled(true);


            // Show a simple toast message
            Toast.makeText(this, "Image Saved ",Toast.LENGTH_SHORT).show();

        }
    private byte[] readInFile(String path) throws IOException {
        // TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();
    }
    private File getFileFromBitMap(Bitmap bitmap){

        File file = new File(getCacheDir(), "image_"+System.currentTimeMillis());
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public void onSubmit(View v) {
        try {
            final String apt = aptType.getText().toString();
            final String avail = availability.getText().toString();
            final String addr = address.getText().toString();
            final String cost = costET.getText().toString();
            final String mobl = mobile.getText().toString();
            if (apt.length() == 0) {
                aptType.requestFocus();
                aptType.setError("AptType field cannot be empty!!");
            } else if (avail.length() == 0) {
                availability.requestFocus();
                availability.setError("Availability Field is Empty");
            } else if (!Pattern.matches("[0-9]+", avail)) {
                availability.setError("Availability Field should contain only numerical values");
            } else if (avail.length() > 2) {
                availability.requestFocus();
                availability.setError("Availability Field is too Long");
            } else if (addr.length() == 0) {
                address.requestFocus();
                address.setError("Address Field is empty");
            } else if (addr.length() > 50) {
                address.requestFocus();
                address.setError("Address length is too long: Max 50 Chars");
            } else if (cost.length() == 0) {
                costET.requestFocus();
                costET.setError("PinCode Field is empty");
            } else if (cost.length() > 7) {
                costET.requestFocus();
                costET.setError("PinCode length is too long: Max 6 Chars");
            } else if (mobl.length() == 0 || mobl.length() < 10) {
                mobile.requestFocus();
                mobile.setError("Mobile Field is Empty/ too short");
            } else if (!Pattern.matches("[0-9]+", mobl)) {
                mobile.setError("Mobile Field should contain only numerical values");
            } else {
                try {
                    Intent toOtherIntent = new Intent(this, SubmitActivity.class);
                    startActivity(toOtherIntent);
                } catch (Exception e) {
                }
            }
            room = new ParseObject("Room");
            room.put("AptType", apt);
            room.put("Availability", avail);
            room.put("Address", addr);
            room.put("Cost", cost);
            room.put("Mobile", mobl);
            room.put("Tenant", MainActivity.tenantName);
            room.put("genderPreference", MainActivity.genderPreference);
            image_object_id = imgupload.getObjectId();
            Log.d("object_id", "object_id is" + image_object_id);
            Toast.makeText(PostingAvailabilityActivity.this, "object is is " + image_object_id, Toast.LENGTH_SHORT).show();
            room.put("car_image_object_id", image_object_id);

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
            Log.d("Parse", "Room Object saved pc " + room.getObjectId());
            room.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    //Toast.makeText(getApplicationContext(),"Save "+e,Toast.LENGTH_LONG).show();
                    Log.d("Parse", "Room Object saved cb " + room.getObjectId());
                    Log.d("Parse", "Room Saved at " + room.getCreatedAt());

                }
            });
            Log.d("Parse", "Room Object saved ac " + room.getObjectId());
        }
        catch(Exception e){}

    }
}

