package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Drink;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.MultiChoiceViewHolder;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceViewHolder> {

    Context context;
    List<Drink> optionList;

    public MultiChoiceAdapter(Context context, List<Drink> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @Override
    public MultiChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.multi_check_layout,null);

        return new MultiChoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MultiChoiceViewHolder holder, final int position) {

        holder.checkBox.setText(optionList.get(position).Name);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.toppingPrice += Double.parseDouble(optionList.get(position).Price);
                }
                else    {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.toppingPrice -= Double.parseDouble(optionList.get(position).Price);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }
}
