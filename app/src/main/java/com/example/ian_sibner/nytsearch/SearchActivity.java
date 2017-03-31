package com.example.ian_sibner.nytsearch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ian_sibner.nytsearch.Fragments.FiltersDialogFragment;
import com.example.ian_sibner.nytsearch.adapters.ArticlesAdapter;
import com.example.ian_sibner.nytsearch.api.NytSearchApi;
import com.example.ian_sibner.nytsearch.decorators.ItemClickSupport;
import com.example.ian_sibner.nytsearch.decorators.SpacesItemsDecorator;
import com.example.ian_sibner.nytsearch.lib.EndlessRecyclerViewScrollListener;
import com.example.ian_sibner.nytsearch.models.Doc;
import com.example.ian_sibner.nytsearch.models.Filters;
import com.example.ian_sibner.nytsearch.models.NytSearchApiResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText etSearchInput;
    Button btnSearch;
    Filters filters;
    Toolbar toolbar;
    ArrayList<Doc> articles = new ArrayList<>();
    ArticlesAdapter articlesAdapter;
    RecyclerView recyclerView;
    EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filters = new Filters("newest", new HashSet<String>(), null, "");
        setupViews();
    }

    public void onArticleSearch(final int page) {
        filters.setQuery( this.etSearchInput.getText().toString());

        // Damn rate limit
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (page != 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                NytSearchApi.search(filters, page, new Callback<NytSearchApiResponse>() {
                    @Override
                    public void onResponse(Call<NytSearchApiResponse> call,
                                           Response<NytSearchApiResponse> response) {
                        NytSearchApiResponse body = response.body();
                        if (body == null) {
                            return;
                        }
                        int curSize = articlesAdapter.getItemCount();
                        List<Doc> newItems = body.getResponse().getDocs();
                        articles.addAll(newItems);
                        articlesAdapter.notifyItemRangeInserted(curSize, newItems.size());
                    }

                    @Override
                    public void onFailure(Call<NytSearchApiResponse> call, Throwable t) {
                        t.printStackTrace();
                        Log.d("failure", t.getMessage());
                        Toast.makeText(getApplicationContext(),
                                "There was a big ol’ network error, partner.",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        });
    }

    private void clearArticles() {
        articles.clear();
        articlesAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    // called when search button pressed
    public void onArticleSearch(View view) {
        clearArticles();
        onArticleSearch(0);
    }

    private void setupViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.etSearchInput = (EditText) findViewById(R.id.etSearchInput);
        this.btnSearch = (Button) findViewById(R.id.btnSearch);
        recyclerView = (RecyclerView) findViewById(R.id.rvArticles);

        articlesAdapter = new ArticlesAdapter(this, articles);
        recyclerView.setAdapter(articlesAdapter);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemsDecorator(16));
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
            new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Log.d("DEBUG", "click" + position);
                }
            }
        );
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                onArticleSearch(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onFilterActionBarItemClick(MenuItem mi) {
        showEditDialog();
    }

    private void showEditDialog() {
        final FragmentManager fm = getSupportFragmentManager();
        final FiltersDialogFragment frag = FiltersDialogFragment.newInstance(filters);
        frag.setEditorFiltersDialogListener(new FiltersDialogFragment.EditFiltersDialogListener() {
            @Override
            public void onFinishedEditFilters(Filters newFilters) {
                frag.dismiss();
                filters = newFilters;
                clearArticles();
                onArticleSearch(0);
            }
        });
        frag.show(fm, "fragment_edit_filters");
    }
}
