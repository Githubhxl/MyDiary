package com.nexuslink.mydiary.view;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nexuslink.mydiary.R;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivityForget extends AppCompatActivity implements OnItemClickListener {
    @BindView(R.id.recycled_items)
    SwipeMenuRecyclerView recyclerItems;
    private static final int TYPEADD = 0;
    private static final int TYPENORMAL = 1;

    private AlertView deleteAlert;
    private AlertView editAlert;
    private AlertView addAlert;

    private ItemsAdapter itemAdapter;
    private List<String> list = new ArrayList<>();
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 200;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(EditActivityForget.this)
                        .setBackgroundDrawable(R.color.colorRed)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
                SwipeMenuItem deleteItem2 = new SwipeMenuItem(EditActivityForget.this)
                        .setBackgroundDrawable(R.color.colorfacebookBlue)
                        .setText("完成")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem2);
                SwipeMenuItem deleteItem3 = new SwipeMenuItem(EditActivityForget.this)
                        .setBackgroundDrawable(R.color.colorIosYellow)
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem3);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_forget);
        ButterKnife.bind(this);

        // 测试数据
        list.add("sdkl");
        list.add("sdk2");list.add("sdk3");list.add("sdk4");list.add("sdkl");list.add("sdk5");list.add("sdk58");

        recyclerItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerItems.setAdapter(itemAdapter = new ItemsAdapter( list));
        recyclerItems.setItemAnimator(new DefaultItemAnimator());
        recyclerItems.setHasFixedSize(true);
        recyclerItems.setLongPressDragEnabled(true);
        recyclerItems.setSwipeMenuCreator(swipeMenuCreator);
        itemAdapter.setOnItemClickListener(new ItemsViewHolder.OnItemClickListener() {
            @Override
            public void onClick(ItemsViewHolder holder, int position) {

            }
        });
        recyclerItems.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(int fromPosition, int toPosition) {
                Collections.swap(list,fromPosition,toPosition);
                itemAdapter.notifyItemMoved(fromPosition,toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(int position) {
            }
        });
        recyclerItems.setSwipeMenuItemClickListener(new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();

                if(direction == SwipeMenuRecyclerView.RIGHT_DIRECTION){
                    switch (menuPosition){
                        case 0:
                            deleteAlert = new AlertView("提示", "确认删除备忘？", "取消", null, new String[]{"确认"}, EditActivityForget.this,
                                    AlertView.Style.Alert, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    if(position == 0){
                                        itemAdapter.deleteItem(adapterPosition);
                                    }

                                }
                            });
                            deleteAlert.show();
                            break;
                        case 1:
                            //完成备忘
                            new AlertView("编辑12", null, "取消", null, new String[]{"完成"}, EditActivityForget.this,
                                    AlertView.Style.Alert, EditActivityForget.this).addExtView(LayoutInflater.from(EditActivityForget.this).inflate(R.layout.dialog_additem,null))
                                    .show();
                            break;
                        case 2:
                            View view = LayoutInflater.from(EditActivityForget.this).inflate(R.layout.dialog_additem,null);
                            final EditText edit = (EditText) view.findViewById(R.id.edit_item);
                            edit.setText(list.get(adapterPosition));
                            editAlert = new AlertView("编辑备忘", null, "取消", null, new String[]{"完成"}, EditActivityForget.this,
                                    AlertView.Style.Alert, new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    if (position == 0){
                                        itemAdapter.changeItem(edit.getText().toString(),adapterPosition);
                                    }
                                }
                            }).addExtView(view);
                            editAlert.show();
                            break;

                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(Object o, int position) {

    }

    private class ItemsAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder>{
        private List<String> list;
        private ItemsViewHolder.OnItemClickListener onItemClickListener;

        public void deleteItem(int position){
            list.remove(position);
            notifyItemRemoved(position);
        }
        public void additem(String s) {
            list.add(0,s);
            notifyItemInserted(0);
        }

        public void changeItem(String s, int adapterPosition) {
            list.set(adapterPosition,s);
            notifyDataSetChanged();
        }

        public ItemsAdapter(List<String> list) {
            this.list =list;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return  LayoutInflater.from(EditActivityForget.this).inflate(R.layout.item_items,parent,false);
        }


        @Override
        public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            if(viewType == TYPEADD){
                return new AddViewHolder(LayoutInflater.from(EditActivityForget.this).inflate(R.layout.item_addforget,null));
            }
            return new ItemsViewHolder(realContentView);
        }



        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof  ItemsViewHolder){
                final ItemsViewHolder itemsViewHolder = (ItemsViewHolder)holder;
                itemsViewHolder.item.setText(list.get(position));
                itemsViewHolder.item.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       onItemClickListener.onClick(itemsViewHolder,position);
                                                   }

                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size()+1;
        }

        public void setOnItemClickListener(ItemsViewHolder.OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }


        @Override
        public int getItemViewType(int position) {
            if(position == itemAdapter.getItemCount() - 1){
                return TYPEADD;
            }else{
                return TYPENORMAL;
            }
        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private View editView;

        public AddViewHolder(View realContentView) {
            super(realContentView);
            imageView  = (ImageView) realContentView.findViewById(R.id.add_forget_items);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAlert = new AlertView("添加", null, "取消", null, new String[]{"完成"}, EditActivityForget.this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 0){
                                EditText edit = (EditText) editView.findViewById(R.id.edit_item);
                                itemAdapter.additem(edit.getText().toString());
                            }

                        }
                    }).addExtView(editView = LayoutInflater.from(EditActivityForget.this).inflate(R.layout.dialog_additem,null));
                    addAlert.show();
                }
            });
        }
    }
    private static class ItemsViewHolder extends RecyclerView.ViewHolder{
        private TextView item;
        public ItemsViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.item);
        }
        public interface OnItemClickListener{
            void onClick( ItemsViewHolder holder, int position);
        }
    }
}