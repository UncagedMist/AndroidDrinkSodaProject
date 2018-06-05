package tbc.techbytecare.kk.androiddrinksodaproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tbc.techbytecare.kk.androiddrinksodaproject.Adapter.CartAdapter;
import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;
import tbc.techbytecare.kk.androiddrinksodaproject.Helper.RecyclerItemTouchHelper;
import tbc.techbytecare.kk.androiddrinksodaproject.Helper.RecyclerItemTouchHelperListener;
import tbc.techbytecare.kk.androiddrinksodaproject.Retrofit.IDrinkShopAPI;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.CartViewHolder;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {


    RecyclerView recycler_cart;
    Button btn_place_order;

    CompositeDisposable compositeDisposable;

    RelativeLayout rootLayout;

    CartAdapter adapter;

    List<Cart> cartList = new ArrayList<>();

    IDrinkShopAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        mService = Common.getAPI();

        rootLayout = findViewById(R.id.rootLayout);

        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        btn_place_order = findViewById(R.id.btn_place_order);

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);
        
        loadCartItems();
    }

    private void placeOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Submit Order");

        View submit_order_layout = LayoutInflater.from(this)
                .inflate(R.layout.submit_order_layout,null);

        final EditText edt_comment = submit_order_layout.findViewById(R.id.edt_comment);
        final EditText edt_other_address = submit_order_layout.findViewById(R.id.edt_other_address);

        final RadioButton rdi_user_address = submit_order_layout.findViewById(R.id.rdi_user_address);
        final RadioButton rdi_other_address = submit_order_layout.findViewById(R.id.rdi_other_address);

        rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    edt_other_address.setEnabled(false);
                }
            }
        });

        rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)  {
                    edt_other_address.setEnabled(true);
                }
            }
        });
        builder.setView(submit_order_layout);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("PLACE ORDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String order_comment = edt_comment.getText().toString();
                final String orderAddress;

                if (rdi_user_address.isChecked())   {
                    orderAddress = Common.currentUser.getAddress();
                }
                else  if (rdi_other_address.isChecked())  {
                    orderAddress = edt_other_address.getText().toString();
                }
                else    {
                    orderAddress = "";
                }

                compositeDisposable.add(
                        Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                            @Override
                            public void accept(List<Cart> carts) throws Exception {

                                if (!TextUtils.isEmpty(orderAddress))   {
                                    submitOrderToServer(
                                            Common.cartRepository.sumPrice(),
                                            carts,
                                            order_comment,
                                            orderAddress);
                                }
                                else    {
                                    Toast.makeText(CartActivity.this, "Some Problem Occurred with Order Address!!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                );
            }
        });
        builder.show();
    }

    private void submitOrderToServer(float sumPrice, List<Cart> carts, String order_comment, String orderAddress) {
        if (carts.size() > 0)   {

            String orderDetail = new Gson().toJson(carts);

            mService.submitOrder(
                    sumPrice,
                    orderDetail,
                    order_comment,
                    orderAddress,
                    Common.currentUser.getPhone())
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(CartActivity.this, "Order Submitted...", Toast.LENGTH_SHORT).show();

                            Common.cartRepository.emptyCart();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("ERROR", t.getMessage());
                        }
                    });
        }
    }


    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItems(carts);
                    }
                }));
    }

    private void displayCartItems(List<Cart> carts) {
        cartList = carts;
        adapter = new CartAdapter(this,carts);
        recycler_cart.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartViewHolder)  {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deletedIndex);

            Common.cartRepository.deleteCartItem(deletedItem);

            Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name)
                            .append(" removed from Cart").toString(),
                    Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deletedItem,deletedIndex);
                    Common.cartRepository.insertToCart(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
