package com.iyad.sultan.notes.Controller;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyad.sultan.notes.Model.Note;
import com.iyad.sultan.notes.R;

import io.realm.RealmResults;

/**
 * Created by salkhmis on 9/1/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private RealmResults<Note> NoteItems;


    public NoteAdapter(RealmResults<Note> noteList) {

        NoteItems = noteList;
        // http://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview

    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get shape for View

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {

        final Note note = NoteItems.get(position);
        holder.primaryText.setText(note.getTitle());
        holder.secondaryText.setText(note.getDescription().trim());

        switch (note.getNoteColor()) {

            case 1:
                holder.itemView.setBackgroundColor(Color.rgb(156, 39, 176));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.rgb(76,150,80));
                break;
            case 3:
                holder.itemView.setBackgroundColor(Color.rgb(96, 125, 139));
                break;
            case 4:
                holder.itemView.setBackgroundColor(Color.rgb(0, 188, 212));
                break;
            case 5:
                holder.itemView.setBackgroundColor(Color.rgb(240, 98, 146));
                break;

        }
        holder.dateText.setText(note.getDate());

    }

    @Override
    public int getItemCount() {

        return NoteItems.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {


        public ImageView imgageFav;
        public TextView primaryText;
        public TextView dateText;
        public View rootLayout;
        public TextView secondaryText;


        public NoteViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.primary_text);
            secondaryText = (TextView) itemView.findViewById(R.id.scondary_text);
            imgageFav = (ImageView) itemView.findViewById(R.id.img_fav);
            rootLayout = itemView.findViewById(R.id.liner_row_root);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
