package com.o058team.hajjuserapp.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.o058team.hajjuserapp.R;
import com.o058team.hajjuserapp.models.ServiceResponseItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class ServicesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<ServiceResponseItem> serviceItems;
    Context mContext;
    OnServiceClickListener serviceCallback;
    LayoutInflater layoutInflater;


    public interface OnServiceClickListener {
        public void onServiceItemClicked(ServiceResponseItem item);
        public void playSound();
    }


    public ServicesListAdapter(ArrayList<ServiceResponseItem> serviceItems, Context mContext ,OnServiceClickListener serviceCallback ) {
        this.serviceItems = serviceItems;
        this.mContext = mContext;
        this.serviceCallback= serviceCallback;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.viewholder_servicetype,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ServiceViewHolder serviceViewHolder =  (ServiceViewHolder) holder;
        serviceViewHolder.bindView(serviceItems.get(position));
    }

    @Override
    public int getItemCount() {
        return serviceItems.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvServiceName)
        ImageView tvServiceName;


        public ServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(ServiceResponseItem serviceResponseItem){
//            tvServiceName.setText(serviceResponseItem.getServiceName());

            tvServiceName.setImageDrawable(ContextCompat.getDrawable(mContext,serviceResponseItem.getUrl()));

        }

        @OnClick(R.id.cvContainer)
        public void onContainerClicked() {
            serviceCallback.onServiceItemClicked(serviceItems.get(getAdapterPosition()));
        }

        @OnClick(R.id.ivSound)
        public void playSound(){

        }




    }
}
