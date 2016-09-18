package com.iyad.sultan.notes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import io.realm.Realm;

/**
 * Created by Administrator on 8/24/2016.
 */
public class reader extends AppCompatActivity {
    //Keys
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String DATE = "DATE";
    private static final String POSITION = "POSITION";
    private Bundle bundle;
    //ViewSwitcher
    private ViewSwitcher switcher;
    private EditText editText_des;
    //Menu bar Images
    private ImageView deleteImag;
    private ImageView moreImg;
    private ImageView whattappImg;
    private ImageView twitterImg;
    private ImageView saveNeweChangeImg;

    private TextView title;
    private TextView textView_des;
    private TextView date;
    private int position;
    private static OnNoteDeleteClicked onNoteDeleteClicked;

    //Anamtion
    private Animation fade_out;
    private Animation fade_in;

    //Interface
    public interface OnNoteDeleteClicked {

        public void noteDeleleteClicked(int position);

        public void updateNoteClicked(int position,String tit, String des);
    }

    public static void setNoteonDeleteClicked(OnNoteDeleteClicked noteDelete) {
        onNoteDeleteClicked = noteDelete;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        title = (TextView) findViewById(R.id.text_title);
        textView_des = (TextView) findViewById(R.id.text_description);
        date = (TextView) findViewById(R.id.text_date);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            title.setText(bundle.getString(TITLE, "Null"));
            textView_des.setText(bundle.getString(DESCRIPTION, "Null"));
            date.setText(bundle.getString(DATE, "Null"));
            position = bundle.getInt(POSITION);

        }
        //ViewSwitcher
        switcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        editText_des = (EditText) findViewById(R.id.hidden_edit_text);

        //Access to all Menu bar images
        deleteImag = (ImageView) findViewById(R.id.delete_img);

        moreImg = (ImageView) findViewById(R.id.more_img);
        whattappImg = (ImageView) findViewById(R.id.whattap_img);
        twitterImg = (ImageView) findViewById(R.id.twitter_img);
        saveNeweChangeImg = (ImageView) findViewById(R.id.save_new_change_img);

        //Animation
        fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        return super.onOptionsItemSelected(item);
    }

    private void addNote() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void share_whatsApp(View view) {
        Intent shareWattsApp = new Intent(Intent.ACTION_SEND);
        shareWattsApp.setType("text/plain");
        shareWattsApp.setPackage("com.whatsapp");
        shareWattsApp.putExtra(Intent.EXTRA_TEXT, title.getText() + "\n" + textView_des.getText());
        try {
            startActivity(shareWattsApp);
        } catch (Exception e) {
            Toast.makeText(reader.this, "تأكد من تنصيب واتساب", Toast.LENGTH_SHORT).show();

        }
    }

    public void share_twitter(View view) {
        Intent shareTwitter = new Intent(Intent.ACTION_SEND);
        shareTwitter.setType("text/plain");
        shareTwitter.setPackage("com.twitter.android");
        shareTwitter.putExtra(Intent.EXTRA_TEXT, title.getText() + "\n" + textView_des.getText());
        try {
            startActivity(shareTwitter);
        } catch (Exception e) {
            Toast.makeText(reader.this, "تأكد من تنصب تويتر", Toast.LENGTH_SHORT).show();

        }
    }


    public void more_share(View view) {
        Intent more = new Intent(Intent.ACTION_SEND);
        more.setType("text/plain");

        more.putExtra(Intent.EXTRA_TEXT, title.getText() + "\n" + textView_des.getText());
        try {
            startActivity(Intent.createChooser(more, "Share using"));
        } catch (Exception e) {
            Toast.makeText(reader.this, "Error happened! Plz send Developer", Toast.LENGTH_SHORT).show();

        }
    }

    public void edit_note(View view) {

    }

    public void delete_note(View view) {
        onNoteDeleteClicked.noteDeleleteClicked(this.position);
        Toast.makeText(reader.this, "تم الحذف", Toast.LENGTH_SHORT).show();
        finish();
    }


    public void TextViewClicked(View v) {

        switcher.showNext(); //or switcher.showPrevious();
//get Text and hide all menu buttons
        editText_des.setText(textView_des.getText());
        hide_all_meun_bar();

    }

    //click save new Change
    public void update_note(View v) {
        // onNoteDeleteClicked.updateNote(this.position,editText_des.getText().toString());
        Toast.makeText(reader.this, "" + editText_des.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void hide_all_meun_bar() {


        //set Animation
        deleteImag.setAnimation(fade_out);

        whattappImg.setAnimation(fade_out);
        moreImg.setAnimation(fade_out);
        twitterImg.setAnimation(fade_out);
        saveNeweChangeImg.setAnimation(fade_in);

        deleteImag.setVisibility(View.GONE);

        moreImg.setVisibility(View.GONE);
        whattappImg.setVisibility(View.GONE);
        twitterImg.setVisibility(View.GONE);
        saveNeweChangeImg.setVisibility(View.VISIBLE);


    }

}


