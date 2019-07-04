package br.uff.caronet.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.activities.MainActivity;
import br.uff.caronet.models.TestRide;
import br.uff.caronet.models.ViewUser;
import br.uff.caronet.service.Dao;

public class RidesAdapter extends FirestoreRecyclerAdapter <TestRide, RidesAdapter.ViewHolderRides>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
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

                Log.v("Clicked on: ","Driver Id: "+model.getDriver().getId()+
                        " - Name: "+model.getDriver().getName());

                for (ViewUser user: model.getPassengers()){
                    Log.v("passenger.id: ", user.getId());
                    Log.v("passenger.name: ", user.getName());
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
