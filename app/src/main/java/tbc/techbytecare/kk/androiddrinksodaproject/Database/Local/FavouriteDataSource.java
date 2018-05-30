package tbc.techbytecare.kk.androiddrinksodaproject.Database.Local;

import java.util.List;

import io.reactivex.Flowable;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.DataSource.IFavouriteDataSource;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;

public class FavouriteDataSource implements IFavouriteDataSource {

    private FavouriteDAO favouriteDAO;
    private static FavouriteDataSource instance;

    public FavouriteDataSource(FavouriteDAO favouriteDAO) {
        this.favouriteDAO = favouriteDAO;
    }

    public static FavouriteDataSource getInstance(FavouriteDAO favouriteDAO)    {

        if (instance == null)   {

            instance = new FavouriteDataSource(favouriteDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Favourite>> getFavItems() {
        return favouriteDAO.getFavItems();
    }

    @Override
    public int isFavourite(int itemId) {
        return favouriteDAO.isFavourite(itemId);
    }

    @Override
    public void insertFav(Favourite... favourites) {
        favouriteDAO.insertFav(favourites);
    }

    @Override
    public void delete(Favourite favourite) {
        favouriteDAO.delete(favourite);
    }
}
