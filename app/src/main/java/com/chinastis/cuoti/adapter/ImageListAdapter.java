package com.chinastis.cuoti.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chinastis.cuoti.R;

import java.io.File;

/**
 * Created by xianglong on 2018/12/6.
 */

public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private File[] data;

    private AddImageListener addImageListener;
    private DeleteImageListener deleteImageListener;

    public ImageListAdapter(Context mContext, File[] data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setAddListener(AddImageListener listener) {
        this.addImageListener = listener;
    }

    public void setDeleteImageListener(DeleteImageListener deleteImageListener) {
        this.deleteImageListener = deleteImageListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        ImageViewHolder vh = new ImageViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder viewHolder = (ImageViewHolder) holder;

        if (position == data.length) {
            viewHolder.imageItem.setImageBitmap(BitmapFactory.
                            decodeResource(mContext.getResources(),R.drawable.icon_add_ques));
            viewHolder.deleteItem.setVisibility(View.GONE);

        } else {
            viewHolder.imageItem
                    .setImageBitmap(BitmapFactory.decodeFile(data[position].getAbsolutePath()));
            viewHolder.deleteItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 :data.length+1;
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageItem;
        ImageView deleteItem;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView) itemView.findViewById(R.id.image_item);
            deleteItem = (ImageView) itemView.findViewById(R.id.delete_image);
            deleteItem.setOnClickListener(this);
            imageItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.delete_image) {
                if (deleteImageListener!=null) {
                    deleteImageListener.deleteItemClicked(getLayoutPosition());
                }
            } else {
                if (getLayoutPosition() == data.length)
                if (addImageListener!= null) {
                    addImageListener.addItemClicked();
                }
            }
        }
    }

    public interface AddImageListener {
        void addItemClicked();
    }

    public interface DeleteImageListener {
        void deleteItemClicked(int position);
    }
}
