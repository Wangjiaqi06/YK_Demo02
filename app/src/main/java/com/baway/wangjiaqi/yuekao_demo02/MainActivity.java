package com.baway.wangjiaqi.yuekao_demo02;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyItemClickListener {

    private RecyclerView recyclerView;
    private String urlpath = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=";
    private MyAdapter myAdapter;
    private List<Data.DataBean> list;
    int i = 1;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            myAdapter.notifyDataSetChanged();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        boolean networkConnected = isNetworkConnected(this);

        if (networkConnected == false) {
            Toast.makeText(this, "无网络连接，请连接网络！", Toast.LENGTH_SHORT).show();
        } else {
            //列表显示
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            list = new ArrayList<>();
            myAdapter = new MyAdapter(this, list);
            myAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(myAdapter);
            okhttp(urlpath + i);


            /**
             * recylerview 滑动加载  分页加载数据
             */
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                //用来标记是否正在向最后一个滑动
                boolean isSlidingToLast = false;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    // 当不滚动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();

                        // 判断是否滚动到底部，并且是向右滚动
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            //加载更多功能的代码
                            ++i;
                            okhttp(urlpath+i);
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                    if (dx > 0) {
                        //大于0表示正在向右滚动
                        isSlidingToLast = true;
                    } else {
                        //小于等于0表示停止或向左滚动
                        isSlidingToLast = false;
                    }
                }
            });

        }
    }

    /**
     * 判断网络是否连接
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 网络请求
     */
    void okhttp(String s) {
        Utils.sendOkHttpRequest(s, new Callback() {
            //网络请求失败的方法
            @Override
            public void onFailure(Call call, IOException e) {
            }

            //网络请求成功的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Logger.e(string);
                Gson gson = new Gson();
                Data data = gson.fromJson(string, Data.class);
                list.addAll(data.getData());

                handler.sendEmptyMessage(0);
            }
        });

    }

    @Override
    public void onItemClick(View view, int postion) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "alpha", 0.0F, 1.0F)//
                .setDuration(1000);//
        anim.start();
        Toast.makeText(this, list.get(postion).getIntroduction(), Toast.LENGTH_SHORT).show();
    }

}
