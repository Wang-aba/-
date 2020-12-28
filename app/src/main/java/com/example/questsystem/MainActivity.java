package com.example.questsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.questsystem.db.QuestionDao;
import com.example.questsystem.db.RankingDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mPosition = 0;
    private TextView tvQuestion;
    private RadioButton rbA;
    private RadioButton rbB;
    private RadioButton rbC;
    private RadioButton rbD;
    private RadioGroup radioGroup;
    private List<Question> list;
    private Button btnNext;
    private CountDownTimer timer;
    private TextView tv_title;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = getIntent().getStringExtra("name");
        tv_title = findViewById(R.id.tv_title);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectAnswer();
                setTvQuestion(++mPosition);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectAnswer();
            }
        });

        QuestionDao dao = new QuestionDao(this);
        list = dao.queryAll();

        if (list.isEmpty()) {
            initDb();
        } else {
            setTvQuestion(mPosition);
        }
    }

    private void selectAnswer() {
        if(mPosition>=5){
            return;
        }
        int id = radioGroup.getCheckedRadioButtonId();
        int answer = -1;
        if (id == rbA.getId()) {
            answer = 1;
        } else if (id == rbB.getId()) {
            answer = 2;
        } else if (id == rbC.getId()) {
            answer = 3;
        } else if (id == rbD.getId()) {
            answer = 4;
        }
        list.get(mPosition).answerKey = answer;
    }

    private void setTvQuestion(int position) {
        if (position >= 5) {
            //最后一题
            return;
        }
        tv_title.setText("第" + (position + 1) + "题");
        Question question = list.get(position);
        tvQuestion.setText(question.question);
        rbA.setChecked(false);
        rbB.setChecked(false);
        rbC.setChecked(false);
        rbD.setChecked(false);
        rbA.setText(question.keyA);
        rbB.setText(question.keyB);
        rbC.setText(question.keyC);
        rbD.setText(question.keyD);
        if (position < list.size() - 1) {
            setCountDown("下一提");
        } else {
            setCountDown("最后一题");
        }
    }

    private void setCountDown(String txt) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                btnNext.setText(txt + (millisUntilFinished / 1000) + "秒");

            }

            public void onFinish() {
                timer.cancel();
                if (mPosition < list.size() - 1) {
                    setTvQuestion(++mPosition);
                } else {
                    btnNext.setText("完成");
                    int count = 0;
                    for (Question q : list) {
                        if (q.answer == q.answerKey) {
                            count = count + 20;
                        }
                    }

                    RankingBean rankingBean = new RankingBean();
                    rankingBean.name = name;
                    rankingBean.score = count;
                    new RankingDao(MainActivity.this).insert(rankingBean);
                    Intent intent = new Intent(MainActivity.this, RankingListActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        //调用 CountDownTimer 对象的 start() 方法开始倒计时，也不涉及到线程处理
        timer.start();
    }


    private Question getQuestion(String question, String keyA, String keyB, String keyC, String keyD, int answer) {
        return new Question(question, keyA, keyB, keyC, keyD, answer);
    }

    private void initDb() {

        QuestionDao dao = new QuestionDao(this);


        dao.insertQuestion(getQuestion("下列组合中，两地主要产业部门不相一致的是:",
                "福山一匹兹堡",
                "丰细一 底特律",
                "硅谷一 九州岛",
                "休斯敦一 长崎",
                4));
        dao.insertQuestion(getQuestion("有关尼罗河、阿姆河、印度河的下列说法，正确的是:"
                , "都是外流河B、 都流经热带沙漠地区"
                , "都流经热带沙漠地区"
                , "都是沿岸地区，重要的灌溉水源"
                , "都是古代文明的摇篮",
                3));

        dao.insertQuestion(getQuestion("地跨两大洲，首都在西半球的是: "
                , "埃及"
                , "俄罗斯"
                , "国"
                , "其他"
                , 3));
        dao.insertQuestion(getQuestion("世界上最大的白鹤栖息地是在:"
                , "鄱阳湖"
                , "洞庭湖"
                , "太湖"
                , "巢湖"
                , 1));
        dao.insertQuestion(getQuestion("长江三峡位于最西面的一个峡谷是:"
                , "西陵峡"
                , "巫峡"
                , "瞿塘峡"
                , "三门峡"
                , 3));

        dao.insertQuestion(getQuestion("下列城市中，按工矿城、农林城、旅游城顺序排列的一组专业性城市是:"
                , "徐州~郑州~杭州"
                , "长春~伊春~宜春"
                , "鞍山~舢~黄山"
                , "金錫~南昌~唱"
                , 2));
        dao.insertQuestion(getQuestion("次撒哈拉非洲居民完全依靠两种可以在干旱贫瘠土壤上旺盛生长的抗旱和耐热谷物,一种是小米另- -种是:"
                , "高粱"
                , "小麦"
                , "黑麦"
                , "燕麦"
                , 1));
        dao.insertQuestion(getQuestion("世界上人口最稠密的农业国家是:"
                , "比利时"
                , "B、荷兰"
                , "C、勐拉国"
                , "D、日本"
                , 3));
        dao.insertQuestion(getQuestion("乌鲁木齐是中国大陆最富异国情调的城市,下列哪一叙述正确:"
                , "位于中缅公路的东端"
                , "比世界上任何城市离海洋更远"
                , "亚洲最高的城市"
                , "亚洲第一个人口达到200万的城市"
                , 2));
        dao.insertQuestion(getQuestion("中东哪-城市是三个宗教的圣地:"
                , "伊斯坦布尔"
                , "大马土革"
                , "拿撒勒"
                , "耶路撒冷"
                , 4));
        dao.insertQuestion(getQuestion("世界上最大的海湾是:"
                , "勐拉湾"
                , "墨西哥湾"
                , "亚丁湾"
                , "几内亚湾"
                , 4));
        dao.insertQuestion(getQuestion("按最近距离看，北美洲位于非洲的:"
                , "东北方向"
                , "西北方向"
                , "西南方向"
                , "东南方"
                , 2));

        dao.insertQuestion(getQuestion(
                "美国国土未跨"
                , "北寒带"
                , "热带"
                , "北温带"
                , "南温带"
                , 4));
        dao.insertQuestion(getQuestion(
                "日本国土中最大的岛是"
                , "北海道"
                , "本州"
                , "四国"
                , "九州"
                , 2));
        dao.insertQuestion(getQuestion(
                "\"风车之国\"的风车是用来"
                , "观赏的"
                , "向堤外提水的"
                , "吹风的"
                , "自然的"
                , 2));
        dao.insertQuestion(getQuestion(
                "有“世界火炉”之称的城市是"
                , "喀土穆"
                , "伦敦"
                , "巴格达"
                , "华盛顿"
                , 1));
        dao.insertQuestion(getQuestion(
                "下列地区中,动、植物种类最多的是"
                , "亚马孙雨林"
                , "非洲"
                , "中美洲"
                , "密克罗尼西亚群岛"
                , 2));
        dao.insertQuestion(getQuestion(
                "北美五大湖的成因是"
                , "冰川侵蚀"
                , "冰川堆积"
                , "流水侵蚀"
                , "地壳断裂、凹陷"
                , 1));
        dao.insertQuestion(getQuestion(
                "目前巴以争端的焦是"
                , "淡水资源"
                , "矿产资源"
                , "土地主权"
                , "宗教信仰"
                , 3));
        dao.insertQuestion(getQuestion(
                "中秋节那天月亮升起的时刻是"
                , "日落时"
                , "子夜"
                , "中午"
                , "日出前2小时"
                , 1));

    }
}