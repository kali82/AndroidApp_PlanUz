package com.blackhammers.kalisz.planuz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kalis on 2018-05-25.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    List<Subjects> subjectsList;

    public UserListAdapter(List<Subjects> subjectsList) {
        this.subjectsList = subjectsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bindView(subjectsList.get(position));

    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.show_user_email);
        }
        public void bindView(Subjects subjects) {
            name.setText(subjects.getName());
        }
    }
}
