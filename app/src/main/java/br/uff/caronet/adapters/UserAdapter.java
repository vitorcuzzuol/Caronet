package br.uff.caronet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.common.OnItemClickListener;
import br.uff.caronet.model.ViewUser;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<ViewUser> users;
    private Context context;

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView imProfile;
        TextView tvName;
        Button btConfirm;
        Button btRemove;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =  itemView.findViewById(R.id.tvName);
            imProfile = itemView.findViewById(R.id.imProfile);
            btRemove = itemView.findViewById(R.id.btRemoveUser);
            btConfirm = itemView.findViewById(R.id.btConfirmUser);
        }
    }

    public  UserAdapter (List<ViewUser> users, Context context) {
        this.context = context;
        this.users = users;

    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_users, parent, false);


        return  new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        ViewUser user = users.get(position);

        holder.tvName.setText(user.getName());

        if (user.getPhoto() != null) {

            Picasso.with(context)
                    .load(user.getPhoto())
                    .fit()
                    .centerCrop()
                    .into(holder.imProfile);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }





}
