package com.example.recycle_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private ArrayList<DataModel> dataSet = null;

    private CustomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.res);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dataSet = new ArrayList<>();

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i = 0 ; i < MyData.drawableArray.length ; i++){

            dataSet.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i],
                    MyData.descriptionArray[i]
            ));
        }

        adapter = new CustomeAdapter(dataSet);
        adapter.setOnItemClickListener(new CustomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get the ViewHolder at the clicked position
                CustomeAdapter.MyViewHolder viewHolder = (CustomeAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                DataModel clickedItem = dataSet.get(position);
                if (viewHolder != null) {
                    viewHolder.showPopup(clickedItem.getDescription());
                }
            }
        });
//        adapter.setOnItemClickListener(new CustomeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // Handle item click here
//                DataModel clickedItem = dataSet.get(position);
//                Toast.makeText(getApplicationContext(), "You clicked " + clickedItem.getDescription(), Toast.LENGTH_SHORT).show();
//            }
//        });
        EditText editTextSearch = findViewById(R.id.editTextText);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        recyclerView.setAdapter(adapter);

    }
}