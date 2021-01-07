package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemInboxBinding;
import com.example.happytravelapp.databinding.ItemMessageBinding;
import com.example.happytravelapp.model.Inbox;
import com.example.happytravelapp.viewmodel.HotelViewModel;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    private final static String TAG = "InboxAdapter";
    private List<? extends Inbox> mList;
    private ItemClickListener mListenner;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInboxBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_inbox,
                parent, false);
        return new ViewHolder(binding);
    }

    public InboxAdapter(List<? extends Inbox> mList,ItemClickListener mListenner) {
        this.mList = mList;
        this.mListenner=mListenner;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inbox inbox = mList.get(position);
        holder.binding.setInbox(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.llitem.setOnClickListener(v -> {
            if (mListenner != null) {
                mListenner.onClick(mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemInboxBinding binding;

        public ViewHolder(@NonNull ItemInboxBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ItemClickListener {
        void onClick(Inbox inbox);
    }

}
