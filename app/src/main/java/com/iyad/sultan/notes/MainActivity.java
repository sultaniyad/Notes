package com.iyad.sultan.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.iyad.sultan.notes.Controller.NoteAdapter;
import com.iyad.sultan.notes.Model.Note;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteClickCallback,reader.OnNoteDeleteClicked {
    private Realm realm;
    private RealmQuery<Note> query;
    private RealmResults<Note> result;
    private RealmAsyncTask  realmAsyncTask;
    NoteAdapter adapter;
    int x= 0;

    //Keys
    private static final String TITLE="TITLE";
    private static final String DESCRIPTION ="DESCRIPTION";
    private static final String DATE = "DATE";
    private static final String POSITION = "POSITION";

    private   Bundle bundle;
    private RecyclerView rec;
    private Paint p = new Paint();

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
        Toast.makeText(MainActivity.this, "Create", Toast.LENGTH_SHORT).show();
        //OnclickListener set
        adapter.setNoteClickBack(this);

        //RecyclerView and ItemTouchHelper
        rec = (RecyclerView) findViewById(R.id.recycler_note);
        rec.setAdapter(adapter);
        rec.setItemAnimator(new DefaultItemAnimator());
        rec.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper = new ItemTouchHelper(createHelperCallback());
        helper.attachToRecyclerView(rec);

        //Initialize Delete INTERFACE
        reader.setNoteonDeleteClicked(this);

//http://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm != null){
        realm.close();
        realm = null;}
    }


    private void refresh() {
     adapter.notifyDataSetChanged();
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
                        if(swipeDir == ItemTouchHelper.LEFT)
                         deleteNote(viewHolder.getAdapterPosition());
                        else deleteNoteV2(viewHolder.getAdapterPosition());

                    }
                    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        Bitmap icon;
                        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                            View itemView = viewHolder.itemView;
                            float height = (float) itemView.getBottom() - (float) itemView.getTop();
                            float width = height / 3;

                            if(dX > 0){
                                p.setColor(Color.parseColor("#388E3C"));
                                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                                c.drawRect(background,p);
                                icon = BitmapFactory.decodeResource(getResources(), R.drawable.edit_swap);
                                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                                c.drawBitmap(icon,null,icon_dest,p);
                            } else {
                                p.setColor(Color.parseColor("#D32F2F"));
                                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                                c.drawRect(background,p);
                                icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete_swap);
                                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                                c.drawBitmap(icon,null,icon_dest,p);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
                Toast.makeText(MainActivity.this, "تم حذف", Toast.LENGTH_SHORT).show();

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
        bundle.putInt(POSITION,position);
        startActivity(new Intent(getApplicationContext(),reader.class).putExtras(bundle));
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void noteDeleleteClicked(int position) {
        deleteNote(position);

    }

    @Override
    public void updateNoteClicked(int position_par, String title_par,String des_par) {

        updateNote(position_par,title_par,des_par);

    }

    void updateNote(int position,String tit,String des){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    void deleteNoteV2(final int pop){

        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                //back thread
                //result.deleteFromRealm(pop);
               // adapter.notifyItemRemoved(pop);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
               //on UI thread
                Toast.makeText(getApplicationContext(), " Rتم الحدف", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //on UI Thread
               error.printStackTrace();
            }
        });
    }
}
