package com.sa45team7.sevenbooksapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sa45team7.sevenbooksapp.dao.BookDAO;
import com.sa45team7.sevenbooksapp.model.Book;
import com.sa45team7.sevenbooksapp.util.HttpHandler;

import java.lang.ref.WeakReference;

/**
 * A fragment representing a single Book detail screen.
 * This fragment is either contained in a {@link BookListActivity}
 * in two-pane mode (on tablets) or a {@link BookDetailActivity}
 * on handsets.
 */
public class BookDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private static final String ROOT_URL = "http://10.211.55.5/something/image/";

    /**
     * The dummy content this fragment is presenting.
     */
    private Book mBook;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mBook = BookDAO.getInstance().getBooksMap().get(getArguments().getInt(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mBook.getTitle());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.book_detail, container, false);

        if (mBook != null) {
            ((TextView) rootView.findViewById(R.id.title)).setText(mBook.getTitle());

            String author = "Written by " + mBook.getAuthor();
            ((TextView) rootView.findViewById(R.id.author)).setText(author);

//            ((TextView) rootView.findViewById(R.id.isbn)).setText(mBook.getIsbn());

            String category = "Category: " + mBook.getCategory();
            ((TextView) rootView.findViewById(R.id.category)).setText(category);

            String stock = "Stock: " + String.valueOf(mBook.getStock());
            ((TextView) rootView.findViewById(R.id.stock)).setText(stock);

            String price = "S$ " + String.valueOf(mBook.getPrice());
            ((TextView) rootView.findViewById(R.id.price)).setText(price);

            String url = ROOT_URL + mBook.getIsbn() + ".jpg";
            new GetBookImage(rootView).execute(url);
        }

        return rootView;
    }

    static private class GetBookImage extends AsyncTask<String, Void, Bitmap> {

        WeakReference<View> rootView;

        GetBookImage(View rootView) {
            this.rootView = new WeakReference<>(rootView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            return HttpHandler.getImageFromServer(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (rootView != null) {
                View view = rootView.get();
                if (view != null) {
                    ((ImageView) view.findViewById(R.id.book_image)).setImageBitmap(bitmap);
                }
            }
        }

    }
}
