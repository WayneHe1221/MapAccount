package com.chun.mapaccount;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Wayne on 2017/12/20.
 */

public class SetPlace extends Activity {
    private Button finish_setplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_place);

        finish_setplace = (Button) findViewById(R.id.finish_setplace);
        finish_setplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetPlace.this.finish();
            }
        });
    }
}
