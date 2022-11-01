package com.impel.impelArticle.view.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.impel.impelArticle.R;
import com.impel.impelArticle.databinding.ActivityMainBinding;
import com.impel.impelArticle.databinding.FragmentArticleListPageBinding;
import com.impel.impelArticle.databinding.FragmentBrowsePageBinding;
import com.impel.impelArticle.utils.Utility;

public class BrowsePage extends Fragment {

    FragmentBrowsePageBinding binding;
    Context context;
    Utility utility;
    public static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");

    public BrowsePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentBrowsePageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
                final String[] HISTORY_PROJECTION = new String[]{
                        "_id", // 0
                        "url", // 1
                        "visits", // 2
                        "date", // 3
                        "bookmark", // 4
                        "title", // 5
                        "favicon", // 6
                        "thumbnail", // 7
                        "touch_icon", // 8
                        "user_entered", // 9
                };
                utility.logger("paisi"+BOOKMARKS_URI.toString());
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }
}