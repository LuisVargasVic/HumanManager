package com.venkonenterprises.humanmanager.ui.main.fragments.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;
import com.venkonenterprises.humanmanager.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    UserAdapter(Context context) {
        mContext = context;
        mUsers = new ArrayList<>();
        mUsers.add(
                new User(
                        "Luis",
                        "Vargas",
                        "+52 1122334455",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        1800f
                )
        );
        mUsers.add(
                new User(
                        "Eduardo",
                        "Victoria",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        1200f
                )
        );
        mUsers.add(
                new User(
                        "Alfonso",
                        "Arreola",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "01/01/2014",
                        "",
                        "",
                        "",
                        "",
                        "",
                        714.29f
                )
        );
        mUsers.add(
                new User(
                        "Rafael",
                        "Novoa",
                        "+52 6677889900",
                        "",
                        "",
                        "",
                        "07/01/2019",
                        "",
                        "",
                        "",
                        "",
                        "",
                        257.14f
                )
        );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvLastName;
        TextView tvCellphone;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvCellphone = itemView.findViewById(R.id.tv_cellphone);
        }

        void bind(User user) {
            tvName.setText(user.getName());
            tvLastName.setText(user.getLastName());
            tvCellphone.setText(user.getCellphone());
        }
    }
}
