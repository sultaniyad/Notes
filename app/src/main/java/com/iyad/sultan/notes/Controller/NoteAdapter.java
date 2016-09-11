package com.iyad.sultan.notes.Controller;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iyad.sultan.notes.Model.Note;
import com.iyad.sultan.notes.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by salkhmis on 9/1/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Realm realm;
    private RealmResults<Note> NoteItems;
private NoteClickCallback noteClickCallback;


    public NoteAdapter(RealmResults<Note> noteList) {

        NoteItems = noteList;

        // http://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview

    }

    public interface NoteClickCallback{
        void onClickNote(int position);
    }

   public void setNoteClickBack(final NoteClickCallback noteClickCallback){

        this.noteClickCallback = noteClickCallback;
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

            case 5:
                holder.itemView.setBackgroundColor(Color.rgb(156, 39, 176));
                break;
            case 4:
                holder.itemView.setBackgroundColor(Color.rgb(76, 150, 80));
                break;
            case 3:
                holder.itemView.setBackgroundColor(Color.rgb(96, 125, 139));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.rgb(0, 188, 212));
                break;
            case 1:
                holder.itemView.setBackgroundColor(Color.rgb(240, 98, 146));
                break;

        }
        holder.dateText.setText(note.getDate());
        holder.imgageFav.setImageResource(note.getFavorite() ? R.drawable.ic_favorite_black_36dp : R.drawable.ic_favorite_white_36dp);

    }

    @Override
    public int getItemCount() {

        return NoteItems.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ImageView imgageFav;
        public TextView primaryText;
        public TextView dateText;
        public View rootLayout;
        public TextView secondaryText;
        private boolean currentStatus;


        public NoteViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.primary_text);
            secondaryText = (TextView) itemView.findViewById(R.id.scondary_text);
            imgageFav = (ImageView) itemView.findViewById(R.id.img_fav);
            rootLayout = itemView.findViewById(R.id.liner_row_root);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
            imgageFav.setOnClickListener(this);
            rootLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.img_fav) {
                currentStatus = NoteItems.get(getAdapterPosition()).getFavorite();
                imgageFav.setImageResource(!currentStatus ? R.drawable.ic_favorite_black_36dp : R.drawable.ic_favorite_white_36dp);
                updateFavStatus(getAdapterPosition(), !currentStatus);

            } else
               noteClickCallback.onClickNote(getAdapterPosition());
        }

        void updateFavStatus(final int p, final Boolean isFav) {

            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        NoteItems.get(p).setFavorite(isFav);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (realm != null) realm.close();
            }

        }
    }
}
