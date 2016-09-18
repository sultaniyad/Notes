package com.iyad.sultan.notes;


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
        df = new SimpleDateFormat("yy/dd/MM", new Locale("ar"));
        date = df.format(c.getTime());


        if (color != 0 && title.getText().toString().length() > 0 && description.getText().toString().length() > 0)
            addNote(title.getText().toString(), description.getText().toString(), color, date);
        else
            Toast.makeText(getApplicationContext(), "تأكد من كتابة العنوان و الملاحظة وأختيار اللون", Toast.LENGTH_SHORT).show();



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
                note.setFavorite(false);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "تأكد ممن وجود مساحة كافية ع الذاكرة", Toast.LENGTH_SHORT).show();
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

        finish();
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



//https://www.youtube.com/watch?v=OKWAjw2Bajg
//http://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-in-android
//https://www.learn2crack.com/2016/02/custom-swipe-recyclerview.html