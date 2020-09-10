package com.infinite.task.viewmodels;

import android.arch.lifecycle.MutableLiveData;

import com.infinite.task.base.BaseViewModel;
import com.infinite.task.model.GraphData;
import com.infinite.task.services.JsonParsingFromRaw;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<GraphData>> movies;
    private MutableLiveData<Boolean> isLoading;

    private JsonParsingFromRaw jsonParsingFromRaw;

    public MainViewModel() {
        movies = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<List<GraphData>> getMovies() {
        return movies;
    }

    public MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }


    public void loadMovieLocal() {
        setIsLoading(true);

        setMovies(createLocalMovieList());
    }

    private List<GraphData> createLocalMovieList() {

        List<GraphData> graphData = new ArrayList<>();

        jsonParsingFromRaw = new JsonParsingFromRaw();


        try {
            JSONObject jsonObject = new JSONObject(jsonParsingFromRaw.readJsonRawFile());
            JSONArray jsonArray = jsonObject.getJSONArray("graphdata");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONArray jsonArray1 = jsonObject1.getJSONArray("days");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    graphData.add(new GraphData(jsonObject2.getString("day"),
                            Float.parseFloat(jsonObject2.getString("value")),
                            jsonObject2.getString("description")));
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return graphData;
    }


    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }

    private void setMovies(List<GraphData> movies) {
        setIsLoading(false);
        this.movies.postValue(movies);
    }

}
