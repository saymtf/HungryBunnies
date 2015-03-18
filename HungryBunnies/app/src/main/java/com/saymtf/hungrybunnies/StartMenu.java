package com.saymtf.hungrybunnies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class StartMenu extends Activity {

    ImageButton playGameButton;
    ImageButton optionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);


        playGameButton = (ImageButton) findViewById(R.id.playGameButton);
        optionButton = (ImageButton) findViewById(R.id.optionButton);

        playGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), GameActivity.class);
                    startActivity(intent);
                }
             }
        );

        optionButton.setOnClickListener(settingsButtonListener);

    }


    private View.OnClickListener settingsButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SettingsActivity.class);
            startActivity(intent);
        }
    };
}
