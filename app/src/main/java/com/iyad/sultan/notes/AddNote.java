package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Administrator on 8/24/2016.
 */
public class AddNote extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        //always from RTL Direction API 17
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
