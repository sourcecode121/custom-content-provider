package com.example.contentprovider.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentprovider.R;
import com.example.contentprovider.model.User;

import java.util.List;

/**
 * Created by Anand on 28/10/2016.
 */

public class ShowUsersAdapter extends RecyclerView.Adapter<ShowUsersAdapter.ViewHolder> {

    private List<User> users;
    private OnItemClickListener clickListener;

    public ShowUsersAdapter(List<User> users) {
        this.users = users;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_users_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());
        holder.age.setText(String.valueOf(users.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView firstName;
        private TextView lastName;
        private TextView age;

        public ViewHolder(View itemView) {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.show_first_name);
            lastName = (TextView) itemView.findViewById(R.id.show_last_name);
            age = (TextView) itemView.findViewById(R.id.show_age);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
