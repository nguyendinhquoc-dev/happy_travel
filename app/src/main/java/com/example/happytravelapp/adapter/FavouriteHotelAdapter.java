package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemFavouriteHotelBinding;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.model.FavouriteHotel;
import com.example.happytravelapp.model.Hotel;

import java.util.List;

public class FavouriteHotelAdapter extends RecyclerView.Adapter<FavouriteHotelAdapter.ViewHolder> {
    private final static String TAG = "FavouriteHotelAdapter";
    private List<? extends FavouriteHotel> mList ;
    private ItemClickListener mListenner;
    private ItemLongClickListener itemLongClickListener;
    private RemoveClickListener removeClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavouriteHotelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_favourite_hotel,
                parent,false);
        return new ViewHolder(binding);
    }

    public FavouriteHotelAdapter(List<? extends FavouriteHotel> mList, ItemClickListener mListenner, ItemLongClickListener itemLongClickListener, RemoveClickListener removeClickListener) {
        this.mList=mList;
        this.mListenner=mListenner;
        this.itemLongClickListener=itemLongClickListener;
        this.removeClickListener=removeClickListener;
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
        FavouriteHotel favouriteHotel =mList.get(position);
        holder.binding.setFavoutite(mList.get(position));
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

        holder.binding.favourite.setOnClickListener(v->{
            if(removeClickListener!=null){
                removeClickListener.onClick(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFavouriteHotelBinding binding;
        public ViewHolder(@NonNull ItemFavouriteHotelBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        }

    public interface ItemClickListener {
        void onClick(FavouriteHotel favouriteHotel);
    }
    public interface ItemLongClickListener {
        boolean onLongClick(FavouriteHotel favouriteHotel);
    }

    public interface RemoveClickListener {
        void onClick(FavouriteHotel favouriteHotel);
    }


}
