package com.example.pagingdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public class ItemAdapter extends PagedListAdapter<Item,ItemAdapter.viewHolder> {

    private Context context;
    protected ItemAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_rv,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemAdapter.viewHolder holder, int position) {
        Item item = getItem(position);
        if (item != null){
            Glide.with(context).load(item.owner.profile_image).into(holder.imageView);
            holder.textView.setText(item.owner.display_name);
        }else {
            Toast.makeText(context, "Item is null", Toast.LENGTH_SHORT).show();
        }

    }

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull Item oldItem, @NonNull @NotNull Item newItem) {
                    return oldItem.answer_id == newItem.answer_id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull @NotNull Item oldItem, @NonNull @NotNull Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
