package com.chun.mapaccount;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Wayne on 2017/12/20.
 */

public class EditObj extends Activity {
    private Button finish_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_object);

        finish_edit = (Button) findViewById(R.id.finish_edit);
        finish_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditObj.this.finish();
            }
        });
    }
}
