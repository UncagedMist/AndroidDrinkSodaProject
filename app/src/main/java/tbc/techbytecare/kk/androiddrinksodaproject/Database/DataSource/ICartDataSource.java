package tbc.techbytecare.kk.androiddrinksodaproject.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;

public interface ICartDataSource {

    Flowable<List<Cart>> getCartItems();

    Flowable<List<Cart>> getCartItemById(int cartItemId);

    int countCartItems();

    void emptyCart();

    void insertToCart(Cart...carts);

    void updateCart(Cart...carts);

    void deleteCartItem(Cart cart);
}
