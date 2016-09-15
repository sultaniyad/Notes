package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

/**
 * Created by Administrator on 8/24/2016.
 */
public class reader extends AppCompatActivity {
    //Keys
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DATE = "DATE";
    private Bundle bundle;


    private TextView title;
    private TextView des;
    private TextView date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        title = (TextView) findViewById(R.id.text_title);
        des = (TextView) findViewById(R.id.text_description);
        date = (TextView) findViewById(R.id.text_date);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString(TITLE, "Null"));
            des.setText(bundle.getString(DESCRIPTION, "Null"));
            date.setText(bundle.getString(DATE, "Null"));

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);

        return super.onOptionsItemSelected(item);
    }

    private void addNote() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}
