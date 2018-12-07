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

public class QuesContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private File[] data;

    public QuesContentAdapter(Context mContext, File[] data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_ques_content,
                parent,false);

        QuesViewHolder vh = new QuesViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QuesViewHolder viewHolder = (QuesViewHolder) holder;
        viewHolder.image.setImageBitmap(BitmapFactory.
                decodeFile(data[position].getAbsolutePath()));

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.length;
    }

    class QuesViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public QuesViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image_ques_content);
        }
    }
}
