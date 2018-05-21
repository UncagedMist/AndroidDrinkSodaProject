package tbc.techbytecare.kk.androiddrinksodaproject.Retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Banner;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Category;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.CheckUserResponse;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Drink;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.User;

public interface IDrinkShopAPI {

    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("address") String address,
                               @Field("birthdate") String birthdate);

    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>> getDrink(@Field("menuid") String menuID);

    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();
}