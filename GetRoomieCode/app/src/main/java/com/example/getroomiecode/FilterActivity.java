package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {
    public int costValue;
    public String genderStrFilter;
    public RadioGroup gender;
    public RadioButton radioButtonGenderFilter;
    public int rescode=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        SeekBar seekBarCost=findViewById(R.id.seekBarCost);
        final TextView seekbarTV=findViewById(R.id.seekbarTV);

        seekBarCost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekbarTV.setText(i+"$");
             costValue =i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void onApply(View v){
        EditText availabilityET=findViewById(R.id.availabilityFilterET);
        String availability=String.valueOf(availabilityET.getText());
        if(availability.equals("")){
            availability="0";
        }
        TextView costValueTV=findViewById(R.id.seekbarTV);
        String costValueStr=String.valueOf(costValue);
        try {
            gender = findViewById(R.id.RadioGenderFilter);
            int selectedId = gender.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            radioButtonGenderFilter = (RadioButton) findViewById(selectedId);
            genderStrFilter = radioButtonGenderFilter.getText().toString();
        }
        catch(Exception e){
            genderStrFilter="null";
        }
        Log.d("check","check" +genderStrFilter+"cost "+costValueStr+"availabilty "+availability);
        Intent intent = new Intent(this, RoomsListView.class);
        intent.putExtra("availability",availability);
        intent.putExtra("costValue",costValueStr);
        intent.putExtra("genderPreference",genderStrFilter);
        setResult(rescode,intent);
        finish();

    }

}
