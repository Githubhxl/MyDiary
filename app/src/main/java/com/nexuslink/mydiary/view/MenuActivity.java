package com.nexuslink.mydiary.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private MenuAdapter menuAdapter;

    private static final int TYPECALL = 1;
    private static final int TYPEDIARY = 2;
    private static final int TYPENORMAL = 3;

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 200;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            {
                SwipeMenuItem addItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem);
            }

            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorRed)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
                SwipeMenuItem deleteItem2 = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setText("修改")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem2);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setHasFixedSize(true);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(MenuActivity.this);
        swipeMenuRecyclerView.setAdapter( menuAdapter = new MenuAdapter());
        menuAdapter.setOnItemClickListener(new ViewHolder.OnItemClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder holder, int position) {
                if(position == 0){
                    Intent intent = new Intent(MenuActivity.this,CallActivity.class);
                    startActivity(intent);
                }else if(position == 1){
                    Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.myanim2,R.anim.myanim2);
                    finish();
                }else {
                    Intent intent = new Intent(MenuActivity.this,EditActivityForget.class);
                    startActivity(intent);
                    Toast.makeText(MenuActivity.this,position+"asjfcjas",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
        closeable.smoothCloseMenu();
        /*Toast.makeText(MenuActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
        if(adapterPosition == 0){
            Intent intent = new Intent(MenuActivity.this,CallActivity.class);
            startActivity(intent);
        }else if(adapterPosition == 1){
            Intent intent = new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent);
        }*/
    }

    private class MenuAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
        private ViewHolder.OnItemClickListener onItemClickListener;
        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MenuActivity.this).inflate(R.layout.item_forget,parent,false);
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return TYPECALL;
            }else if(position == 1){
                return TYPEDIARY;
            }
            return TYPENORMAL;
        }

        public void setOnItemClickListener(ViewHolder.OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            if(viewType == TYPENORMAL){
                return new ViewHolder(realContentView);
            }else if(viewType == TYPEDIARY){
                return new DiaryViewHolder(realContentView);
            }else if(viewType == TYPECALL){
                return new CallViewHolder( realContentView);
            }
            return null;
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if( holder instanceof ViewHolder){
                ((ViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(holder,position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    //三种viewHolder
    private static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootView;
        public ViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.rootview_forget);
            TextView textView = (TextView) itemView.findViewById(R.id.forget_title);
            textView.setText("备忘事项1");
        }
        public interface OnItemClickListener{
            void onClick(RecyclerView.ViewHolder holder, int position);
        }
    }

    private class DiaryViewHolder extends ViewHolder {
        public DiaryViewHolder(View realContentView) {
            super(realContentView);
            TextView textView = (TextView) realContentView.findViewById(R.id.forget_title);
            ImageView imageView = (ImageView) realContentView.findViewById(R.id.forget_image);
            imageView.setImageDrawable(getDrawable(R.drawable.diary));
            textView.setText("Diary");
        }
    }

    private class CallViewHolder extends ViewHolder {
        public CallViewHolder(View realContentView) {
            super(realContentView);
            TextView textView = (TextView) realContentView.findViewById(R.id.forget_title);
            ImageView imageView = (ImageView) realContentView.findViewById(R.id.forget_image);
            imageView.setImageDrawable(getDrawable(R.drawable.phone));
            textView.setText("通讯");

        }
    }
}
