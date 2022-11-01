package com.impel.impelArticle.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.impel.impelArticle.R;
import com.impel.impelArticle.databinding.RecyclerHomearticleBinding;
import com.impel.impelArticle.model.Article;
import com.impel.impelArticle.utils.KeyWord;
import com.impel.impelArticle.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomelistAdapter extends RecyclerView.Adapter<HomelistAdapter.Todo_View_Holder> {
    Context context;
    List<Article> list;
    Utility utility;

    public HomelistAdapter(List<Article> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerHomearticleBinding binding;

        public Todo_View_Holder(RecyclerHomearticleBinding view) {
            super(view.getRoot());
            this.binding = view;
        }

        public void bind(Article s) {
            //historyBinding.setHistory(deliveryModel);
            binding.executePendingBindings();
        }
    }

    @Override
    public HomelistAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerHomearticleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_homearticle, parent, false);
        return new HomelistAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final HomelistAdapter.Todo_View_Holder holder, int position) {
        final Article bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);

            Glide.with(context)
                    .load(bodyResponse.getUrlToImage())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(holder.binding.homeitemImage);
            holder.binding.homeitemTittle.setText(bodyResponse.getAuthor());
            holder.binding.homeitemDescription.setText(bodyResponse.getTitle());
            holder.binding.homeitemDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(holder.binding.getRoot());
                    if (navController != null) {
                        Bundle args = new Bundle();
                        args.putString(KeyWord.IMAGE, bodyResponse.getUrlToImage());
                        args.putString(KeyWord.TITTLE, bodyResponse.getSource().getName());
                        args.putString(KeyWord.SUBTITTLE, bodyResponse.getTitle());
                        args.putString(KeyWord.AUTHOR, bodyResponse.getAuthor() + ", " + gettime(bodyResponse.getPublishedAt()));
                        args.putString(KeyWord.DESCRIPTION, bodyResponse.getContent());
                        navController.navigate(R.id.action_articlelist_fragment_page_to_articledetails_fragment_page, args);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public String gettime(String s) {
        String re = "Listed " + 0 + "days Ago";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(s);
            //te date2 = simpleDateFormat.parse("2019-07-18 19:23:20.0");
            Date endDate = Calendar.getInstance().getTime();
            //milliseconds
            long different = endDate.getTime() - startDate.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long elapsedDays = different / daysInMilli;
            re = "Listed " + elapsedDays + " days Ago";
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return re;
    }
}