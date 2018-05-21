package tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tbc.techbytecare.kk.androiddrinksodaproject.Interface.ItemClickListener;
import tbc.techbytecare.kk.androiddrinksodaproject.R;

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView img_product;
    public TextView txt_drink_name,txt_price;
    public Button btn_add_cart;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DrinkViewHolder(View itemView) {
        super(itemView);

        img_product = itemView.findViewById(R.id.img_product);

        txt_drink_name = itemView.findViewById(R.id.txt_drink_name);
        txt_price = itemView.findViewById(R.id.txt_price);

        btn_add_cart = itemView.findViewById(R.id.btn_add_cart);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v);
    }
}
