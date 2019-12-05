package com.example.getroomiecode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class PostingAvailabilityActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMG=1;
    EditText aptType;
    EditText availability;
    EditText address;
    EditText costET;
    EditText mobile;
    Bitmap selectedImage;
    ParseObject room;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_availability);
        aptType=findViewById(R.id.aptTypeET);
        availability=findViewById(R.id.availabilityET);
        address=findViewById(R.id.addressET);
        costET =findViewById(R.id.priceET);
        mobile=findViewById(R.id.mobileET);
        imageView=findViewById(R.id.imageView11);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(PostingAvailabilityActivity.this);
            }
        });
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
    }
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your room picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            try {
                                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                imageView.setImageBitmap(image);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    public void gotohomePage(View v) {
        try {
            Intent toOtherIntent = new Intent(this, HomeActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }

//    public void gotouploadimage(View v) {
//        try {
//            //Intent toOtherIntent = new Intent(this,UploadImageActivity.class);
//            //startActivity(toOtherIntent);
//            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//            photoPickerIntent.setType("image/*");
//            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
//
//        } catch (Exception e) {
//
//        }
//
//    }
//    @Override
//    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//        ImageView imageView11=findViewById(R.id.imageView11);
//
//        String picturePath = PreferenceManager.getDefaultSharedPreferences(this).getString("picturePath", "");
//        if (!picturePath.equals("")) {
//            ImageView imageView = (ImageView) findViewById(R.id.imageView11);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
//
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                selectedImage= BitmapFactory.decodeStream(imageStream);
//                imageView11.setImageBitmap(selectedImage);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(PostingAvailabilityActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        }else {
//            Toast.makeText(PostingAvailabilityActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }
//    }
    public void onSubmit(View v) {
        final String apt=aptType.getText().toString();
        final String avail=availability.getText().toString();
        final String addr=address.getText().toString();
        final String cost= costET.getText().toString();
        final String mobl=mobile.getText().toString();
        Bitmap bimage = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        final String image=getEncoded64ImageStringFromBitmap(bimage);
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
        else if(cost.length()==0){
            costET.requestFocus();
            costET.setError("PinCode Field is empty");
        }
        else if(cost.length()>7){
            costET.requestFocus();
            costET.setError("PinCode length is too long: Max 6 Chars");
        }
        else if (mobl.length() == 0 || mobl.length() < 10) {
            mobile.requestFocus();
            mobile.setError("Mobile Field is Empty/ too short");
        }
        else if (!Pattern.matches("[0-9]+",mobl)){
            mobile.setError("Mobile Field should contain only numerical values");
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
        room.put("Cost",cost);
        room.put("Mobile",mobl);
        room.put("Tenant",MainActivity.tenantName);
        room.put("genderPreference",MainActivity.genderPreference);
        room.put("image",image);

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
                //Toast.makeText(getApplicationContext(),"Save "+e,Toast.LENGTH_LONG).show();
                Log.d("Parse","Room Object saved cb "+ room.getObjectId());
                Log.d("Parse","Room Saved at "+ room.getCreatedAt());

            }
        });
        Log.d("Parse","Room Object saved ac "+ room.getObjectId());
    }
}

