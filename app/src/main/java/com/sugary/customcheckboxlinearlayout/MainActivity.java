package com.sugary.customcheckboxlinearlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBoxLayout checkBoxLayout = (CheckBoxLayout)findViewById(R.id.main_box_layout);
        RadioGroup styleRadioGroup = (RadioGroup)findViewById(R.id.main_radio_group_style);
        RadioGroup selectRadioGroup = (RadioGroup)findViewById(R.id.main_radio_group_select);


        checkBoxLayout.setOnSelectedListener(new CheckBoxLayout.onSelectedListener() {
            @Override
            public void onSelected(View view,int position) {
                Toast.makeText(MainActivity.this,"position="+position,Toast.LENGTH_SHORT).show();
            }
        });


        styleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_radio_group_style_big:
                        checkBoxLayout.setResId(R.drawable.big_star_selector);
                        break;
                    case R.id.main_radio_group_style_small:
                        checkBoxLayout.setResId(R.drawable.star_selector);
                        break;
                }

            }
        });

        selectRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_radio_group_select_enable:
                        checkBoxLayout.setCheckEnable(true);
                        break;
                    case R.id.main_radio_group_select_disable:
                        checkBoxLayout.setCheckEnable(false);
                        break;
                }

            }
        });

    }

}
