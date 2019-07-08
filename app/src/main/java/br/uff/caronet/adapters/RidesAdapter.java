package br.uff.caronet.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.Util.OnItemClickListener;
import br.uff.caronet.models.TestRide;
import br.uff.caronet.models.ViewUser;

public class RidesAdapter extends FirestoreRecyclerAdapter <TestRide, RidesAdapter.ViewHolderRides>{

    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }

    public RidesAdapter(@NonNull FirestoreRecyclerOptions<TestRide> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderRides holder, int position, @NonNull final TestRide model) {

        String departure = "From: ";
        String arrival = "To: ";

        if (model.isGoingToUff()){
            departure += model.getNeighborhood();
            arrival += model.getCampus();
        }
        else {
            departure += model.getCampus();
            arrival += model.getNeighborhood();
        }

        holder.tvName.setText(model.getDriver().getName());
        holder.tvDate.setText(model.getDeparture().toString());
        holder.tvDeparture.setText(departure);
        holder.tvArrival.setText(arrival);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if  (mListener != null){
                    mListener.onItemClick(v, model);
                }
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
