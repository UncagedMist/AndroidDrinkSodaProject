package tbc.techbytecare.kk.androiddrinksodaproject.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;

public interface IFavouriteDataSource {

    Flowable<List<Favourite>> getFavItems();

    int isFavourite(int itemId);

    void insertFav(Favourite... favourites);

    void delete(Favourite favourite);

}
