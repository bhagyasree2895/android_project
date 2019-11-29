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

public class filterActivity extends AppCompatActivity {
    public int costValue;
    String genderStr;
    RadioGroup gender;
    RadioButton radioButtonGender;
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
        int availability=Integer.parseInt(String.valueOf(availabilityET.getText()));

        RadioGroup gender =  findViewById(R.id.RadioGender);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonGender = findViewById(checkedId);
                genderStr = radioButtonGender.getText().toString();
            }
        });
        Intent intent=new Intent(this,RoomsListView.class);
        intent.putExtra("availability",availability);
        Log.d("availability", String.valueOf(availability));
        intent.putExtra("costValue",costValue);
        intent.putExtra("genderPreference",genderStr);
        setResult(rescode,intent);
        finish();

    }

}
