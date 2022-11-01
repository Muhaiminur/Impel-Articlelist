package com.impel.impelArticle.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.impel.impelArticle.R;
import com.impel.impelArticle.adapter.HomelistAdapter;
import com.impel.impelArticle.databinding.FragmentArticleListPageBinding;
import com.impel.impelArticle.model.Article;
import com.impel.impelArticle.model.ArticleModel;
import com.impel.impelArticle.network.ApiService;
import com.impel.impelArticle.network.Controller;
import com.impel.impelArticle.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleListPage extends Fragment {

    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    Context context;
    Utility utility;
    HomelistAdapter adapter;
    List<Article> list = new ArrayList<>();
    FragmentArticleListPageBinding binding;

    public ArticleListPage() {
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
            binding = FragmentArticleListPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                get_articlelist();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    public void reccyler() {
        try {
            adapter = new HomelistAdapter(list, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.homeList.setLayoutManager(mLayoutManager);
            binding.homeList.setItemAnimator(new DefaultItemAnimator());
            binding.homeList.setAdapter(adapter);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void get_articlelist() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, context.getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.get_home_articlelist("us", "business", "e2d34b5941fd420fafd3ae98fcdbe409");
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        utility.hideProgress();
                        try {
                            utility.logger(response.toString());
                            if (response.isSuccessful() && response.code() == 200) {
                                utility.logger("article list home " + response.body().toString());
                                ArticleModel model = gson.fromJson(response.body(), ArticleModel.class);
                                if (model.getArticles().size() > 0) {
                                    list.clear();
                                    list.addAll(model.getArticles());
                                    reccyler();
                                }
                            } else if (response.code() == 400) {
                                JSONObject jObj = new JSONObject(response.errorBody().string());
                                JSONObject errorjObj = new JSONObject(jObj.getString("errors"));
                                Iterator<String> keys = errorjObj.keys();
                                String str_Name = keys.next();
                                JSONArray api_data = errorjObj.getJSONArray(str_Name);
                                for (int i = 0; i < api_data.length(); i++) {
                                    utility.logger("abir" + api_data.getString(0));
                                }
                                utility.showDialog(api_data.getString(0));
                            } else {
                                utility.showToast(context.getResources().getString(R.string.something_went_wrong));
                                //utility.showToast(context.getResources().getString(R.string.something_went_wrong));
                            }
                        } catch (Exception e) {
                            utility.hideProgress();
                            Log.d("Failed to hit api", Log.getStackTraceString(e));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.d("On Failure to hit api", t.toString());
                        utility.hideProgress();
                    }
                });
            } else {
                utility.showDialog(context.getResources().getString(R.string.no_internet));
            }
        } catch (Exception e) {
            utility.hideProgress();
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}