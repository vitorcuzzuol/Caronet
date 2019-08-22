package br.uff.caronet.adapters;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.common.OnItemClickListener;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.ViewUser;
import br.uff.caronet.util.Utils;

public class RidesAdapter extends FirestoreRecyclerAdapter <Ride, RidesAdapter.ViewHolderRides>{

    public OnItemClickListener mListener;
    private boolean isSearching;
    private Context context;

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public RidesAdapter(@NonNull FirestoreRecyclerOptions<Ride> options, boolean isSearching, Context context) {
        super(options);
        this.isSearching = isSearching;
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderRides holder, final int position, @NonNull final Ride model) {
        if (((Dao.get().getUId().equals(model.getDriver().getId()) || isPassenger(model) ) && isSearching))
        {
            if (model.getDeparture() != null) {
                if (Utils.diffInMinutes(model.getDeparture()) < 10) {
                    hideView(holder);
                }
            }
        }


        else {
            String departure = context.getString(R.string.from);
            String arrival = context.getString(R.string.to);


            if (model.isGoingToUff()){
                departure += model.getNeighborhood().getName();
                arrival += model.getCampus();
            }
            else {
                departure += model.getCampus();
                arrival += model.getNeighborhood().getName();
            }


            holder.tvName.setText(model.getDriver().getName());
            holder.tvDate.setText(Utils.dateToStringStr(model.getDeparture()));
            holder.tvDeparture.setText(departure);
            holder.tvArrival.setText(arrival);

            holder.itemView.setOnClickListener(v -> {

                if  (mListener != null){
                    mListener.onItemClick(v, model, getSnapshots().getSnapshot(position).getId());
                }
            });
        }
    }

    private boolean isPassenger(Ride ride) {
        if (ride.getPassengers() != null) {
            for (ViewUser passenger: ride.getPassengers()){
                if (passenger.getId().equals(Dao.get().getUId()))
                    return true;
            }
        }

        return false;
    }

    private void hideView(ViewHolderRides holder) {

        holder.itemView.setVisibility(View.GONE);
        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
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
