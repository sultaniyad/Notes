package com.iyad.sultan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.iyad.sultan.notes.Controller.NoteAdapter;
import com.iyad.sultan.notes.Model.Note;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteClickCallback{
    private Realm realm;
    private RealmQuery<Note> query;
    private RealmResults<Note> result;
    NoteAdapter adapter;

    //Keys
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION ="DESCRIPTION";
    private static final String DATE = "DATE";
    private   Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set Transitions
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        realm = Realm.getDefaultInstance();
        query = realm.where(Note.class);
        result = query.findAll();
        adapter = new NoteAdapter(result);

        //OnclickListener set
        adapter.setNoteClickBack(this);

        //RecyclerView and ItemTouchHelper
        RecyclerView rec = (RecyclerView) findViewById(R.id.recycler_note);
        rec.setAdapter(adapter);
        rec.setItemAnimator(new DefaultItemAnimator());
        rec.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(createHelperCallback());
        helper.attachToRecyclerView(rec);


//http://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
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

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        //    moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                         deleteNote(viewHolder.getAdapterPosition());

                    }
                };
        return simpleItemTouchCallback;
    }

    private void deleteNote(final int p) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteFromRealm(p);
                adapter.notifyItemRemoved(p);

            }
        });


    }/*
    private void moveItem(int oldPos, int newPos) {

        ListItem item = (ListItem) listData.get(oldPos);
        listData.remove(oldPos);
        listData.add(newPos, item);
        adapter.notifyItemMoved(oldPos, newPos);
    }*/


    @Override
    public void onClickNote(int position) {
        bundle = new Bundle();
        bundle.putString(TITLE,result.get(position).getTitle());
        bundle.putString(DESCRIPTION,result.get(position).getDescription());
        bundle.putString(DATE, result.get(position).getDate());

        startActivity(new Intent(getApplicationContext(),reader.class).putExtras(bundle));
    }
}
