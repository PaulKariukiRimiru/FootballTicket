package com.example.mike.footballticket.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.mike.footballticket.Delegates.CartDelegate;
import com.example.mike.footballticket.Delegates.MatchesDelegate;
import com.example.mike.footballticket.Delegates.TicketsDelegate;
import com.example.mike.footballticket.Interfaces.DataRemovalInterface;
import com.example.mike.footballticket.Interfaces.DataTransferInterface;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

/**
 * Created by Mike on 1/28/2017.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<IMainObject>> manager;
    private List<IMainObject> mainObjects;

    public MainAdapter(Context context, List<IMainObject> mainObjects, DataTransferInterface dataTransferInterface, DataRemovalInterface dataRemovalInterface){
        this.mainObjects = mainObjects;
        manager = new AdapterDelegatesManager<>();
        if (dataRemovalInterface != null)
        manager.addDelegate(new CartDelegate(context, dataRemovalInterface));
        manager.addDelegate(new TicketsDelegate(context,dataRemovalInterface));
        if (dataTransferInterface != null)
        manager.addDelegate(new MatchesDelegate(context,dataTransferInterface));
    }

    @Override
    public int getItemViewType(int position) {
        return manager.getItemViewType(mainObjects,position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return manager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        manager.onBindViewHolder(mainObjects,position,holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        manager.onBindViewHolder(mainObjects,position,holder,payloads);
    }
    @Override
    public int getItemCount() {
        return mainObjects.size();
    }
}
