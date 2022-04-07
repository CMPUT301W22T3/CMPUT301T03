package com.example.qrhunt1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class photoList extends ArrayAdapter<UserPhoto> {

    private ArrayList<UserPhoto> userPhotos;
    private Context context;

    public photoList(Context context, ArrayList<UserPhoto> userPhotos){
        super(context, 0, userPhotos);
        this.context = context;
        this.userPhotos = userPhotos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@Nullable ViewGroup parent){

        View view = convertView;
//        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.photo_row,parent,false);
        }

        //get this photo links to username in userPhotos array list
        UserPhoto userPhoto = userPhotos.get(position);

        //the layouts
        ImageView photoView = view.findViewById(R.id.eachPhoto);
        TextView usernameText = view.findViewById(R.id.photoUsername);

        //get URL and username
        String photoURL = userPhoto.getURL();
        String username = userPhoto.getUserName();

        //set the imageView to the image when there exists one
        if(photoURL!= null){
            Picasso.get().load(photoURL).resize(90,90).centerCrop().into(photoView);
        }
        //set text as the username
        usernameText.setText(username);

        return view;
    }

}