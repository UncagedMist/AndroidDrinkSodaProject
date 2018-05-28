package tbc.techbytecare.kk.androiddrinksodaproject.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Cart;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;

@Database(entities = {Cart.class, Favourite.class},version = 1)
public abstract class TBCRoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    public abstract FavouriteDAO favouriteDAO();

    private static TBCRoomDatabase instance;

    public static TBCRoomDatabase getInstance(Context context) {
        if (instance == null)   {
            instance = Room.databaseBuilder(context,TBCRoomDatabase.class,"TBC_DrinkShopDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
