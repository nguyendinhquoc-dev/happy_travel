package com.example.happytravelapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ItemRoomtypeBinding;
import com.example.happytravelapp.model.RoomType;
import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.ViewHolder> {
    private final static String TAG = "RoomtypeAdapter";
    private List<? extends RoomType> mList ;
    private ItemClickListener mListenner;
    private ItemLongClickListener itemLongClickListener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoomtypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_roomtype,
                parent,false);
        return new ViewHolder(binding);
    }

    public RoomTypeAdapter(List<? extends RoomType> mList, ItemClickListener mListenner, ItemLongClickListener itemLongClickListener) {
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
        RoomType roomType=mList.get(position);
        holder.binding.setRoomtype(mList.get(position));
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
        private ItemRoomtypeBinding binding;
        public ViewHolder(@NonNull ItemRoomtypeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        }

    public interface ItemClickListener {
        void onClick(RoomType roomType);
    }
    public interface ItemLongClickListener {
        boolean onLongClick(RoomType roomType);
    }


}
