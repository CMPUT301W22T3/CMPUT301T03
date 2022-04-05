package com.example.qrhunt1.ui.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qrhunt1.R;


public class LoginFragment extends Fragment {

    CallbackFragment callbackFragment;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login,container,false);

        TextView textView = root.findViewById(R.id.tv_login_text);
        ImageView scan_btn = root.findViewById(R.id.iv_login_scan);
        TextView create = root.findViewById(R.id.createnew);
        EditText username = root.findViewById(R.id.input_username);
        EditText password = root.findViewById(R.id.input_password);
        Button login_btn = root.findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (callbackFragment != null){
                //callbackFragment.toSignupFragment();
            }

        });


//        scan_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.setText("Click Button");
//
//            }
//        });

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