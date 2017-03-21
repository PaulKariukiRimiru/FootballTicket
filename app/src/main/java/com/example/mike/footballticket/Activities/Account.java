package com.example.mike.footballticket.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mike.footballticket.Adapters.MainAdapter;
import com.example.mike.footballticket.Database.DatabaseHelperClass;
import com.example.mike.footballticket.Interfaces.DataRemovalInterface;
import com.example.mike.footballticket.Pojo.AccountObject;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.example.mike.footballticket.R;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity implements DataRemovalInterface {
    List<IMainObject> listObjects;
    private MainAdapter adapter;
    DatabaseHelperClass databaseHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        RecyclerView accountTickets = (RecyclerView) findViewById(R.id.viewAccount);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progAccount);
        listObjects = new ArrayList<>();
        databaseHelperClass = new DatabaseHelperClass(this);
        for (AccountObject accountObject : databaseHelperClass.getAllTickets()) {
            Log.d("RETRIEVEING DATA",accountObject.getMatchId());
            IMainObject iMainObject = accountObject;
            listObjects.add(iMainObject);
        }
        adapter = new MainAdapter(this,listObjects,null,this);
        accountTickets.setAdapter(adapter);
        accountTickets.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void removeObject(IMainObject object, int position) {
        listObjects.remove(object);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,listObjects.size());

        AccountObject object1 = (AccountObject) object;
        databaseHelperClass.deleteContact(object1.getMatchId());
    }
}
