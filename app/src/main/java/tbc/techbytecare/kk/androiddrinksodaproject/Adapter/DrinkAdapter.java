package tbc.techbytecare.kk.androiddrinksodaproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;
import tbc.techbytecare.kk.androiddrinksodaproject.Interface.ItemClickListener;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Drink;
import tbc.techbytecare.kk.androiddrinksodaproject.R;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.DrinkViewHolder;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    Context context;
    List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @Override
    public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.drink_item_layout,null);

        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DrinkViewHolder holder, final int position) {

        holder.txt_price.setText(new StringBuilder("$ ").append(drinkList.get(position).Price));
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_product);

        holder.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked..", Toast.LENGTH_SHORT).show();
            }
        });

        if (Common.favouriteRepository.isFavourite(Integer.parseInt(drinkList.get(position).ID)) == 1)  {
            holder.imgFav.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
        else    {
            holder.imgFav.setImageResource(R.drawable.ic_favorite_border_24dp);
        }

        holder.imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.favouriteRepository.isFavourite(Integer.parseInt(drinkList.get(position).ID)) != 1)  {
                    addOrRemoveFavourites(drinkList.get(position),true);
                    holder.imgFav.setImageResource(R.drawable.ic_favorite_white_24dp);
                }
                else    {
                    addOrRemoveFavourites(drinkList.get(position),false);
                    holder.imgFav.setImageResource(R.drawable.ic_favorite_border_24dp);
                }
            }
        });
    }

    private void addOrRemoveFavourites(Drink drink, boolean isAdd) {
        Favourite favourite = new Favourite();
        favourite.id = drink.ID;
        favourite.link = drink.Link;
        favourite.name = drink.Name;
        favourite.price = drink.Price;
        favourite.menuId = drink.MenuId;

        if (isAdd)  {
            Common.favouriteRepository.insertFav(favourite);
        }
        else    {
            Common.favouriteRepository.delete(favourite);
        }
    }

    private void showAddToCartDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout,null);

        ImageView img_product_dialog = itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM = itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL = itemView.findViewById(R.id.rdi_sizeL);

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sizeOfCup = 0;
                }
            }
        });

        rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sizeOfCup = 1;
                }
            }
        });

        RadioButton rdi_sugar_100 = itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar_75 = itemView.findViewById(R.id.rdi_sugar_75);
        RadioButton rdi_sugar_50 = itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar_25 = itemView.findViewById(R.id.rdi_sugar_25);
        RadioButton rdi_sugar_free = itemView.findViewById(R.id.rdi_sugar_free);

        rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sugarAmount = 100;
                }
            }
        });

        rdi_sugar_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sugarAmount = 75;
                }
            }
        });

        rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sugarAmount = 50;
                }
            }
        });

        rdi_sugar_25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sugarAmount = 25;
                }
            }
        });

        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.sugarAmount = 0;
                }
            }
        });

        RadioButton rdi_ice_100 = itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_75 = itemView.findViewById(R.id.rdi_ice_75);
        RadioButton rdi_ice_50 = itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_25 = itemView.findViewById(R.id.rdi_ice_25);
        RadioButton rdi_ice_free = itemView.findViewById(R.id.rdi_ice_free);

        rdi_ice_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.iceAmount = 100;
                }
            }
        });

        rdi_ice_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.iceAmount = 75;
                }
            }
        });

        rdi_ice_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.iceAmount = 50;
                }
            }
        });

        rdi_ice_25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.iceAmount = 25;
                }
            }
        });

        rdi_ice_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    Common.iceAmount = 0;
                }
            }
        });

        RecyclerView recycler_toppings = itemView.findViewById(R.id.recycler_topping);
        recycler_toppings.setLayoutManager(new LinearLayoutManager(context));
        recycler_toppings.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
        recycler_toppings.setAdapter(adapter);

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);

        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);

        builder.setPositiveButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Common.sizeOfCup == -1) {
                    Toast.makeText(context, "Please choose size of cup you need", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Common.sugarAmount == -1)   {
                    Toast.makeText(context, "Please choose amount of sugar to be added", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Common.iceAmount == -1) {
                    Toast.makeText(context, "Please choose amount of ice to be added..", Toast.LENGTH_SHORT).show();
                    return;
                }

                showConfirmDialog(position,txt_count.getNumber());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("LATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showConfirmDialog(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);

        ImageView img_product_dialog = itemView.findViewById(R.id.img_product);

        final TextView txt_product_dialog = itemView.findViewById(R.id.txt_cart_product_name);
        final TextView txt_product_price = itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_sugar = itemView.findViewById(R.id.txt_sugar);
        TextView txt_ice = itemView.findViewById(R.id.txt_ice);
        final TextView txt_topping_extras = itemView.findViewById(R.id.txt_topping_extras);

        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);

        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
                .append(Common.sizeOfCup == 0 ? " Size M" : " Size L")
                        .append(number).toString());

        txt_sugar.setText(new StringBuilder("Sugar : ").append(Common.sugarAmount).append("%"));
        txt_ice.setText(new StringBuilder("Ice : ").append(Common.iceAmount).append("%"));

        double price = (Double.parseDouble(drinkList.get(position).Price) * (Double.parseDouble(number)) + Common.toppingPrice);

        if (Common.sizeOfCup == 1)  {
            price += (3.0 * Double.parseDouble(number));
        }

        StringBuilder topping_final_comment = new StringBuilder("");

        for (String line : Common.toppingAdded) {
            topping_final_comment.append(line).append("\n");
        }

        txt_topping_extras.setText(topping_final_comment);

        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("$").append(finalPrice));

        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                try {
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.ice = Common.iceAmount;
                    cartItem.sugar = Common.sugarAmount;
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.toppingExtras = txt_topping_extras.getText().toString();
                    cartItem.link = drinkList.get(position).Link;

                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("TBC_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Items saved to cart", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(itemView);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
