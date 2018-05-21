package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.DrinkActivity;
import tbc.techbytecare.kk.androiddrinksodaproject.Interface.ItemClickListener;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Category;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.CategoryViewHolder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.menu_item_layout,null);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {

        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory = categories.get(position);

                context.startActivity(new Intent(context, DrinkActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
