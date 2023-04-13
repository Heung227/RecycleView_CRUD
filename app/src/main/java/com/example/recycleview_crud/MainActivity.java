package com.example.recycleview_crud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview_crud.model.Cat;
import com.example.recycleview_crud.model.CatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CatAdapter.CatItemListener, SearchView.OnQueryTextListener{
    Spinner sp;
    RecyclerView recyclerView;
    CatAdapter adapter;
    EditText eName, eDescribe, ePrice;
    Button btAdd, btUpdate;
    SearchView searchView;
    int pcurr;
    int[]imgs={R.drawable.meo1, R.drawable.meo2, R.drawable.meo3,
            R.drawable.meo4, R.drawable.meo5, R.drawable.meo6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        adapter=new CatAdapter(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        searchView.setOnQueryTextListener(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cat cat=new Cat();
                String i=sp.getSelectedItem().toString(); //4:08
                String name=eName.getText().toString();
                String des=eDescribe.getText().toString();
                String p=ePrice.getText().toString();
                int img=R.drawable.remove;
                double price=0;
                try {
                    img=imgs[Integer.parseInt(i)];
                    price=Double.parseDouble(p);
                    cat.setImg(img);
                    cat.setName(name);
                    cat.setDescribe(des);
                    cat.setPrice(price);
                    adapter.add(cat);
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cat cat=new Cat();
                String i=sp.getSelectedItem().toString(); //4:08
                String name=eName.getText().toString();
                String des=eDescribe.getText().toString();
                String p=ePrice.getText().toString();
                int img=R.drawable.remove;
                double price=0;
                try {
                    img=imgs[Integer.parseInt(i)];
                    price=Double.parseDouble(p);
                    cat.setImg(img);
                    cat.setName(name);
                    cat.setDescribe(des);
                    cat.setPrice(price);
                    adapter.update(pcurr, cat);
                    btAdd.setEnabled(true);
                    btUpdate.setEnabled(false);
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initview() {
        sp=findViewById(R.id.img);
        SpinnerAdapter adapter=new com.example.recycleview_crud.model.SpinnerAdapter(this);
        sp.setAdapter(adapter);
        recyclerView=findViewById(R.id.recycleView);
        eName=findViewById(R.id.name);
        eDescribe=findViewById(R.id.describe);
        ePrice=findViewById(R.id.price);
        btAdd=findViewById(R.id.btAdd);
        btUpdate=findViewById(R.id.btUpdate);
        btUpdate.setEnabled(false);
        searchView=findViewById(R.id.search);
    }

    @Override
    public void onItemClick(View view, int position) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        pcurr=position;
        Cat cat=adapter.getItem(position);
        int img=cat.getImg();
        int p=0;
        for (int i = 0; i < imgs.length; i++) {
            if (img == imgs[i]) {
                p = i;
                break;
            }
        }
            sp.setSelection(p);
            eName.setText(cat.getName());
            eDescribe.setText(cat.getDescribe());
            ePrice.setText(cat.getPrice()+"");

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);
        return false;
    }

    private void filter(String s) {
        List<Cat> filterlist=new ArrayList<>();
        for (Cat i:adapter.getBackup()){
            if(i.getName().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(i);
            }
        }
        if (filterlist.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.filterList(filterlist);
        }
    }
}
