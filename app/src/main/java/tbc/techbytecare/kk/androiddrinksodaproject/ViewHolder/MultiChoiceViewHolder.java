package tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import tbc.techbytecare.kk.androiddrinksodaproject.R;

public class MultiChoiceViewHolder extends RecyclerView.ViewHolder {

    public CheckBox checkBox;

    public MultiChoiceViewHolder(View itemView) {
        super(itemView);

        checkBox = itemView.findViewById(R.id.ckb_toppings);
    }
}
