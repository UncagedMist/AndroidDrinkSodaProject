package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.FavouriteViewHolder;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

    Context context;
    List<Favourite> favouriteList;

    public FavouriteAdapter(Context context, List<Favourite> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.fav_item_layout,parent,false);


        return new FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        Picasso.with(context)
                .load(favouriteList.get(position).link)
                .into(holder.img_product);

        holder.txt_product_name.setText(favouriteList.get(position).name);
        holder.txt_price.setText(new StringBuilder("$ ").append(favouriteList.get(position).price).toString());
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public void removeItem(int position)    {
        favouriteList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Favourite item,int position)   {
        favouriteList.add(position,item);
        notifyItemInserted(position);
    }
}
