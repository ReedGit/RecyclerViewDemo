package com.reed.recyclerviewdemo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reed.recyclerviewdemo.R;
import com.reed.recyclerviewdemo.adapters.RecyclerAdapter;

/**
 * 加载中布局
 * Created by 伟 on 2016/2/23.
 */
public class FootViewHolder extends RecyclerView.ViewHolder {

    public TextView footTextView;
    public ProgressBar footPB;

    public FootViewHolder(View itemView) {
        super(itemView);
        footTextView = (TextView) itemView.findViewById(R.id.tv_foot);
        footPB = (ProgressBar) itemView.findViewById(R.id.pb_foot);
    }

    public void bindData(int status) {
        switch (status) {
            case RecyclerAdapter.NO_DATA:
                footPB.setVisibility(View.GONE);
                footTextView.setVisibility(View.VISIBLE);
                footTextView.setText("没有更多数据");
                break;
            case RecyclerAdapter.LOADING_DATA:
                footPB.setVisibility(View.VISIBLE);
                footTextView.setVisibility(View.VISIBLE);
                footTextView.setText("正在加载中......");
                break;
            case RecyclerAdapter.FINISH_DATA:
                footPB.setVisibility(View.GONE);
                footTextView.setVisibility(View.VISIBLE);
                footTextView.setText("上拉加载更多");
                break;

        }
    }
}
