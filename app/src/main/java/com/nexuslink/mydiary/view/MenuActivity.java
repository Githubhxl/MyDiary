package com.nexuslink.mydiary.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nexuslink.mydiary.R;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements OnSwipeMenuItemClickListener {
    @BindView(R.id.forget)
    SwipeMenuRecyclerView swipeMenuRecyclerView;

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 200;

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)// 点击的背景。
                        .setImage(R.mipmap.ic_launcher) // 图标。
                        .setWidth(width) // 宽度。
                        .setHeight(height); // 高度。
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setImage(R.mipmap.ic_launcher)
                        .setWidth(width)
                        .setHeight(height);

                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setImage(R.mipmap.ic_launcher)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem closeItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setImage(R.mipmap.ic_launcher)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加一个按钮到右侧菜单。

                SwipeMenuItem addItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        /*swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(this);
        swipeMenuRecyclerView.setAdapter(new MenuAdapter());*/

        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        swipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(MenuActivity.this);

       /* mMenuAdapter = new MenuAdapter(mStrings);*/
       /* mMenuAdapter.setOnItemClickListener(onItemClickListener);*/
        swipeMenuRecyclerView.setAdapter(new MenuAdapter());
    }


    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {

    }

    private class MenuAdapter extends SwipeMenuAdapter<ViewHolder> {
        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MenuActivity.this).inflate(R.layout.item_forget,parent,false);
            return view;
        }

        @Override
        public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            return new ViewHolder(realContentView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
