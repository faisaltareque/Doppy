package com.skyrentyle.faisal.doppy.Recipient;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.skyrentyle.faisal.doppy.DatBaseHelper;
import com.skyrentyle.faisal.doppy.R;

import java.util.ArrayList;

public class ShowDatabaseElementActivity extends AppCompatActivity {
    private ListView listView;
    private DatBaseHelper datBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database_elements);


        ListView listView = (ListView) findViewById(R.id.Showdatbase);
        datBaseHelper= new DatBaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = datBaseHelper.showData();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                String x=data.getString(1);
                String y=data.getString(2);
                String w=data.getString(5);
                String v=data.getString(3);
                String pass=data.getString(4);
                String z="Name: "+x+ "  "+y+"\n Email:  "+v+" \nPhone No: "+w+" \n Password: "+pass;
                theList.add(z);



                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
            }
        }


    }
}
