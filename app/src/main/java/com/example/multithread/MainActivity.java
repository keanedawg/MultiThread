package com.example.multithread;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;





public class MainActivity extends AppCompatActivity {

    Button create;
    Button load;
    Button clear;

    Context myContext = this;
    ArrayAdapter<String> adapter;



    class Read extends AsyncTask<Void, Integer, Void> {

        ArrayAdapter<String> adpt;
        File file;
        Scanner scan;

        Read(ArrayAdapter<String> a, File f) {
            adpt = a;
            file = f;
            try {
                scan = new Scanner(file);
            }
            catch (IOException e) {
                Toast.makeText(MainActivity.this, "Error reading file!", Toast.LENGTH_SHORT).show();
                // I should do something... just not sure what.
            }
        }

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected Void doInBackground(Void... params) {
            Integer i = 0;
         //   Toast.makeText(MainActivity.this, "Error reading file!", Toast.LENGTH_SHORT).show();
            while (scan.hasNext()) {
                String j = scan.next();
                adpt.add(j);
                i++;
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            adpt.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void result) {
            scan.close();
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Context hamburger = this;

        create = (Button) findViewById(R.id.create);
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream FIO = openFileOutput("numbers.txt", MODE_PRIVATE);
                    for(int i = 1; i < 11; i++ ) {
                        String myVal = Integer.toString(i) + "\n";
                        FIO.write(myVal.getBytes());
                        Thread.sleep(250);
                    }
                   FIO.close();
                }
                catch (IOException i) {
                    Toast.makeText(MainActivity.this, "Error reading file!", Toast.LENGTH_SHORT).show();
                }
                catch (InterruptedException e) {
                    Toast.makeText(MainActivity.this, "Interruption", Toast.LENGTH_SHORT).show();
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getFilesDir(), "numbers.txt");
                try {
                    Scanner scan = new Scanner(file);
                    //TextView txt = (TextView) findViewById(R.id.textview);
                   // txt.setText(stuff);

                    List myValues = new  ArrayList<String>();

                    adapter = new ArrayAdapter<String>(
                            hamburger,
                            R.layout.item,
                            myValues
                    );

                    ListView lv = (ListView) findViewById(R.id.lv);
                    lv.setAdapter(adapter);


                    Read r = new Read(adapter, file);
                    r.execute();


/*
                    for (int i = 0; i < 10; i++) {
                        if(scan.hasNext()) {
                            Thread.sleep(250);
                            adapter.add(scan.next());
                            //adapter.notifyDataSetChanged(); // thought experiment
                        }
                    }*/


                }
                catch (IOException e) {

                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
            }
        });

    }
}
