package com.example.qrhunt1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class photoList extends ArrayAdapter {

    private ArrayList<Uri> photoList;
    private ArrayList<String> photoUsername;
    private Activity context;

    public photoList(Activity context, ArrayList<Uri> photoList, ArrayList<String> photoUsername){
        super(context, 0, photoUsername);
        this.context = context;
        this.photoUsername = photoUsername;
        this.photoList = photoList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@Nullable ViewGroup parent){
        View view = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.photo_row,parent,false);
        }

        ImageView photoView = view.findViewById(R.id.eachPhoto);
        TextView usernameText = view.findViewById(R.id.photoUsername);

        Uri photo = photoList.get(position);
        String username = photoUsername.get(position);

        photoView.setImageURI(photo);
        usernameText.setText(username);

        return view;
    }

}