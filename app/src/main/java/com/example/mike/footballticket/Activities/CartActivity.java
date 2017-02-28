package com.example.mike.footballticket.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.mike.footballticket.Adapters.MainAdapter;
import com.example.mike.footballticket.Interfaces.DataRemovalInterface;
import com.example.mike.footballticket.Pojo.CartList;
import com.example.mike.footballticket.Interfaces.DataTransferInterface;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.example.mike.footballticket.Pojo.MainMatchObject;
import com.example.mike.footballticket.R;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void removeObject(IMainObject object, int position) {
        mainMatchesItem.remove(object);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
    }
}
