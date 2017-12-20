package com.sa45team7.sevenbooksapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sa45team7.sevenbooksapp.dummy.DummyContent;
import com.sa45team7.sevenbooksapp.model.Book;

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
            mBook = DummyContent.BOOK_MAP.get(getArguments().getInt(ARG_ITEM_ID));

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
        }

        return rootView;
    }
}
