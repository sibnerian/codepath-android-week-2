package com.example.ian_sibner.nytsearch;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ian_sibner.nytsearch.Fragments.FiltersDialogFragment;
import com.example.ian_sibner.nytsearch.api.NytSearchApi;
import com.example.ian_sibner.nytsearch.models.Filters;
import com.example.ian_sibner.nytsearch.models.NytSearchApiResponse;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText etSearchInput;
    GridView gvResults;
    Button btnSearch;
    Filters filters;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        filters = new Filters("newest", new HashSet<String>(), null, "");
        setupViews();
    }

    public void onArticleSearch() {
        filters.setQuery( this.etSearchInput.getText().toString());

        NytSearchApi.search(filters, new Callback<NytSearchApiResponse>() {
            @Override
            public void onResponse(Call<NytSearchApiResponse> call,
                                   Response<NytSearchApiResponse> response) {
                NytSearchApiResponse body = response.body();
                Toast.makeText(getApplicationContext(),
                        "Cool beans.",
                        Toast.LENGTH_LONG
                ).show();
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

    public void onArticleSearch(View view) {
        onArticleSearch();
    }

    private void setupViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.etSearchInput = (EditText) findViewById(R.id.etSearchInput);
//        this.gvResults = (GridView) findViewById(R.id.gvResults);
        this.btnSearch = (Button) findViewById(R.id.btnSearch);
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
                onArticleSearch();
            }
        });
        frag.show(fm, "fragment_edit_filters");
    }
}
