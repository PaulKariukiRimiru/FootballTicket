package com.example.mike.footballticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mike.footballticket.Activities.AboutusActivity;
import com.example.mike.footballticket.Activities.Account;
import com.example.mike.footballticket.Activities.CartActivity;
import com.example.mike.footballticket.Activities.FavoritesActivity;
import com.example.mike.footballticket.Activities.LoginActivity;
import com.example.mike.footballticket.Activities.QRscanner;
import com.example.mike.footballticket.Activities.SettingsActivity;
import com.example.mike.footballticket.Activities.WhatshotActivity;
import com.example.mike.footballticket.Adapters.MainAdapter;
import com.example.mike.footballticket.Fragments.QRgeneratorFragment;
import com.example.mike.footballticket.Pojo.CartList;
import com.example.mike.footballticket.Pojo.CartObject;
import com.example.mike.footballticket.Interfaces.DataTransferInterface;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.example.mike.footballticket.Pojo.MainMatchObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DataTransferInterface {
    List<CartObject> cartObjectList = new ArrayList<CartObject>();
    List<MainMatchObject> mainMatchObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<IMainObject> objects = new ArrayList<>();
        for (int i=1; i <=20; i++){
            MainMatchObject matchObject = new MainMatchObject();
            matchObject.setHomeName("Gor Mahia");
            matchObject.setAwayName("Thika Untd");
            matchObject.setTime("Sat,21/1/2017");
            matchObject.setLocation("Nyayo stadium");
            matchObject.setTicketPrice(100);
            matchObject.setHomeLogo("not uploaded");
            matchObject.setAwayLogo("not uploaded");
            matchObject.setMatchId("001");

            objects.add(matchObject);
        }
        MainAdapter mainAdapter = new MainAdapter(this,objects,this,null);
        RecyclerView matchesView = (RecyclerView) findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMatchObjectsToCartObjects();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getMatchObjectsToCartObjects(){
        List<IMainObject> cartObjectList = new ArrayList<>();
        CartList cartList = new CartList();
        if (mainMatchObjects != null){
            for (MainMatchObject object:mainMatchObjects){
                CartObject cartObject = new CartObject();
                cartObject.setHomeTeam(object.getHomeName());
                cartObject.setAwayTeam(object.getAwayName());
                cartObject.setLocation(object.getLocation());
                cartObject.setMatchId(object.getMatchId());
                cartObject.setTime(object.getTime());
                cartObject.setPrice(object.getTicketPrice());
                cartObject.setHomeLogo(object.getHomeLogo());
                cartObject.setAwaylogo(object.getAwayLogo());
                cartObject.setNumberOfTickets(1);
                cartObjectList.add(cartObject);
            }
        }
        cartList.setCartobjects(cartObjectList);
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.putExtra("cartList",cartList);
        startActivity(intent);
    }

    public void addMatchObjects(IMainObject object){
        MainMatchObject object1 = (MainMatchObject) object;
        mainMatchObjects.add(object1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_account) {
            // Handle the camera action
            intent = new Intent(this, Account.class);
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hot) {
            intent = new Intent(this, WhatshotActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            intent = new Intent(this, QRgeneratorFragment.class);
            startActivity(intent);
        } else if (id == R.id.nav_favorites) {
            intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_about) {
            intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_scan) {
            intent = new Intent(this, QRscanner.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setValues(IMainObject object) {
        addMatchObjects(object);
    }
}
