package com.cameron.finalyearprojectv2.ui.timetableView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class timetableViewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public timetableViewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is daily fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}