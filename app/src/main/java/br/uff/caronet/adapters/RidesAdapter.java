package br.uff.caronet.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.R;
import br.uff.caronet.models.TestRide;

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

        holder.tvName.setText(model.getDriver().getName());
        holder.tvDate.setText(model.getDeparture().toString());

        if (model.isGoingToUff()){
            holder.tvIsGoingToUff.setText(R.string.gotouff);
        }
        else {
            holder.tvIsGoingToUff.setText(R.string.leavingUff);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("Clicked on: ","Driver Id: "+model.getDriver().getDriverId()+
                        " - Name: "+model.getDriver().getName());
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
        TextView tvIsGoingToUff;
        TextView tvDate;

        public ViewHolderRides(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvIsGoingToUff = itemView.findViewById(R.id.tvIsGoingToUff);

        }
    }
}
