package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemCommentBinding;
import com.example.happytravelapp.databinding.ItemFeedBinding;
import com.example.happytravelapp.model.Comment;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.ultil.Common;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final static String TAG = "PostAdapter";
    private List<? extends Comment> mList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comment,
                parent, false);
        return new ViewHolder(binding);
    }

    public CommentAdapter(List<? extends Comment> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setComment(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding binding;

        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
