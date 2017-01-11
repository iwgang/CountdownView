package cn.iwgang.countdownviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;


/**
     此类模拟在ListView中使用倒计时,
     复用 本地的计时器 —— System.currentTimeMillis(), 不必自行计时
 */
public class ListViewActivity extends AppCompatActivity {
    private List<ItemInfo> mDataList;
    private MyListAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initData();

        ListView lvList = (ListView) findViewById(R.id.lv_list);
        lvList.setAdapter(mMyAdapter = new MyListAdapter(this, mDataList));
    }

    private void initData() {
        mDataList = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            mDataList.add(new ItemInfo(1000 + i, "ListView_测试标题_" + i, i * 20 * 1000));
        }

        // 校对倒计时
        long curTime = System.currentTimeMillis();
        for (ItemInfo itemInfo : mDataList) {
            itemInfo.setEndTime(curTime + itemInfo.getCountdown());
        }
    }

    static class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private List<ItemInfo> mDatas;

        public MyListAdapter(Context context, List<ItemInfo> datas) {
            this.mContext = context;
            this.mDatas = datas;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
                holder = new MyViewHolder();
                holder.initView(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            ItemInfo curItemInfo = mDatas.get(position);
            holder.bindData(curItemInfo);

            dealWithLifeCycle(holder, position);

            return convertView;
        }

        /**
         * 以下两个接口代替 activity.onStart() 和 activity.onStop(), 控制 timer 的开关
         */
        private void dealWithLifeCycle(final MyViewHolder holder, final int position) {
            holder.getCvCountdownView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

                @Override
                public void onViewAttachedToWindow(View countdownView) {
                    int pos = position;
//                    Log.d("MyViewHolder", String.format("mCvCountdownView %s is attachedToWindow", pos));

                    ItemInfo itemInfo = mDatas.get(pos);

                    holder.refreshTime(itemInfo.getEndTime() - System.currentTimeMillis());
                }

                @Override
                public void onViewDetachedFromWindow(View countdownView) {
//                    int pos = position;
//                    Log.d("MyViewHolder", String.format("mCvCountdownView %s is detachedFromWindow", pos));

                    holder.getCvCountdownView().stop();
                }
            });
        }

        static class MyViewHolder {
            private TextView mTvTitle;
            private CountdownView mCvCountdownView;
            private ItemInfo mItemInfo;

            public void initView(View convertView) {
                mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                mCvCountdownView = (CountdownView) convertView.findViewById(R.id.cv_countdownView);
            }

            public void bindData(ItemInfo itemInfo) {
                mItemInfo = itemInfo;
                mTvTitle.setText(itemInfo.getTitle());
                refreshTime(mItemInfo.getEndTime() - System.currentTimeMillis());
            }

            public void refreshTime(long leftTime) {
                if (leftTime > 0) {
                    mCvCountdownView.start(leftTime);
                } else {
                    mCvCountdownView.stop();
                    mCvCountdownView.allShowZero();
                }
            }

            public ItemInfo getBean() {
                return mItemInfo;
            }

            public CountdownView getCvCountdownView() {
                return mCvCountdownView;
            }
        }
    }

    static class ItemInfo {
        private int id;
        private String title;
        private long countdown;
        /*
           根据服务器返回的countdown换算成手机对应的开奖时间 (毫秒)
           [正常情况最好由服务器返回countdown字段，然后客户端再校对成该手机对应的时间，不然误差很大]
         */
        private long endTime;

        public ItemInfo(int id, String title, long countdown) {
            this.id = id;
            this.title = title;
            this.countdown = countdown;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCountdown() {
            return countdown;
        }

        public void setCountdown(long countdown) {
            this.countdown = countdown;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }

}


