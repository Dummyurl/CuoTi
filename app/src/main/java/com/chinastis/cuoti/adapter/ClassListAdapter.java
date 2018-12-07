package com.chinastis.cuoti.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.QuesClass;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.view.QuesListActivity;

import java.util.List;

/**
 * Created by xianglong on 2018/12/5.
 */

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {

    private Context mContext;
    private List<QuesClass> classes;

    public ClassListAdapter(Context mContext, List<QuesClass> classes) {
        this.mContext = mContext;
        this.classes = classes;
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_class, parent,false);

        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        holder.classNameText.setText(classes.get(position).getClassName());
        holder.classCountText.setText(classes.get(position).getQesNum());
        holder.classIcon.setImageBitmap(classes.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return classes == null ? 0 : classes.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView classNameText;
        TextView classCountText;
        ImageView classIcon;

        ClassViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            classNameText = (TextView) itemView.findViewById(R.id.class_item_name);
            classCountText = (TextView) itemView.findViewById(R.id.class_item_count);
            classIcon = (ImageView) itemView.findViewById(R.id.class_item_icon);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, QuesListActivity.class);
            intent.putExtra(Constant.TITLE,
                    classes.get(getLayoutPosition()).getClassName());
            mContext.startActivity(intent);
        }
    }
}
