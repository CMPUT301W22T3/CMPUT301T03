package com.example.qrhunt1.ui.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrhunt1.R;


public class SignupFragment extends Fragment {

    CallbackFragment callbackFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup,container,false);

        EditText su_username = root.findViewById(R.id.input_su_username);
        EditText su_password = root.findViewById(R.id.input_su_password);
        Button signup_btn = root.findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (callbackFragment != null){
                callbackFragment.toLoginFragment();

            }
        });

        return root;

    }

    public void setCallbackFragment(CallbackFragment callbackFragment){
        this.callbackFragment = callbackFragment;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}