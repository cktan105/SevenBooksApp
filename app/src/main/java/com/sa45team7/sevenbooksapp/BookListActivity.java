package com.sa45team7.sevenbooksapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sa45team7.sevenbooksapp.dummy.DummyContent;
import com.sa45team7.sevenbooksapp.model.Book;

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

    private static final String ROOT_URL = "172.17.253.95/BookShopWCFService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.book_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        new GetBooksList().execute();
    }

    private class GetBooksList extends AsyncTask<Void, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(Void... voids) {
//            String json = HttpHandler.makeServiceCall("");
//            Gson gson = new Gson();
//            List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>() {
//            }.getType());
//            return books;
            return DummyContent.BOOKS;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            RecyclerView recyclerView = findViewById(R.id.book_list);
            recyclerView.setAdapter(new BookAdapter(BookListActivity.this, books, mTwoPane));
        }

    }

}
