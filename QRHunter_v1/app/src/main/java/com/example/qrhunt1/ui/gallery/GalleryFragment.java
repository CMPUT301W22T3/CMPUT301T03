package com.example.qrhunt1.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrhunt1.GameQRCode;
import com.example.qrhunt1.GameQRList;
import com.example.qrhunt1.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ListView codeList;
    ArrayAdapter<GameQRCode> codeArrayAdapter;
    ArrayList<GameQRCode> codeArrayList;
    Button addCommentButtom;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        Context thisContext = container.getContext();
        codeList = view.findViewById(R.id.gallery_list);

        codeArrayList = new ArrayList<>();
        codeArrayList.add(new GameQRCode("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"));
        codeArrayList.add(new GameQRCode("91e9240f415223982edc345532630710e94a7f52cd5f48f5ee1afc555078f0ab"));
        codeArrayList.add(new GameQRCode("87298cc2f31fba73181ea2a9e6ef10dce21ed95e98bdac9c4e1504ea16f486e4"));
        codeArrayList.get(0).addComment("Hello");
        codeArrayList.get(1).addComment("Hi!");

        //addCommentButtom = view.findViewById(R.id.add_comment);
        codeArrayAdapter = new GameQRList(thisContext, codeArrayList);
        codeList.setAdapter(codeArrayAdapter);

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
