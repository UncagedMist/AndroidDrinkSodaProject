package tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import tbc.techbytecare.kk.androiddrinksodaproject.R;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public ImageView img_product;
    public TextView txt_product_name,txt_sugar_ice,txt_price;
    public ElegantNumberButton txt_amount;

    public CartViewHolder(View itemView) {
        super(itemView);

        img_product = itemView.findViewById(R.id.img_product);

        txt_amount = itemView.findViewById(R.id.txt_amount);
        txt_product_name = itemView.findViewById(R.id.txt_product_name);
        txt_sugar_ice = itemView.findViewById(R.id.txt_sugar_ice);
        txt_price = itemView.findViewById(R.id.txt_price);
    }
}
