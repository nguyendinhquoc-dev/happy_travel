package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemHighlightHotelBinding;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.model.Hotel;

import java.util.List;

public class HighlightsHotelAdapter extends RecyclerView.Adapter<HighlightsHotelAdapter.ViewHolder> {
    private final static String TAG = "HotelAdapter";
    private List<? extends Hotel> mList;
    private ItemClickListener mListenner;
    private ItemLongClickListener itemLongClickListener;
    private FavouriteClickListener favouriteClickListener;
    int count = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHighlightHotelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_highlight_hotel,
                parent, false);
        return new ViewHolder(binding);
    }

    public HighlightsHotelAdapter(List<? extends Hotel> mList, ItemClickListener mListenner, ItemLongClickListener itemLongClickListener, FavouriteClickListener favouriteClickListener) {
        this.mList = mList;
        this.mListenner = mListenner;
        this.itemLongClickListener = itemLongClickListener;
        this.favouriteClickListener = favouriteClickListener;
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
        Hotel hotel = mList.get(position);
        holder.binding.setHotel(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.llitem.setOnClickListener(v -> {
            if (mListenner != null) {
                mListenner.onClick(mList.get(position));
            }
        });
        holder.binding.llitem.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) {
                itemLongClickListener.onLongClick(mList.get(position));
            }
            return true;
        });

        holder.binding.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favouriteClickListener!=null) {
                    favouriteClickListener.onClick(mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHighlightHotelBinding binding;

        public ViewHolder(@NonNull ItemHighlightHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ItemClickListener {
        void onClick(Hotel hotel);
    }

    public interface ItemLongClickListener {
        boolean onLongClick(Hotel hotel);
    }

    public interface FavouriteClickListener {
        void onClick(Hotel hotel);
    }


}
