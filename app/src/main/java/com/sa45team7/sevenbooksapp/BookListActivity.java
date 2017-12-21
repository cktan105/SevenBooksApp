package com.sa45team7.sevenbooksapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sa45team7.sevenbooksapp.dao.BookDAO;
import com.sa45team7.sevenbooksapp.model.Book;
import com.sa45team7.sevenbooksapp.util.HttpHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Books. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BookDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BookListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private static final String ROOT_URL = "http://10.211.55.5/something/AndroidService.svc/Books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        recyclerView = findViewById(R.id.book_list);

        if (!handleIntent(getIntent())) {
            new GetBooksList(this, recyclerView, mTwoPane).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_all) {
            new GetBooksList(this, recyclerView, mTwoPane).execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private boolean handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY).toLowerCase();

            List<Book> result = new ArrayList<>();
            for (Book book : BookDAO.getInstance().getBooksMap().values()) {
                if (book.getTitle().toLowerCase().contains(query)) {
                    result.add(book);
                }
            }
            recyclerView.setAdapter(new BookAdapter(this, result, mTwoPane));

            return true;
        }
        return false;
    }

    static private class GetBooksList extends AsyncTask<Void, Void, String> {

        WeakReference<RecyclerView> recyclerViewWeakReference;
        WeakReference<BookListActivity> activityWeakReference;
        boolean twoPane;

        GetBooksList(BookListActivity activity, RecyclerView recyclerView, boolean twoPane) {
            this.activityWeakReference = new WeakReference<>(activity);
            this.recyclerViewWeakReference = new WeakReference<>(recyclerView);
            this.twoPane = twoPane;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return HttpHandler.getJsonFromServer(ROOT_URL);
        }

        @Override
        protected void onPostExecute(String json) {
            Gson gson = new Gson();
            List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>() {
            }.getType());
            BookDAO.getInstance().setBooksMap(books);
            if (recyclerViewWeakReference != null) {
                RecyclerView recyclerView = recyclerViewWeakReference.get();
                if (recyclerView != null) {
                    recyclerView.setAdapter(new BookAdapter(activityWeakReference.get(), books, twoPane));
                }
            }
        }

    }

}
