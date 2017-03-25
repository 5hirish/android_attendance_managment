package com.alleviate.attendence;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by felix on 15/2/16.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    ArrayList<AdminActivity.StudentInfo> student_list;
    Context context;

    public StudentAdapter(ArrayList<AdminActivity.StudentInfo> studentInfos, Context applicationContext) {
        this.student_list = studentInfos;
        this.context = applicationContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_layout,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ((TextView) holder.itemView.findViewById(R.id.student_name)).setText(student_list.get(position).name);
        ((TextView) holder.itemView.findViewById(R.id.student_username)).setText(student_list.get(position).uname);
        ((TextView) holder.itemView.findViewById(R.id.reges_no)).setText(""+student_list.get(position).reg_no);

        ((RelativeLayout) holder.itemView.findViewById(R.id.stud_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,DetailActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("Username",student_list.get(position).uname);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return student_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
