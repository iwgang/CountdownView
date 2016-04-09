package cn.iwgang.countdownviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.CustomCountDownTimer;


/*
     此类模拟在ListView中使用倒计时
 */
public class ListViewActivity extends ActionBarActivity {
    private Map<Long, MyCustomCountDownTimer> mCustomCountDownTimers;
    private List<ItemInfo> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initData();

        ListView mLvList = (ListView) findViewById(R.id.lv_list);
        mLvList.setAdapter(new MyListAdapter(this, mDataList, mCustomCountDownTimers));
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mCustomCountDownTimers = new HashMap<>();

        for (int i = 1; i < 20; i++) {
            mDataList.add(new ItemInfo(1000 + i, "ListView_测试标题_" + i, i * 20 * 1000));
        }

        // 预处理倒计时
        for (ItemInfo itemInfo : mDataList) {
            long curID = itemInfo.getId();
            long countdown = itemInfo.getCountdown();

            // 如果下拉刷新之类的要重新加载数据, 建议加上下面这段
//            if (mCustomCountDownTimers.containsKey(curID)) {
//                MyCustomCountDownTimer curMyCustomCountDownTimer = mCustomCountDownTimers.get(curID);
//                curMyCustomCountDownTimer.stop();
//                mCustomCountDownTimers.remove(curID);
//            }

            MyCustomCountDownTimer myCustomCountDownTimer = new MyCustomCountDownTimer(countdown, 16, itemInfo); // 第二个参数是间隔时间, 如果倒计时只到秒级别的, 请填1000
            myCustomCountDownTimer.start();
            mCustomCountDownTimers.put(curID, myCustomCountDownTimer);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 处理线程可能占用的资源不能回收问题
        if (null != mCustomCountDownTimers && mCustomCountDownTimers.size() > 0) {
            for (Map.Entry<Long, MyCustomCountDownTimer> ccd : mCustomCountDownTimers.entrySet()) {
                ccd.getValue().stop();
                ccd.setValue(null);
            }

            mCustomCountDownTimers.clear();
            mCustomCountDownTimers = null;
        }
    }

    class MyCustomCountDownTimer extends CustomCountDownTimer {
        private CountdownView mCountdownView;
        private ItemInfo mItemInfo;

        public MyCustomCountDownTimer(long millisInFuture, long countDownInterval, ItemInfo itemInfo) {
            super(millisInFuture, countDownInterval);
            this.mItemInfo = itemInfo;
        }

        public void refView(CountdownView countdownView) {
            this.mCountdownView = countdownView;
        }

        @Override
        public void onTick(long l) {
            if (l < 0) l = 0;

            mItemInfo.setCountdown(l);

            // 这里需要注意会有很多情况下出现两个线程去刷新同一个CountdownView，所以使用tag中获取显示的item和当前的对象对比是否是同一个
            if (null != mCountdownView && Long.valueOf(mCountdownView.getTag().toString()) == mItemInfo.getId()) {
                mCountdownView.updateShow(l);
            }
        }

        @Override
        public void onFinish() {
            mItemInfo.setCountdown(0);
            if (null != mCountdownView && Long.valueOf(mCountdownView.getTag().toString()) == mItemInfo.getId()) {
                mCountdownView.updateShow(0);
            }
            mCustomCountDownTimers.remove(mItemInfo.getId());
        }
    }


    static class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private List<ItemInfo> mDatas;
        private Map<Long, MyCustomCountDownTimer> mCustomCountDownTimers;

        public MyListAdapter(Context context, List<ItemInfo> datas, Map<Long, MyCustomCountDownTimer> customCountDownTimers) {
            this.mContext = context;
            this.mDatas = datas;
            this.mCustomCountDownTimers = customCountDownTimers;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.cvCountdownView = (CountdownView) convertView.findViewById(R.id.cv_countdownView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ItemInfo curItemInfo = mDatas.get(position);
            long curID = curItemInfo.getId();

            holder.cvCountdownView.setTag(curID);
            long countdown = curItemInfo.getCountdown();
            if (countdown > 0) {
                if (mCustomCountDownTimers.containsKey(curID)) {
                    MyCustomCountDownTimer curMyCustomCountDownTimer = mCustomCountDownTimers.get(curID);
                    curMyCustomCountDownTimer.refView(holder.cvCountdownView);
                }
            } else {
                holder.cvCountdownView.allShowZero();
            }

            holder.tvTitle.setText(curItemInfo.getTitle());

            return convertView;
        }

        static class ViewHolder {
            TextView tvTitle;
            CountdownView cvCountdownView;
        }
    }

    static class ItemInfo {
        private long id;
        private String title;
        private long countdown;

        public ItemInfo(long id, String title, long countdown) {
            this.id = id;
            this.title = title;
            this.countdown = countdown;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
    }

}


