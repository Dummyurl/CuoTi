package com.chinastis.cuoti.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chinastis.cuoti.R;

import java.util.List;

/**
 * Created by xianglong on 2018/12/6.
 */

public class ButtonSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;
    private Context mContext;
    private String selectedItem;
    private Button lastSelectButton;

    public ButtonSelectAdapter(List<String> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_button,parent,false);
        ButtonViewHolder vh = new ButtonViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ButtonViewHolder vh = (ButtonViewHolder) holder;
        vh.button.setText(data.get(position));

        if ( selectedItem!=null && selectedItem.equals(data.get(position))) {
            vh.button.setSelected(true);
            vh.button.setTextColor(mContext.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    private class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button button;
        ButtonViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.button_item);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (lastSelectButton!= null) {
                lastSelectButton.setSelected(false);
                lastSelectButton.setTextColor(mContext.getResources().getColor(R.color.deep_gray));
            }

            Button button = (Button) v;
            button.setSelected(true);
            button.setTextColor(mContext.getResources().getColor(R.color.white));
            selectedItem = (String) button.getText();
            lastSelectButton = button;

        }
    }
}
