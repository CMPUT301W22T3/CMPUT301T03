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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.qrhunt1.GameQRCode;
import com.example.qrhunt1.GameQRList;
import com.example.qrhunt1.R;
import com.example.qrhunt1.Scan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment{
    ListView codeList;
    TextView geoLocation;
    static ArrayAdapter<GameQRCode> codeArrayAdapter;
    static ArrayList<GameQRCode> codeArrayList = new ArrayList<>();;
    FloatingActionButton scanButton;
    static FirebaseFirestore db;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery,container,false);
        Context thisContext = container.getContext();
        codeList = view.findViewById(R.id.gallery_list);

        scanButton = view.findViewById(R.id.fab);

        geoLocation = view.findViewById(R.id.qr_location);
        codeArrayAdapter = new GameQRList(thisContext, codeArrayList);
        codeList.setAdapter(codeArrayAdapter);

        // Access a Cloud Firestore instance from the Fragment
        db = FirebaseFirestore.getInstance();

        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUser = currentUser.replace("@gmail.com", "");
        CollectionReference dbQR = db.collection("users/").document(currentUser).collection("QR/");

        dbQR.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                for (DocumentSnapshot snapshot : snapshotList) {
                    if (snapshot.exists()){
                        //dbQR.document(snapshot.getString("UserName")).collection("QR/");

                        GameQRCode newCode = new GameQRCode(snapshot.getString("Hashcode"));
                        codeArrayList.add(newCode);
                        newCode.setLocation(snapshot.getGeoPoint("Location"));

                    }
                }
                codeArrayAdapter = new GameQRList(thisContext, codeArrayList);
                codeList.setAdapter(codeArrayAdapter);

            }});

        // Edit comment
        codeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();

                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.fragment_qr_detail, null);

                alertDialog.setTitle("Edit Comment");

                EditText commentText = (EditText) dialogView.findViewById(R.id.comment_edit);
                commentText.setText(codeArrayAdapter.getItem(position).getComments(), TextView.BufferType.EDITABLE);
                commentText.setSelection(0, commentText.getText().toString().length());
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    /**
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog the dialog that received the click
                     * @param which  the button that was clicked (ex.
                     *               {@link DialogInterface#BUTTON_POSITIVE}) or the position
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = commentText.getText().toString();
                        codeArrayList.get(position).editComment(comment);
                        codeArrayAdapter.notifyDataSetChanged();
                        Toast.makeText(thisContext,"Done! You edited your comment.",Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * This method will be invoked when a button in the dialog is clicked.
                     *
                     * @param dialog the dialog that received the click
                     * @param which  the button that was clicked
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(dialogView);
                alertDialog.show();
                return false;

            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Scan.class);
                intent.putExtra("mode","hunt");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    // function to delete an item given its index in the list.
    public static void deleteItem(int position) {
        final String TAG = "Sample";
        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUser = currentUser.replace("@gmail.com", "");
        CollectionReference dbQR = db.collection("users/").document(currentUser).collection("QR/");

        String hash = codeArrayList.get(position).getHashcode();
        //System.out.println(hash);

        dbQR.document(hash).delete();

        codeArrayList.remove(position);
        codeArrayAdapter.notifyDataSetChanged();

    }
}