package com.infinite.task.views;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.infinite.task.viewmodels.MainViewModel;


public class MainViewModelFactory implements ViewModelProvider.Factory {


    public MainViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
