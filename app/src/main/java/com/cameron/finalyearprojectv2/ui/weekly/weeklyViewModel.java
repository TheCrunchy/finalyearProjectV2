package com.cameron.finalyearprojectv2.ui.weekly;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class weeklyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public weeklyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is weekly fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}