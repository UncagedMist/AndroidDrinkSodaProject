package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.CartViewHolder;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>  {

    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View iteView = LayoutInflater.from(context)
                .inflate(R.layout.cart_item_layout,parent,false);

        return new CartViewHolder(iteView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {

        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_product);

        holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));

        holder.txt_price.setText(new StringBuilder("$ ").append(cartList.get(position).price));
        holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                .append(" x")
                .append(cartList.get(position).amount)
                .append(cartList.get(position).size == 0 ? " Size M" : " Size L"));

        holder.txt_sugar_ice.setText(new StringBuilder("Sugar : ")
            .append(cartList.get(position).sugar)
            .append("%")
            .append("\n")
            .append("Ice : ")
            .append(cartList.get(position).ice)
            .append("%").toString());

        final double priceOneCup = cartList.get(position).price / cartList.get(position).amount;

        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(position);
                cart.amount = newValue;
                cart.price = Math.round(priceOneCup * newValue);

                Common.cartRepository.updateCart(cart);
                holder.txt_price.setText(new StringBuilder("$ ")
                        .append(cartList.get(position).price));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position)    {
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position)   {
        cartList.add(position,item);
        notifyItemInserted(position);
    }
}
