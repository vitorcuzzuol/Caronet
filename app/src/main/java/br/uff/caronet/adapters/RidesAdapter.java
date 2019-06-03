package br.uff.caronet.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.models.TestUser;

public class RidesAdapter extends FirestoreRecyclerAdapter <TestUser, RidesAdapter.ViewHolderRides>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RidesAdapter(@NonNull FirestoreRecyclerOptions<TestUser> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderRides holder, int position, @NonNull TestUser model) {

        holder.tvName.setText(model.getName());
        holder.tvEmail.setText(model.getEmail());
    }

    @NonNull
    @Override
    public ViewHolderRides onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rides,
                viewGroup, false);

        return new ViewHolderRides(view);
    }

    class ViewHolderRides extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvEmail;


        public ViewHolderRides(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);

        }
    }
}
