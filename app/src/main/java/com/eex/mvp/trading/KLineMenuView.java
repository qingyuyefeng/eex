package com.eex.mvp.trading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eex.R;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: Futures
 * @Package: com.futures.trading.weight
 * @ClassName: KLineMenuView
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/8/26 16:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/26 16:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KLineMenuView extends LinearLayout {

    private View tabView;

    private LayoutParams tabParams;


    private int selIndex = -1;

    public int getSelIndex() {
        return selIndex;
    }

    public KLineMenuView(Context context) {
        this(context, null);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(HORIZONTAL);
    }

    public void setData(List<String> dataList,int position) {
        if(dataList!=null && dataList.size()>0){
            removeAllViews();
            for (int i = 0; i < dataList.size(); i++) {
                String text = dataList.get(i);
                addTab(text, i);
            }
            updateTabSelection(position);
        }
    }

    private void addTab(String text, int index) {
        tabView = View.inflate(getContext(), R.layout.content_k_line_menu_item, null);
        TextView tv_content = tabView.findViewById(R.id.tv_content);
        tv_content.setText(text);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = indexOfChild(v);
                updateTabSelection(index);
            }
        });

        tabParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        this.addView(tabView, index, tabParams);
    }

    private void updateTabSelection(int index) {
        if (index == selIndex) return;
        this.selIndex = index;
        int mTabCount = getChildCount();
        for (int i = 0; i < mTabCount; i++) {
            View tabView = getChildAt(i);
            View indicatorView = tabView.findViewById(R.id.indicator);
            final boolean isSelect = i == index;
            indicatorView.setVisibility(isSelect ? VISIBLE : INVISIBLE);
        }
        if (mOnTabSelectListener != null) mOnTabSelectListener.onTabClick(index);
    }

    private OnTabSelectListener mOnTabSelectListener;

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.mOnTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onTabClick(int position);
    }

}
