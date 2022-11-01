package com.impel.impelArticle.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.impel.impelArticle.databinding.ActivityMainBinding;
import com.impel.impelArticle.utils.Utility;
import com.impel.impelArticle.view.fragment.ArticleListPage;
import com.impel.impelArticle.view.fragment.BrowsePage;
import com.impel.impelArticle.view.fragment.NewsPage;

public class Homepage extends AppCompatActivity {

    ActivityMainBinding binding;
    Context context;
    Utility utility;
    // array for tab labels
    private final String[] tablabels = new String[]{"News", "Bookmark"};
    ViewPagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            context = Homepage.this;
            utility = new Utility(context);
            // call function to initialize views
            init();
            // bind and set tabLayout to viewPager2 and set labels for every tab
            new TabLayoutMediator(binding.tabLayout, binding.viewPager2, (tab, position) -> tab.setText(tablabels[position])).attach();
            // set default position to 1 instead of default 0
            //binding.viewPager2.setCurrentItem(1, false);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void init() {
        adapter = new ViewPagerFragmentAdapter(Homepage.this);
        // set adapter to viewPager2
        binding.viewPager2.setAdapter(adapter);
    }

    // create adapter to attach fragments to viewpager2 using FragmentStateAdapter
    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {
        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        // return fragments at every position
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new NewsPage(); // article list fragment
                case 1:
                    return new BrowsePage(); // browse fragment
            }
            return new ArticleListPage(); //article fragment
        }

        // return total number of tabs in our case we have 3
        @Override
        public int getItemCount() {
            return tablabels.length;
        }
    }
}