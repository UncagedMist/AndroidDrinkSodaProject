package tbc.techbytecare.kk.androiddrinksodaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Adapter.OrderDetailAdapter;
import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;

public class OrderDetailActivity extends AppCompatActivity {

    TextView txt_order_id,txt_order_price,txt_order_address,txt_order_comment,txt_order_status;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        txt_order_id = findViewById(R.id.txt_order_id);
        txt_order_price = findViewById(R.id.txt_order_price);
        txt_order_address = findViewById(R.id.txt_order_address);
        txt_order_comment = findViewById(R.id.txt_order_comment);
        txt_order_status = findViewById(R.id.txt_order_status);

        recyclerView = findViewById(R.id.recycler_order_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        txt_order_id.setText(new StringBuilder("#").append(Common.currentOrder.getOrderId()));
        txt_order_price.setText(new StringBuilder("$ ").append(Common.currentOrder.getOrderPrice()));
        txt_order_address.setText(Common.currentOrder.getOrderAddress());
        txt_order_comment.setText(Common.currentOrder.getOrderComment());
        txt_order_status.setText(new StringBuilder("Order Status : ")
                .append(Common.convertCodeToStatus(Common.currentOrder.getOrderStatus())));

        displayOrderDetail();
    }

    private void displayOrderDetail() {
        List<Cart> orderDetail = new Gson().fromJson(Common.currentOrder.getOrderDetail(),
                new TypeToken<List<Cart>>(){}.getType());

        recyclerView.setAdapter(new OrderDetailAdapter(this,orderDetail));
    }
}
