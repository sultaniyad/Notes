package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iyad.sultan.notes.Model.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmAsyncTask;


/**
 * Created by Administrator on 8/24/2016.
 */
public class AddNote extends AppCompatActivity {
    Realm realmDB;
    RealmAsyncTask realmAsyncTask;

    Calendar c;
    SimpleDateFormat df;
    EditText title;
    EditText description;
    RadioGroup rd;
    int color;
    String date;
    String theTitle;
    String dd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        //always from RTL Direction API 17
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Realm object Configurations
        realmDB = Realm.getDefaultInstance();

        //Access Data
        title = (EditText) findViewById(R.id.Title);
        description = (EditText) findViewById(R.id.des);
        rd = (RadioGroup) findViewById(R.id.RG);

    }

    public void InsertNote(View v) {
//get Color
        switch (rd.getCheckedRadioButtonId()) {
            case R.id.Orange:
                color = 1; break;
            case R.id.lightGreen:
                color = 2;
                break;
            case R.id.lightBlue:
                color = 3
                ;
                break;
            case R.id.Purple:
                color = 4;
                break;
            case R.id.Cyan:
                color = 5;
                break;

        }
        //get Note Title and Description and Time ;
        c = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MMM", new Locale("ar"));
        date = df.format(c.getTime());


        if (color != 0 && title.getText().toString().length() > 0 && description.getText().toString().length() > 0)
            addNote(title.getText().toString(), description.getText().toString(), color, date);
        else
            Toast.makeText(getApplicationContext(), "تأد من كتابة العنوان و الملاجظة وأختيار الون", Toast.LENGTH_SHORT).show();
    }

    void addNote(final String title_P, final String description_P, final int color_p, final String date_P) {
        realmAsyncTask = realmDB.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.createObject(Note.class);
                note.setTitle(title_P);
                note.setDescription(description_P);
                note.setNoteColor(color_p);
                note.setDate(date_P);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "onSuccess", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "OnError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (realmAsyncTask != null && !realmAsyncTask.isCancelled())
            realmAsyncTask.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
