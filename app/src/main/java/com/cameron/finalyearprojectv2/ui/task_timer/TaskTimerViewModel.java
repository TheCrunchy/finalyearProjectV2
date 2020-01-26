package com.cameron.finalyearprojectv2.ui.task_timer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskTimerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TaskTimerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is task timer fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}