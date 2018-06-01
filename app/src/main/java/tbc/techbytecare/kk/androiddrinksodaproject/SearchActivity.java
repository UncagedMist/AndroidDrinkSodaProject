package tbc.techbytecare.kk.androiddrinksodaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import tbc.techbytecare.kk.androiddrinksodaproject.Adapter.DrinkAdapter;
import tbc.techbytecare.kk.androiddrinksodaproject.Common.Common;
import tbc.techbytecare.kk.androiddrinksodaproject.Model.Drink;
import tbc.techbytecare.kk.androiddrinksodaproject.Retrofit.IDrinkShopAPI;

public class SearchActivity extends AppCompatActivity {

    List<String> suggestList = new ArrayList<>();
    List<Drink> localDataSource = new ArrayList<>();
    MaterialSearchBar searchBar;

    IDrinkShopAPI mService;

    RecyclerView recycler_search;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    DrinkAdapter searchAdapter,adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mService = Common.getAPI();

        recycler_search = findViewById(R.id.recycler_search);
        recycler_search.setLayoutManager(new GridLayoutManager(this,2));

        searchBar = findViewById(R.id.search_bar);
        searchBar.setHint("Enter Your Drink Name");

        loadAllDrinks();

        searchBar.setCardViewElevation(10);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();

                for (String search : suggestList)   {

                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))   {
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)   {
                    recycler_search.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        List<Drink> result = new ArrayList<>();

        for (Drink drink : localDataSource) {

            if (drink.Name.contains(text))  {
                result.add(drink);
            }
        }
        searchAdapter = new DrinkAdapter(this,result);
        recycler_search.setAdapter(searchAdapter);
    }

    private void loadAllDrinks() {
        compositeDisposable.add(mService.getAllDrinks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Drink>>() {
                    @Override
                    public void accept(List<Drink> drinks) throws Exception {
                        displayListDrinks(drinks);
                        buildSuggestList(drinks);
                    }
                }));
    }

    private void buildSuggestList(List<Drink> drinks) {

        for (Drink drink : drinks)  {
            suggestList.add(drink.Name);
        }
        searchBar.setLastSuggestions(suggestList);
    }

    private void displayListDrinks(List<Drink> drinks) {
        localDataSource = drinks;
        adapter = new DrinkAdapter(this,drinks);
        recycler_search.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
