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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.nexuslink.mydiary.R;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements OnSwipeMenuItemClickListener {
    @BindView(R.id.forget)
    SwipeMenuRecyclerView swipeMenuRecyclerView;

    private MenuAdapter menuAdapter;
    private AlertView deleteAlert;
    private AlertView editAlert;
    private AlertView addAlert;

    private List<String> list = new ArrayList<>();
    private EditText editText;

    private static final int TYPECALL = 1;
    private static final int TYPEDIARY = 2;
    private static final int TYPENORMAL = 3;
    private static final int TYPEADD = 4;

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 200;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if(viewType == TYPENORMAL){

                SwipeMenuItem deleteItem = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorRed)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
                SwipeMenuItem deleteItem2 = new SwipeMenuItem(MenuActivity.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setText("编辑")
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
        list.add("sdkl");
        list.add("sdk2");list.add("sdk3");list.add("sdk4");list.add("sdkl");list.add("sdk5");list.add("sdk58");
    }

    private void initRecyclerView() {
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setHasFixedSize(true);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(MenuActivity.this);
        swipeMenuRecyclerView.setAdapter( menuAdapter = new MenuAdapter(list));
        menuAdapter.setOnItemClickListener(new NormalViewHolder.OnItemClickListener() {
            @Override
            public void onClick(RecyclerView.ViewHolder holder, int position) {

                    Intent intent = new Intent(MenuActivity.this,EditActivityForget.class);
                    startActivity(intent);
                    Toast.makeText(MenuActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
        closeable.smoothCloseMenu();
        if(direction == SwipeMenuRecyclerView.RIGHT_DIRECTION){
            switch (menuPosition){
                case 0:
                    deleteAlert = new AlertView("提示", "确认删除备忘？", "取消", null, new String[]{"确认"}, this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if(position == 0){
                                menuAdapter.deleteItem(adapterPosition);
                            }

                        }
                    });
                    deleteAlert.show();
                    break;
                case 1:
                    View view = LayoutInflater.from(this).inflate(R.layout.dialog_additem,null);
                    final EditText edit = (EditText) view.findViewById(R.id.edit_item);
                    edit.setText(list.get(adapterPosition - 2));
                    editAlert = new AlertView("编辑备忘", null, "取消", null, new String[]{"完成"}, this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0){
                                menuAdapter.changeItem(edit.getText().toString(),adapterPosition);
                            }
                        }
                    }).addExtView(view);
                    editAlert.show();
                    break;
            }
        }

    }

    private class MenuAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
        private List<String> list;
        private NormalViewHolder.OnItemClickListener onItemClickListener;

        public MenuAdapter(List<String> list) {
            this.list = list;
        }

        public void deleteItem(int position){
            list.remove(position - 2);
            notifyItemRemoved(position);
        }
        public void additem(String s) {
            list.add(0,s);
            notifyItemInserted(2);
        }

        public void changeItem(String s, int adapterPosition) {
            list.set(adapterPosition - 2,s);
            notifyDataSetChanged();
        }

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
            }else if (position == menuAdapter.getItemCount() - 1){
                return TYPEADD;
            }
            return TYPENORMAL;
        }

        public void setOnItemClickListener(NormalViewHolder.OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            if(viewType == TYPENORMAL){
                return new NormalViewHolder(realContentView);
            }else if(viewType == TYPEDIARY){
                return new DiaryViewHolder(realContentView);
            }else if(viewType == TYPECALL){
                return new CallViewHolder( realContentView);
            }if(viewType == TYPEADD){
                return new AddForgetHolder(LayoutInflater.from(MenuActivity.this).inflate(R.layout.item_addforget,null));
            }
            return null;
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if( holder instanceof NormalViewHolder){
                ((NormalViewHolder) holder).textView.setText(list.get(position - 2));
                ((NormalViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(holder,position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size()+ 3;
        }
    }

    //三种viewHolder
    private static class NormalViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootView;
        private TextView textView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.rootview_forget);
            textView = (TextView) itemView.findViewById(R.id.forget_title);
            textView.setText("备忘事项1");
        }
        public interface OnItemClickListener{
            void onClick(RecyclerView.ViewHolder holder, int position);
        }
    }

    private class DiaryViewHolder extends  RecyclerView.ViewHolder {
        private LinearLayout rootView;

        public DiaryViewHolder(View realContentView) {
            super(realContentView);
            rootView = (LinearLayout) itemView.findViewById(R.id.rootview_forget);
            TextView textView = (TextView) realContentView.findViewById(R.id.forget_title);
            ImageView imageView = (ImageView) realContentView.findViewById(R.id.forget_image);
            imageView.setImageDrawable(getDrawable(R.drawable.diary));
            textView.setText("Diary");
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private class CallViewHolder extends  RecyclerView.ViewHolder {
        private LinearLayout rootView;
        public CallViewHolder(View realContentView) {
            super(realContentView);
            rootView = (LinearLayout) itemView.findViewById(R.id.rootview_forget);
            TextView textView = (TextView) realContentView.findViewById(R.id.forget_title);
            ImageView imageView = (ImageView) realContentView.findViewById(R.id.forget_image);
            imageView.setImageDrawable(getDrawable(R.drawable.phone));
            textView.setText("通讯");
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this,CallActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private class AddForgetHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private View editView;
        public AddForgetHolder(View view) {
            super(view);
            imageView  = (ImageView) view.findViewById(R.id.add_forget_items);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     addAlert = new AlertView("添加", null, "取消", null, new String[]{"完成"}, MenuActivity.this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0){
                                EditText edit = (EditText) editView.findViewById(R.id.edit_item);
                                menuAdapter.additem(edit.getText().toString());
                            }

                        }
                    }).addExtView(editView = LayoutInflater.from(MenuActivity.this).inflate(R.layout.dialog_additem,null));
                    addAlert.show();
                }
            });
        }
    }
}
