package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iyad.sultan.notes.Controller.NoteAdapter;
import com.iyad.sultan.notes.Model.Note;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RealmQuery<Note> query;
    private RealmResults<Note> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set Transitions
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        realm = Realm.getDefaultInstance();
        query = realm.where(Note.class);
        result = query.findAll();


        RecyclerView rec = (RecyclerView) findViewById(R.id.recycler_note);
         rec.setAdapter(new NoteAdapter(result));
         rec.setItemAnimator(new DefaultItemAnimator());
         rec.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private void deleteNote() {

    }

    private void refresh() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivity(new Intent(getApplicationContext(), AddNote.class));
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        return super.onOptionsItemSelected(item);
    }


}
