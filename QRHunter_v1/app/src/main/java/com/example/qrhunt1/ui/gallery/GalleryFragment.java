package com.example.qrhunt1.ui.gallery;

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

        codeList = view.findViewById(R.id.gallery_list);

        codeArrayList = new ArrayList<>();

        addCommentButtom = view.findViewById(R.id.add_comment);





        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}