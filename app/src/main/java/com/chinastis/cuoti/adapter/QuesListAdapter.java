package com.chinastis.cuoti.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinastis.cuoti.R;
import com.chinastis.cuoti.bean.Question;
import com.chinastis.cuoti.util.Constant;
import com.chinastis.cuoti.view.QuesActivity;
import com.chinastis.cuoti.view.QuesListActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianglong on 2018/12/6.
 */

public class QuesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Question> data;
    private Context mContext;

    private boolean isCheckBoxShow;

    List<String> checkedItem;

    public QuesListAdapter(List<Question> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
        checkedItem = new ArrayList<>();
    }


    public boolean isCheckBoxShow() {
        return isCheckBoxShow;
    }

    public void setCheckBoxShow(boolean checkBoxShow) {
        isCheckBoxShow = checkBoxShow;
        if (checkBoxShow) {
            checkedItem = new ArrayList<>();
            ((QuesListActivity)mContext).refreshSelectedNum(checkedItem.size());
        }
        this.notifyDataSetChanged();
    }

    public List<String> getCheckedItem() {
        return checkedItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_ques_list, parent, false);
        QuesViewHolder vh = new QuesViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QuesViewHolder vh = (QuesViewHolder) holder;
        vh.understand.setText(data.get(position).getUnderstand());
        vh.review.setText("复习" + data.get(position).getReviewTime() + "次");

        File imageFile = new File(Constant.PATH +
                data.get(position).getQues_id() + File.separator + "ques");
        vh.quesImage.setImageBitmap(BitmapFactory.
                decodeFile(imageFile.listFiles()[0].getAbsolutePath()));

        if (isCheckBoxShow) {
            vh.checkBox.setVisibility(View.VISIBLE);
            if (isBeenChecked(data.get(position).getQues_id())) {
                vh.checkBox.setChecked(true);
            } else {
                vh.checkBox.setChecked(false);
            }

        } else {
            vh.checkBox.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class QuesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    ,CompoundButton.OnCheckedChangeListener{
        private TextView understand;
        private TextView review;
        private ImageView quesImage;
        private CheckBox checkBox;

        QuesViewHolder(View itemView) {
            super(itemView);
            understand = (TextView) itemView.findViewById(R.id.understand_ques_item);
            review = (TextView) itemView.findViewById(R.id.review_ques_item);
            quesImage = (ImageView) itemView.findViewById(R.id.image_ques_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_ques_item);
            checkBox.setOnCheckedChangeListener(this);
            quesImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, QuesActivity.class);
            intent.putExtra(Constant.TITLE,"错题详情");
            intent.putExtra("quesId",data.get(getLayoutPosition()).getQues_id());

            mContext.startActivity(intent);

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                checkedItem.add(data.get(getLayoutPosition()).getQues_id());
            } else {
                checkedItem.remove(data.get(getLayoutPosition()).getQues_id());
            }
            ((QuesListActivity)mContext).refreshSelectedNum(checkedItem.size());
            Log.e("MENG","checked:"+checkedItem);
        }
    }

    private boolean isBeenChecked(String quesId){
        boolean isChecked = false;
        for (String s:checkedItem) {
            if (s.equals(quesId)) {
                isChecked = true;
                break;
            }
        }
        return isChecked;
    }
}
