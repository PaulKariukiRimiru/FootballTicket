package com.example.mike.footballticket.Delegates;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.example.mike.footballticket.Activities.QRgeneratorFragment;
import com.example.mike.footballticket.Interfaces.DataRemovalInterface;
import com.example.mike.footballticket.Pojo.AccountObject;
import com.example.mike.footballticket.Pojo.IMainObject;
import com.example.mike.footballticket.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Mike on 3/21/2017.
 */

public class AccountDelegate extends AdapterDelegate<List<IMainObject>> {

    private final Context context;
    private final LayoutInflater inflater;
    private final DataRemovalInterface removalInterface;

    public AccountDelegate(Context context, DataRemovalInterface removalInterface){
        this.context = context;
        this.removalInterface = removalInterface;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof AccountObject;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AccountDelegateViewHolder(inflater.inflate(R.layout.account_item,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AccountObject accountObject  = (AccountObject) items.get(position);
        final AccountDelegateViewHolder holder1 = (AccountDelegateViewHolder) holder;

        holder1.homeName.setText(accountObject.getHomeName());
        holder1.awayName.setText(accountObject.getAwayName());
        holder1.matchTime.setText(accountObject.getTime());

        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removalInterface.removeObject(accountObject, holder1.getAdapterPosition());
            }
        });

        holder1.rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(context, QRgeneratorFragment.class);
                intent.putExtra("matchInfo", accountObject.getHomeName()+" "+"vs"+" "+accountObject.getAwayName());
                context.startActivity(intent);
            }
        });
    }

    private class AccountDelegateViewHolder extends RecyclerView.ViewHolder {
        ImageView homeLogo, awayLogo;
        AppCompatTextView homeName, awayName, matchTime;
        AppCompatButton delete;
        RippleView rippleView;
        public AccountDelegateViewHolder(View inflate) {
            super(inflate);
            homeLogo = (ImageView) itemView.findViewById(R.id.imghome);
            awayLogo = (ImageView) itemView.findViewById(R.id.imgaway);

            homeName = (AppCompatTextView) itemView.findViewById(R.id.tvHomename);
            awayName = (AppCompatTextView) itemView.findViewById(R.id.tvAwayname);
            matchTime = (AppCompatTextView) itemView.findViewById(R.id.tvMatchtime);

            delete = (AppCompatButton) itemView.findViewById(R.id.btnDelete);
            rippleView = (RippleView) itemView.findViewById(R.id.ripAccountItem);
        }
    }
}
