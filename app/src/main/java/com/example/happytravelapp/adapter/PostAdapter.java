package com.example.happytravelapp.adapter;

import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemFeedBinding;
import com.example.happytravelapp.databinding.ItemHotelBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.ultil.Common;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final static String TAG = "PostAdapter";
    private List<? extends Post> mList;
    ItemLikeClickListener itemLikeClickListener;
    ItemCommentClickListener itemCommentClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_feed,
                parent, false);
        return new ViewHolder(binding);
    }

    public PostAdapter(List<? extends Post> mList,ItemLikeClickListener itemLikeClickListener,ItemCommentClickListener itemCommentClickListener) {
        this.mList = mList;
        this.itemLikeClickListener=itemLikeClickListener;
        this.itemCommentClickListener=itemCommentClickListener;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setPost(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.like.setOnClickListener(v->{
            if(itemLikeClickListener!=null){
                itemLikeClickListener.onClick(mList.get(position));
                if(mList.get(position).stars.containsKey(Common.USER_ID)){
                    holder.binding.like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    Log.d("like", String.valueOf(mList.get(position).getStarCount()));
                }else {
                    holder.binding.like.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });

        holder.binding.comment.setOnClickListener(v->{
            if(itemCommentClickListener!=null){
                itemCommentClickListener.onClick(mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedBinding binding;

        public ViewHolder(@NonNull ItemFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            Post post = mList.get(getPosition());
//            binding.like.setOnClickListener(v->{
//                if(itemLikeClickListener!=null){
//                    itemLikeClickListener.onClick(post);
//                    if(post.stars.containsKey(Common.USER_ID)){
//                        binding.like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                    }else {
//                        binding.like.setImageResource(R.drawable.ic_favorite_black_24dp);
//                    }
//                }
//            });
        }

    }

    public interface ItemLikeClickListener {
        void onClick(Post post);
    }

    public interface ItemCommentClickListener {
        void onClick(Post post);
    }

}
