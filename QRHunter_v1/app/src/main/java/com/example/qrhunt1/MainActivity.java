package com.example.qrhunt1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qrhunt1.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_gallery, R.id.navigation_map, R.id.navigation_players)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //  My profile
        setContentView(R.layout.fragment_my_profile);
        TextView text1 = findViewById(R.id.textView);
        TextView text2 = findViewById(R.id.textView2);
        TextView text10 = findViewById(R.id.textView10);
        TextView text11 = findViewById(R.id.textView11);
        TextView text12 = findViewById(R.id.textView12);
        TextView text13 = findViewById(R.id.textView13);
        TextView text14 = findViewById(R.id.textView14);
        TextView text15 = findViewById(R.id.textView15);
        TextView text16 = findViewById(R.id.textView16);
        Button button = findViewById(R.id.button);


//  Input username
        String user = "Username: John";
        text1.setText(user);

//  Input Contact info
        String phoneNumber = "Address: Tree Street";
        text2.setText(phoneNumber);

//  Highest QR code
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(10);
        list.add(3);
        list.add(11);
        list.add(2);
        Collections.sort(list);
        Integer a = list.get(list.size()-1);
        String d = Integer.toString(a);
        text10.setText(d);

//  lowest QR code
        Collections.reverse(list);
        String b = list.get(list.size()-1).toString();
        text11.setText(b);

//  Sum of Scores #
        int i;
        int sum=0;
        for (i = 0; i < list.size(); i++)
            sum += list.get(i);
        String c = Integer.toString(sum);
        text12.setText(c);

//  Total number of QR codes
        Integer num = list.size();
        String string = Integer.toString(num);
        text13.setText(string);

//  Ranking in Best QR
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(11);
        list1.add(5);
        list1.add(18);
        list1.add(5);
        list1.add(15);
        Collections.sort(list1);
        Collections.reverse(list1);
        Integer index = list1.indexOf(11)+1;
        String e = Integer.toString(index);
        text14.setText(e);

//  Ranking in Total QRs
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(5);
        list2.add(2);
        list2.add(2);
        list2.add(15);
        list2.add(0);
        Collections.sort(list2);
        Collections.reverse(list2);
        Integer index1 = list2.indexOf(5)+1;
        String f = Integer.toString(index1);
        text15.setText(f);

//  Ranking in Total Score
        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(27);
        list3.add(78);
        list3.add(25);
        list3.add(67);
        list3.add(60);
        Collections.sort(list3);
        Collections.reverse(list3);
        Integer index2 = list3.indexOf(27)+1;
        String g = Integer.toString(index2);
        text16.setText(g);

//  Login QR button
        ImageView QR1 = findViewById(R.id.imageView);
        ImageView QR2 = findViewById(R.id.imageView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QR1.setVisibility(View.INVISIBLE);
                QR2.setVisibility(View.VISIBLE);
            }
        });


    }



}