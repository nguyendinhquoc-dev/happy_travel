package com.example.happytravelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.databinding.ItemHotelbookingBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.HotelBooking;
import com.example.happytravelapp.model.RoomType;
import com.example.happytravelapp.viewmodel.HotelBookingViewModel;

import java.util.List;

public class HotelBookingAdapter extends RecyclerView.Adapter<HotelBookingAdapter.ViewHolder> {
    private final static String TAG = "HotelBookingAdapter";
    private List<? extends HotelBooking> mList ;
    private ItemClickListener mListenner;
    private ItemLongClickListener itemLongClickListener;
    HotelBookingViewModel hotelBookingViewModel;
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHotelbookingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hotelbooking,
                parent,false);
        return new ViewHolder(binding);
    }

    public HotelBookingAdapter(List<? extends HotelBooking> mList, ItemClickListener mListenner, ItemLongClickListener itemLongClickListener) {
        this.mList=mList;
        this.mListenner=mListenner;
        this.itemLongClickListener=itemLongClickListener;
        notifyDataSetChanged();
    }

    public void setmListenner(ItemClickListener mListenner) {
        this.mListenner = mListenner;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelBooking hotelBooking=mList.get(position);
        holder.binding.setHotelbooking(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.llitem.setOnClickListener(v->{
            if(mListenner!=null){
                mListenner.onClick(mList.get(position));
            }
        });
        holder.binding.llitem.setOnLongClickListener(v->{
            if(itemLongClickListener!=null){
                itemLongClickListener.onLongClick(mList.get(position));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHotelbookingBinding binding;
        public ViewHolder(@NonNull ItemHotelbookingBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        }

    public interface ItemClickListener {
        void onClick(HotelBooking hotelBooking);
    }
    public interface ItemLongClickListener {
        boolean onLongClick(HotelBooking hotelBooking);
    }


}
