package com.example.questsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.questsystem.db.RankingDao;

import java.util.List;

public class RankingAdapter extends BaseAdapter {
    private List<RankingBean> list;
    private Context mContext;

    public RankingAdapter(List<RankingBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RankingBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ranking, null);
        TextView tvRanking = view.findViewById(R.id.tvRanking);
        tvRanking.setText("第" + (position + 1) + "名  " + getItem(position).name + "     分数" + getItem(position).score);

        ImageView ivDelete = view.findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankingDao dao = new RankingDao(mContext);
                dao.delete(getItem(position));

                list.clear();
                list.addAll(dao.query());
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
