package com.reed.recyclerviewdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reed.recyclerviewdemo.R;
import com.reed.recyclerviewdemo.viewholders.FootViewHolder;
import com.reed.recyclerviewdemo.viewholders.ItemViewHolder;

import java.util.List;

/**
 * recyclerView adapter
 * Created by 伟 on 2016/2/23.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {

    public static final int NORMAL_TYPE = 0;
    public static final int FOOT_TYPE = 1;
    public static final int NO_DATA = 0;
    public static final int LOADING_DATA = 1;
    public static final int FINISH_DATA = 2;

    private boolean isAll = false;//当前数据是否全部加载完毕
    private boolean isLoading = false;//当前状态是否是加载中
    private List<Integer> texts;

    public void setTexts(List<Integer> texts) {
        this.texts = texts;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case NORMAL_TYPE:
                return new ItemViewHolder(inflater.inflate(R.layout.recycler_item, parent, false));
            case FOOT_TYPE:
                return new FootViewHolder(inflater.inflate(R.layout.more_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (texts == null || (isAll && position == texts.size())) {
            ((FootViewHolder) holder).bindData(NO_DATA);
            return;
        }
        if (isLoading && position == texts.size()) {
            ((FootViewHolder) holder).bindData(LOADING_DATA);
            return;
        }
        if (position == texts.size()) {
            ((FootViewHolder) holder).bindData(FINISH_DATA);
            return;
        }
        ((ItemViewHolder) holder).textView.setText(String.format("%s", texts.get(position)));
        if (onItemClickListener != null) {
            ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(((ItemViewHolder) holder).itemView, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return texts == null ? 1 : texts.size() + 1;//多一个底部布局
    }

    @Override
    public int getItemViewType(int position) {
        if (position == texts.size()) {
            return FOOT_TYPE;//说明该item是底部
        }
        return NORMAL_TYPE;
    }
}
