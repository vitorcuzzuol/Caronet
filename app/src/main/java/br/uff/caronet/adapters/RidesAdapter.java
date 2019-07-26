package br.uff.caronet.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.common.OnItemClickListener;
import br.uff.caronet.models.Ride;

public class RidesAdapter extends FirestoreRecyclerAdapter <Ride, RidesAdapter.ViewHolderRides>{

    public OnItemClickListener mListener;
    private boolean isSearching;

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public RidesAdapter(@NonNull FirestoreRecyclerOptions<Ride> options, boolean isSearching) {
        super(options);
        this.isSearching = isSearching;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderRides holder, final int position, @NonNull final Ride model) {


        String departure = "De: ";
        String arrival = "Para: ";

        if (model.isGoingToUff()){
            departure += model.getNeighborhood().getName();
            arrival += model.getCampus();
        }
        else {
            departure += model.getCampus();
            arrival += model.getNeighborhood().getName();
        }


        holder.tvName.setText(model.getDriver().getName());
        holder.tvDate.setText(model.getDeparture().toString());
        holder.tvDeparture.setText(departure);
        holder.tvArrival.setText(arrival);

        holder.itemView.setOnClickListener(v -> {

            if  (mListener != null){
                mListener.onItemClick(v, model, getSnapshots().getSnapshot(position).getId());
            }
        });

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
        TextView tvDate;
        TextView tvDeparture;
        TextView tvArrival;

        public ViewHolderRides(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDeparture = itemView.findViewById(R.id.tvDeparture);
            tvArrival = itemView.findViewById(R.id.tvArrival);

        }
    }
}
