package com.example.questsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.questsystem.db.RankingDao;

import java.util.List;

public class RankingListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);

        ListView listView = findViewById(R.id.listView);

        List<RankingBean> list = new RankingDao(this).query();
        listView.setAdapter(new RankingAdapter(list, this));
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
