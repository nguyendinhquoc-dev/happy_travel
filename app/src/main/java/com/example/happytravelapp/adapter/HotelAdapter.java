package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> implements Filterable {
    private final static String TAG = "HotelAdapter";
    private List<? extends Hotel> mList;
    private ItemClickListener mListenner;
    List<String> mStringFilterList;
    ValueFilter valueFilter;
    private ItemLongClickListener itemLongClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHotelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hotel,
                parent, false);
        return new ViewHolder(binding);
    }

    public HotelAdapter(List<? extends Hotel> mList, ItemClickListener mListenner, ItemLongClickListener itemLongClickListener) {
        this.mList = mList;
        this.mListenner = mListenner;
        this.itemLongClickListener = itemLongClickListener;
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

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mList = (List<? extends Hotel>) results.values;
            notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHotelBinding binding;

        public ViewHolder(@NonNull ItemHotelBinding binding) {
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

}
