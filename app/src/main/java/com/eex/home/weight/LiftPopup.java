package com.eex.home.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.eex.R;
import com.eex.home.activity.home.MoneyListActivity;
import com.eex.home.adapter.LiftAdapter;
import com.eex.home.bean.SecondRecordlist;

import java.util.ArrayList;

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
 * @ProjectName: OverThrow
 * @Package: com.overthrow.home.weight
 * @ClassName: LiftPopup
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/7/12 17:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/12 17:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LiftPopup extends PopupWindow {


    private Context myContext;
    private ListView myLv;
    private AdapterView.OnItemClickListener myOnItemClickListener;
    private ArrayList<SecondRecordlist> myItems;
    private int myWidth;
    private int myHeight;
    private int myType;

    // 判断是否需要添加或更新列表子类项
    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;

    private LiftAdapter adapter;
    public LiftPopup(Context context) {
        // TODO Auto-generated constructor stub
    }
    public LiftPopup(Context context, int width, int height,
                     AdapterView.OnItemClickListener onItemClickListener, ArrayList<SecondRecordlist> items,
                     int type) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.popup_top, null);

        this.myContext = context;
        this.myItems = items;
        this.myOnItemClickListener = onItemClickListener;
        this.myType = type;

        this.myWidth = width;
        this.myHeight = height;

        System.out.println("--myWidth--:" + myWidth + "--myHeight--:"
                + myHeight);
        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        myLv = (ListView) myMenuView.findViewById(R.id.listview);
        popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
        myLv.setOnItemClickListener(myOnItemClickListener);

        if (myType == 1) {
            LinearLayout.LayoutParams lpPopup = (LinearLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins(0, 0, (int) (myWidth * 3.0 / 4), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if (myType == 2) {
            LinearLayout.LayoutParams lpPopup = (LinearLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins((int) (myWidth * 3.0 / 4), 0, 0, 0);
            popupLL.setLayoutParams(lpPopup);
        }
    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        if (myType == 1) {
            this.setAnimationStyle(R.style.AnimTopLeft);
        } else if (myType == 2) {
            this.setAnimationStyle(R.style.AnimTopRight);
        } else {
            //this.setAnimationStyle(R.style.AnimTop);
            this.setAnimationStyle(R.style.AnimTopMiddle);
        }
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        myMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = popupLL.getBottom();
                int left = popupLL.getLeft();
                int right = popupLL.getRight();
                System.out.println("--popupLL.getBottom()--:"
                        + popupLL.getBottom());
                int y = (int) event.getY();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height || x < left || x > right) {
                        System.out.println("---点击位置在列表下方--");
                        MoneyListActivity.img_biname.setImageResource(R.drawable.shouqi);
                        MoneyListActivity.tx_BiName.setTextColor(myContext.getResources().getColor(R.color.appbar_background3));
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {
        if (myIsDirty) {
            myIsDirty = false;
            adapter = new LiftAdapter((Activity) myContext, myItems);
            myLv.setAdapter(adapter);
        }

        showAsDropDown(view, 0, 0);
    }
}
