package tbc.techbytecare.kk.androiddrinksodaproject.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;

public class FavouriteRepository implements IFavouriteDataSource {

    private IFavouriteDataSource favouriteDataSource;

    public FavouriteRepository(IFavouriteDataSource favouriteDataSource) {
        this.favouriteDataSource = favouriteDataSource;
    }

    public static FavouriteRepository instance;

    public static FavouriteRepository getInstance(IFavouriteDataSource favouriteDataSource) {

        if (instance == null)   {

            instance = new FavouriteRepository(favouriteDataSource);
        }
        return instance;
    }

    @Override
    public Flowable<List<Favourite>> getFavItems() {
        return favouriteDataSource.getFavItems();
    }

    @Override
    public int isFavourite(int itemId) {
        return favouriteDataSource.isFavourite(itemId);
    }

    @Override
    public void insertFav(Favourite... favourites) {
        favouriteDataSource.insertFav(favourites);
    }

    @Override
    public void delete(Favourite favourite) {
        favouriteDataSource.delete(favourite);
    }
}
