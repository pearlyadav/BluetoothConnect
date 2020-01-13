package com.cu.bluetoothconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Grouping extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        Saved saved = Saved.getInstance();

//        CustomAdaptor adapter = new CustomAdaptor(Grouping.this, saved.print());
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, saved.print());
        String[] s = {saved.print()};
        ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.activity_list_view,R.id.listText,s);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }
}
