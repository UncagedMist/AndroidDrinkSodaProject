package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.OrderDetailViewHolder;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailViewHolder>  {

    Context context;
    List<Cart> cartList;

    public OrderDetailAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public OrderDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View iteView = LayoutInflater.from(context)
                .inflate(R.layout.order_detail_layout,parent,false);

        return new OrderDetailViewHolder(iteView);
    }

    @Override
    public void onBindViewHolder(final OrderDetailViewHolder holder, final int position) {

        Picasso.with(context)
                .load(cartList.get(position).link)
                .into(holder.img_product);

        holder.txt_price.setText(new StringBuilder("$ ").append(cartList.get(position).price));
        holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                .append(" x")
                .append(cartList.get(position).amount)
                .append(" ")
                .append(cartList.get(position).size == 0 ? " Size M" : " Size L"));

        holder.txt_sugar_ice.setText(new StringBuilder("Sugar : ")
                .append(cartList.get(position).sugar)
                .append("%")
                .append("\n")
                .append("Ice : ")
                .append(cartList.get(position).ice)
                .append("%").toString());
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