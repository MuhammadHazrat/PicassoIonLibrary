package com.example.android.picassolibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button getButton;
    private ArrayAdapter<String> adapter;
    private ImageView mainImg;
    private ProgressBar pbar;
    private TextView percentTV;
    private ConstraintLayout rootLayout;
    String percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         pbar = findViewById(R.id.pBar);
         percentTV = findViewById(R.id.tvPercent);
         spinner = findViewById(R.id.spinner);
         getButton = findViewById(R.id.btnGet);
         mainImg = findViewById(R.id.imgMain);
         ArrayList<String> arrayList = new ArrayList<String>();

         adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);


         displayImage("http://fakhirshaheen.com/cs466/scifi1.jpg");

        Ion.with(this)
            .load("http://fakhirshaheen.com/cs468/list.txt")
            .asString()
            .setCallback((e, result) -> {
                String[] places = result.split("\n");
                for (String line : places) {
                    adapter.add(line);
                }
            });

            spinner.setAdapter(adapter);
            getButton.setEnabled(true);


            getButton.setOnClickListener(v -> {

                getButton.setEnabled(false);
                String fileName = (String) spinner.getSelectedItem();

                DownloadFile(fileName);

            });



    }

    public void DownloadFile(String fileName) {
//        String url = "http://fakhirshaheen.com/cs468/" + fileName;
//        Log.d("tag", "url:" + url);


        TextView percentTv = findViewById(R.id.tvPercent);

        Ion.with(MainActivity.this)
                .load("http://fakhirshaheen.com/cs468/" + fileName)
                .progressBar(pbar)
//                .progress(new ProgressCallback() {
//                    @Override
//                    public void onProgress(long downloaded, long total) {
//                        percent = String.valueOf(downloaded / total);
//                        percentTV.setText("10");
//                    }
//                })
                .write(new File(getFilesDir() + "/" + fileName))
                .setCallback((e, result) -> {
                    Toast.makeText(getApplicationContext(), "file Downloaded " + result, Toast.LENGTH_SHORT).show();
                    getButton.setEnabled(true);

                    displayImage("http://fakhirshaheen.com/cs468/" + fileName);

                });


    }

    public void displayImage(String url) {
        Picasso.get()
                .load(url)
                .into(mainImg);
    }
}