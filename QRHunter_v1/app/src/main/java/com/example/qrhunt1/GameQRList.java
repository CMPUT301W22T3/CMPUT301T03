package com.example.qrhunt1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameQRList extends ArrayAdapter<GameQRCode> {

    private ArrayList<GameQRCode> codes;
    private Context context;


    public GameQRList( Context context, int resource, ArrayList<GameQRCode> codes) {
        super(context,0, codes);
        this.codes = codes;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gallerylist, parent, false);
        }

        GameQRCode code = codes.get(position);


        TextView gameQRCodeScore = view.findViewById(R.id.qr_score);
        TextView gameQRCodeLocation = view.findViewById(R.id.qr_location);
        TextView gameQRCodeComment = view.findViewById(R.id.qr_comment);


        gameQRCodeScore.setText("Score: " + code.getScore());
        gameQRCodeLocation.setText("Location: " + code.getLocation());
        gameQRCodeComment.setText(code.getComments());

        return view;
    }

}
