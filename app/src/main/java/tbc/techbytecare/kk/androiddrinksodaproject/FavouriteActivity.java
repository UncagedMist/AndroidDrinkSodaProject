package tbc.techbytecare.kk.androiddrinksodaproject;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tbc.techbytecare.kk.androiddrinksodaproject.Adapter.FavouriteAdapter;
import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Database.ModelDB.Favourite;
import tbc.techbytecare.kk.androiddrinksodaproject.Helper.RecyclerItemTouchHelper;
import tbc.techbytecare.kk.androiddrinksodaproject.Helper.RecyclerItemTouchHelperListener;
import tbc.techbytecare.kk.androiddrinksodaproject.ViewHolder.FavouriteViewHolder;

public class FavouriteActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    RecyclerView recycler_fav;

    RelativeLayout rootLayout;

    CompositeDisposable compositeDisposable;

    FavouriteAdapter adapter;

    List<Favourite> localFavourite = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        compositeDisposable = new CompositeDisposable();

        rootLayout = findViewById(R.id.rootLayout);

        recycler_fav = findViewById(R.id.recycler_fav);
        recycler_fav.setLayoutManager(new LinearLayoutManager(this));
        recycler_fav.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_fav);

        loadFavouriteListItem();
    }

    private void loadFavouriteListItem() {
        compositeDisposable.add(Common.favouriteRepository.getFavItems()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Consumer<List<Favourite>>() {
                @Override
                public void accept(List<Favourite> favourites) throws Exception {
                    displayFavouriteItems(favourites);
                }
            }));
    }

    private void displayFavouriteItems(List<Favourite> favourites) {
        localFavourite = favourites;
        adapter = new FavouriteAdapter(this,favourites);
        recycler_fav.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavouriteListItem();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof FavouriteViewHolder)  {
            String name = localFavourite.get(viewHolder.getAdapterPosition()).name;

            final Favourite deletedItem = localFavourite.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deletedIndex);

            Common.favouriteRepository.delete(deletedItem);

            Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name)
                                    .append(" removed from Favourites").toString(),
                                        Snackbar.LENGTH_LONG);

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deletedItem,deletedIndex);
                    Common.favouriteRepository.insertFav(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
