package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.databinding.ItemMessageBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.Inbox;
import com.example.happytravelapp.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private final static String TAG = "MessageAdapter";
    private List<? extends Message> mList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_message,
                parent, false);
        return new ViewHolder(binding);
    }

    public MessageAdapter(List<? extends Message> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = mList.get(position);
        holder.binding.setMessage(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMessageBinding binding;

        public ViewHolder(@NonNull ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
