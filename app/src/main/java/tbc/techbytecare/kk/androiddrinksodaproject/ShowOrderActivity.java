package tbc.techbytecare.kk.androiddrinksodaproject;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tbc.techbytecare.kk.androiddrinksodaproject.Adapter.OrderAdapter;
import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Order;
import tbc.techbytecare.kk.androiddrinksodaproject.Retrofit.IDrinkShopAPI;

public class ShowOrderActivity extends AppCompatActivity {

    IDrinkShopAPI mService;
    RecyclerView recyclerView;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        mService = Common.getAPI();

        recyclerView = findViewById(R.id.recycler_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.order_new) {
                    loadAllOrders("0");
                }
                else if (item.getItemId() == R.id.order_cancel) {
                    loadAllOrders("-1");
                }
                else if (item.getItemId() == R.id.order_processing) {
                    loadAllOrders("1");
                }
                else if (item.getItemId() == R.id.order_shipping) {
                    loadAllOrders("2");
                }
                else if (item.getItemId() == R.id.order_shipped) {
                    loadAllOrders("3");
                }
                return true;
            }
        });
        loadAllOrders("0");
    }

    private void loadAllOrders(String statusCode) {

        if (Common.currentUser != null) {

            compositeDisposable.add(mService.getOrder(
                    Common.currentUser.getPhone(),
                    statusCode
            ).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<List<Order>>() {
                        @Override
                        public void accept(List<Order> orders) throws Exception {
                            displayOrders(orders);
                        }
                    }));
        }
        else    {
            Toast.makeText(this, "Please Login again...", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayOrders(List<Order> orders) {
        OrderAdapter adapter = new OrderAdapter(this,orders);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllOrders("0");
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
