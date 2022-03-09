package com.example.qrhunt1.ui.players;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlayersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Players fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}