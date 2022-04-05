package com.example.qrhunt1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qrhunt1.ui.gallery.GalleryFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GameQRList extends ArrayAdapter<GameQRCode> {

    private ArrayList<GameQRCode> codes;
    private Context context;
    FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     *
     * @param context
     * @param codes
     */
    public GameQRList(Context context, ArrayList<GameQRCode> codes) {
        super(context,0, codes);
        this.codes = codes;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        db = FirebaseFirestore.getInstance();
        String currentUser = mAuth.getCurrentUser().getEmail();
        final CollectionReference dbQR = db.collection("users/").document(currentUser).collection("QR/");

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_gallerylist, null);
            ImageView delete = convertView.findViewById(R.id.delete);

            // Listeners for duplicating and removing an item.
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GalleryFragment.deleteItem(position);

                }
            });

        }

        GameQRCode code = codes.get(position);

        TextView gameQRCodeScore = convertView.findViewById(R.id.qr_score);
        TextView gameQRCodeLocation = convertView.findViewById(R.id.qr_location);


        gameQRCodeScore.setText("Score: " + code.getScore());
        //"["+snapshot.getGeoPoint("Location").getLatitude()+", "+snapshot.getGeoPoint("Location").getLongitude()+"]"
        gameQRCodeLocation.setText("Location: [" + code.getLocation().getLatitude()+", "+ code.getLocation().getLongitude()+"]");

        return convertView;
    }

}
