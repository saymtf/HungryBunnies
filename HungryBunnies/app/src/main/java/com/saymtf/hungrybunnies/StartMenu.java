package com.saymtf.hungrybunnies;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StartMenu extends Activity {

    Button playGameButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);


        playGameButton = (Button) findViewById(R.id.playGameButton);
        //settingsButton = (Button) findViewById(R.id.settingsButton);

        playGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), GameActivity.class);
                    startActivity(intent);
                }
             }
        );


    }

}
