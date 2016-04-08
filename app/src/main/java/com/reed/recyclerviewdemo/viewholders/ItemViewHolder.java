package com.reed.recyclerviewdemo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.reed.recyclerviewdemo.R;

/**
 * recyclerView的item
 * Created by 伟 on 2016/2/23.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
    }
}
