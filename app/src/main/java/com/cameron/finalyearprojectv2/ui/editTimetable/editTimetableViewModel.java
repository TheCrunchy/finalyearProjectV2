package com.cameron.finalyearprojectv2.ui.editTimetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class editTimetableViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public editTimetableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is weekly fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}