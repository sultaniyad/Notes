package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

/**
 * Created by Administrator on 8/24/2016.
 */
public class reader extends AppCompatActivity {
    //Keys
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION ="DESCRIPTION";
    private static final String DATE = "DATE";
    private   Bundle bundle;


    private TextView title;
    private TextView des;
    private TextView date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader);

        title = (TextView) findViewById(R.id.text_title);
        des = (TextView) findViewById(R.id.text_description);
        date = (TextView) findViewById(R.id.text_date);

        bundle = getIntent().getExtras();
        if(bundle !=null) {
             title.setText( bundle.getString(TITLE,"Null"));
             des.setText(bundle.getString(DESCRIPTION,"Null"));
            date.setText(bundle.getString(DATE,"Null"));

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_and_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.back:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.delete:
                Toast.makeText(getApplicationContext(), "add note now.", Toast.LENGTH_LONG).show();
                addNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNote() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
