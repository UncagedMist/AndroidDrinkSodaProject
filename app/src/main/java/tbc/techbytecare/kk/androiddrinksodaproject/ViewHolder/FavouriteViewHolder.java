package tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tbc.techbytecare.kk.androiddrinksodaproject.R;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {

    public ImageView img_product;
    public TextView txt_product_name,txt_price;

    public RelativeLayout view_background;
    public LinearLayout view_foreground;

    public FavouriteViewHolder(View itemView) {
        super(itemView);

        img_product = itemView.findViewById(R.id.img_product);
        txt_product_name = itemView.findViewById(R.id.txt_product_name);
        txt_price = itemView.findViewById(R.id.txt_price);

        view_background = itemView.findViewById(R.id.view_background);
        view_foreground = itemView.findViewById(R.id.view_foreground);
    }
}
