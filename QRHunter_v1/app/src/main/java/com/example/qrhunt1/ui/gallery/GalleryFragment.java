package com.example.qrhunt1.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qrhunt1.GameQRCode;
import com.example.qrhunt1.GameQRList;
import com.example.qrhunt1.R;

import java.util.ArrayList;

public class GalleryFragment extends Fragment{
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


        //addCommentButtom = view.findViewById(R.id.add_comment);
        codeArrayAdapter = new GameQRList(thisContext, codeArrayList);
        codeList.setAdapter(codeArrayAdapter);
        View commentEdit = view.findViewById(R.id.add_comment);

        codeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.fragment_qr_detail, null))
                        // Add action buttons
                        .setTitle("Edit Comment")
                        .setView(new EditText(getActivity()))
                        .setPositiveButton(R.string.ok, null)
                        .setNegativeButton(R.string.cancel, null);
                // Create the AlertDialog object and return it
                builder.create().show();
                return false;
            }

        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
