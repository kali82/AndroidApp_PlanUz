package com.blackhammers.kalisz.planuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InformatykiActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatyki);

        textView = (TextView) findViewById(R.id.textId);

        String bundle = getIntent().getExtras().getString(null);


            if (bundle.) {

                    textView.setText(getIntent().getStringExtra("Infname"));



            }



    }
}
