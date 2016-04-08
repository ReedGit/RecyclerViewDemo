package com.reed.recyclerviewdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.reed.recyclerviewdemo.adapters.RecyclerAdapter;
import com.reed.recyclerviewdemo.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private RecyclerAdapter mAdapter;
    private List<Integer> texts = new ArrayList<>();
    private int visibleLastIndex = 0;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        texts = new ArrayList<>();
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        setData();
        mAdapter.notifyDataSetChanged();
        initListener();
    }

    public void setData() {
        for (int j = 0; j < 10; j++) {
            i++;
            texts.add(i);
        }
        mAdapter.setTexts(texts);
    }

    public void initListener() {
        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "click " + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        texts.clear();
                        i = 0;
                        setData();
                        mAdapter.notifyDataSetChanged();
                        mRefresh.setRefreshing(false);
                    }
                },2000);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int itemLastIndex = mAdapter.getItemCount() - 1;//recyclerView最后一项位置
                if (newState  == RecyclerView.SCROLL_STATE_IDLE  && visibleLastIndex == itemLastIndex) {
                    if (i < 100) {
                        mAdapter.setLoading(true);
                        mAdapter.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setData();
                                mAdapter.setLoading(false);
                                mAdapter.notifyDataSetChanged();
                            }
                        }, 2000);
                    } else {
                        mAdapter.setAll(true);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleLastIndex = manager.findLastCompletelyVisibleItemPosition();
            }
        });
    }
}
