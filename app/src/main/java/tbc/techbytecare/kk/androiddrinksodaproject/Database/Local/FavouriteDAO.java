package tbc.techbytecare.kk.androiddrinksodaproject.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;

@Dao
public interface FavouriteDAO {

    @Query("SELECT * FROM Favourite")
    Flowable<List<Favourite>> getFavItems();

    @Query("SELECT EXISTS (SELECT 1 FROM Favourite WHERE id=:itemId)")
    int isFavourite(int itemId);

    @Delete
    void delete(Favourite favourite);
}
