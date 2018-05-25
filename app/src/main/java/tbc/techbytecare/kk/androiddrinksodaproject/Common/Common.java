package tbc.techbytecare.kk.androiddrinksodaproject.Common;

import java.util.ArrayList;
import java.util.List;

import tbc.techbytecare.kk.androiddrinksodaproject.Database.DataSource.CartRepository;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.Local.CartDatabase;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Category;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Drink;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.User;
import tbc.techbytecare.kk.androiddrinksodaproject.Retrofit.IDrinkShopAPI;
import tbc.techbytecare.kk.androiddrinksodaproject.Retrofit.RetrofitClient;

public class Common {

    public static final String BASE_URL = "http://10.0.2.2/drinkshop/";

    public static final String TOPPING_MENU_ID = "7";

    public static User currentUser = null;

    public static Category currentCategory = null;

    public static double toppingPrice = 0.0;
    public static List<String> toppingAdded = new ArrayList<>();

    public static int sizeOfCup = -1;
    public static int sugarAmount = -1;
    public static int iceAmount = -1;

    public static CartDatabase cartDatabase;
    public static CartRepository cartRepository;


    public static List<Drink> toppingList = new ArrayList<>();

    public static IDrinkShopAPI getAPI()    {
        return RetrofitClient.getClient(BASE_URL)
                .create(IDrinkShopAPI.class);
    }
}
