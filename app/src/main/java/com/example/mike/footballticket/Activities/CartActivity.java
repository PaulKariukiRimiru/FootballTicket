package com.example.mike.footballticket.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mike.footballticket.Adapters.MainAdapter;
import com.example.mike.footballticket.Database.DatabaseHelperClass;
import com.example.mike.footballticket.Interfaces.DataRemovalInterface;
import com.example.mike.footballticket.Pojo.CartList;
import com.example.mike.footballticket.Interfaces.DataTransferInterface;
import com.example.mike.footballticket.Pojo.CartObject;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.example.mike.footballticket.Pojo.MainMatchObject;
import com.example.mike.footballticket.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements DataRemovalInterface{

    private List<IMainObject> mainMatchesItem;
    MainAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.viewCart);
        AppCompatButton checkout = (AppCompatButton) findViewById(R.id.btnCheckoutcart);

        CartList cartList = (CartList) getIntent().getExtras().getSerializable("cartList");
        mainMatchesItem = null;
        if (cartList != null) {
            mainMatchesItem = cartList.getCartobjects();
        }
        adapter = new MainAdapter(this,mainMatchesItem,null,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        final DatabaseHelperClass helperClass = new DatabaseHelperClass(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (IMainObject iMainObject : mainMatchesItem){
                    CartObject cartObject = (CartObject) iMainObject;
                    MainMatchObject matchObject = new MainMatchObject();
                    if (cartObject != null){
                            matchObject.setHomeName(cartObject.getHomeTeam());
                            matchObject.setAwayName(cartObject.getAwayTeam());
                            matchObject.setLocation(cartObject.getLocation());
                            matchObject.setMatchId(cartObject.getMatchId());
                            matchObject.setTime(cartObject.getTime());
                            matchObject.setTicketPrice(cartObject.getPrice());
                            matchObject.setHomeLogo(cartObject.getHomeLogo());
                            matchObject.setAwayLogo(cartObject.getAwaylogo());
                    }
                    helperClass.insertTicket(matchObject);
                }
                Snackbar.make(view, "Transaction complete", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void removeObject(IMainObject object, int position) {
        mainMatchesItem.remove(object);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,mainMatchesItem.size());
    }
}
