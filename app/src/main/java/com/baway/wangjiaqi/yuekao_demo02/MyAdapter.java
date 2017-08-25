package com.baway.wangjiaqi.yuekao_demo02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by admin on 2017/8/25.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<Data.DataBean> list;
    private MyItemClickListener onItem;
    public MyAdapter(Context context, List<Data.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.recylerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.text01.setText(list.get(position).getUserAge()+"");
        holder.text02.setText(list.get(position).getOccupation());
        holder.text03.setText(list.get(position).getIntroduction());
        Glide.with(context).load(list.get(position).getImg()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text01, text02, text03;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text01 = itemView.findViewById(R.id.text01);
            text02 = itemView.findViewById(R.id.text02);
            text03 = itemView.findViewById(R.id.text03);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItem.onItemClick(view,getPosition());
                }
            });
        }
    }
    public interface MyItemClickListener {
        void onItemClick(View view,int postion);
    }
    public void setOnItemClickListener(MyItemClickListener listener){
        this.onItem = listener;
    }
}
