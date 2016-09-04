package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iyad.sultan.notes.Model.Note;

import io.realm.Realm;
import io.realm.RealmAsyncTask;


/**
 * Created by Administrator on 8/24/2016.
 */
public class AddNote extends AppCompatActivity {
    Realm realmDB;
    RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        //always from RTL Direction API 17
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //Realm object Configurations
        realmDB = Realm.getDefaultInstance();

    }

    public void InsertNote(View v){
        realmAsyncTask = realmDB.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"onSuccess",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(),"OnError",Toast.LENGTH_SHORT).show();
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
        if(realmAsyncTask !=null && !realmAsyncTask.isCancelled())
            realmAsyncTask.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
