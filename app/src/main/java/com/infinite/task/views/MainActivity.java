package com.infinite.task.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.infinite.task.R;
import com.infinite.task.base.BaseActivity;
import com.infinite.task.model.GraphData;
import com.infinite.task.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity<MainViewModel> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private LineChart lineChart;

    private DayAdapter dayAdapter;
    LinearLayoutManager horizontalLayout;
    int review_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        lineChart = findViewById(R.id.lineChart);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setDescriptionColor(Color.WHITE);


        dayAdapter = new DayAdapter(this);
        horizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(dayAdapter);
//        dayAdapter.setCurrentPosition(0);

        // add pager behavior
//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //Dragging
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    review_position = horizontalLayout.findFirstVisibleItemPosition();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int firstVisibleItem = horizontalLayout.findFirstVisibleItemPosition();
//                 Log.e ("VisibleItem", String.valueOf(firstVisibleItem));
//                 dayAdapter.setCurrentPosition((int)(firstVisibleItem*1.6));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getLoadingStatus().observe(this, new LoadingObserver());
        viewModel.getMovies().observe(this, new MovieObserver());
        viewModel.loadMovieLocal();
    }

    private void initLineChart(List<String> xVals, List<List<Float>> yValus, String colorCode) {
        try {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            List<Entry> yVals;
            LineDataSet[] sets = new LineDataSet[yValus.size()];

            for (int i = 0; i < yValus.size(); i++) {
                yVals = setYAxisValues(yValus.get(i)); // create a dataset and give it a type
                sets[i] = new LineDataSet(yVals, "");
                sets[i].setFillAlpha(110);
                sets[i].setColor(Color.parseColor(colorCode));
                sets[i].setCircleColor(Color.parseColor(colorCode));
                sets[i].setLineWidth(1f);
                sets[i].setCircleRadius(3f);
                sets[i].setDrawCircleHole(false);
                sets[i].setValueTextSize(9f);
                sets[i].setDrawFilled(false);
                sets[i].setDrawCubic(true);
                sets[i].setDrawCircles(false);
                sets[i].setDrawValues(false);
                dataSets.add(sets[i]);
            }

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            lineChart.setData(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Entry> setYAxisValues(List<Float> values) {
        List<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < values.size(); i++) {
            yVals.add(new Entry(values.get(i), i));
        }
        return yVals;
    }


    @NonNull
    @Override
    protected MainViewModel createViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory();
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }


    //Observer
    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;

            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class MovieObserver implements Observer<List<GraphData>> {

        @Override
        public void onChanged(@Nullable List<GraphData> graphData) {
            if (graphData == null) return;
            List<String> xVals = new ArrayList<>();
            List<GraphData> list = new ArrayList<>();
            List<List<Float>> listvalues = new ArrayList<>();
            List<Float> yVals = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                xVals.add(graphData.get(i).getTitle());
                list.add(graphData.get(i));
            }
            dayAdapter.setItems(list);

            int flag = 0;
            for (int i = 0; i < graphData.size(); i++) {
                flag++;
                yVals.add(graphData.get(i).getvalue());
                if (flag == 7) {
                    listvalues.add(yVals);
                    yVals = new ArrayList<>();
                    flag = 0;
                }
            }

            initLineChart(xVals, listvalues, "#FF0000");
        }
    }
}
