package com.example.multithread;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
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

                }
                catch (InterruptedException e) {

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
                    adapter.setNotifyOnChange(true);



                    for (int i = 0; i < 10; i++) {
                        if(scan.hasNext()) {
                            Thread.sleep(250);
                            adapter.add(scan.next());
                            //adapter.notifyDataSetChanged(); // thought experiment
                        }
                    }


                }
                catch (IOException e) {

                }
                catch (InterruptedException e) {

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
