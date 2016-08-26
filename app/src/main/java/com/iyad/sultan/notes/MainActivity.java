package com.iyad.sultan.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
private Realm realm;
    private LinearLayout rootLayout;
    private RelativeLayout childLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Transition
        //Transition mFadeTransition =
                //TransitionInflater.from(this).
                     //   inflateTransition(R.transition.activity_slide);

        realm = Realm.getDefaultInstance();
       // rootLayout =(LinearLayout)findViewById(R.id.rootLayout);
       // TextView t=new TextView(this);
       // t.setText("12354674897");
       // rootLayout.addView(t);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }



   private void addNote(View v){

    }

    private void  deleteNote(){

    }

    private void refresh(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       startActivity(new Intent(getApplicationContext(),AddNote.class));

        return super.onOptionsItemSelected(item);
    }


}
