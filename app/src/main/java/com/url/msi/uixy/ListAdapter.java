package com.url.msi.uixy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.util.Collections;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements ItemTouchHelper {
    private List<String> list;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;

    public ListAdapter(List<String> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_style,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("identifier.db");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posi = holder.getAdapterPosition();
                String a = list.get(posi);
                Toast.makeText(view.getContext(),a,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(),ChangeActivity.class);
                intent.putExtra("cont",a);
                view.getContext().startActivity(intent);
            }
        });



        return holder;
    }

    public void deleitem(int postion)
    {
        this.notifyItemRemoved(postion);
        list.remove(postion);

    }


    @Override
    public void onItemMove(int startPostion, int endPosttion) {
        Collections.swap(list,startPostion,endPosttion);
        this.notifyItemMoved(startPostion,endPosttion);
    }
    public void deleDb(String ss)
    {
        mg = AssetsDatabaseManager.getManager();
        db = mg.getDatabase("identifier.db");
        String whereCl = "context=?";
        String[] WhereArg = {ss};
        db.delete("file",whereCl,WhereArg);
    }



    @Override
    public void onItemDele(int postion) {
        deleDb(list.get(postion));
        deleitem(postion);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wordView;
        private TextView timeView;
        public ViewHolder(View itemView) { super(itemView);
            wordView = itemView.findViewById(R.id.wordShow);
            /*timeView = itemView.findViewById(R.id.timeShow)*/
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.wordView.setText(list.get(position));

    } //这个方法很简单了，返回List中的子项的个数





    @Override
    public int getItemCount() {
        return list.size();
    }



}
