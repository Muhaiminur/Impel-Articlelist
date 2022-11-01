package com.impel.impelArticle.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.impel.impelArticle.R;
import com.impel.impelArticle.databinding.FragmentArticleDetailsPageBinding;
import com.impel.impelArticle.utils.KeyWord;
import com.impel.impelArticle.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ArticleDetailsPage extends Fragment {
    Context context;
    Utility utility;
    FragmentArticleDetailsPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (binding == null) {
            binding = FragmentArticleDetailsPageBinding.inflate(inflater, container, false);
            try {
                if (getArguments() != null) {
                    context = getActivity();
                    utility = new Utility(context);
                    binding.detailsTittle.setText(getArguments().getString(KeyWord.TITTLE));
                    binding.detailsAuthor.setText(getArguments().getString(KeyWord.AUTHOR));
                    binding.detailsSubtittle.setText(getArguments().getString(KeyWord.SUBTITTLE));
                    binding.detailsDescription.setText(getArguments().getString(KeyWord.DESCRIPTION));
                    Glide.with(context)
                            .load(getArguments().getString(KeyWord.IMAGE))
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default)
                            .into(binding.detailsImage);
                } else {
                    utility.showDialog("Item Not found");
                }
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }


}