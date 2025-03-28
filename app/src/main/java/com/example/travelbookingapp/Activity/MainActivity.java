package com.example.travelbookingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelbookingapp.Adapter.PopularAdapter;
import com.example.travelbookingapp.Adapter.RecommendedAdapter;
import com.example.travelbookingapp.Domain.ItemDomain;
import com.example.travelbookingapp.Domain.Location;
import com.example.travelbookingapp.Helper.BottomNavHelper;
import com.example.travelbookingapp.R;
import com.example.travelbookingapp.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    ChipNavigationBar bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initLocation();
        initRec();
        initPopular();

        bottomNavigation = findViewById(R.id.chipNav);
        BottomNavHelper.setupBottomNav(this, bottomNavigation);
    }

    private void initPopular() {
        DatabaseReference myRef = database.getReference("Item");
        binding.progressBar5.setVisibility(View.VISIBLE);

        ArrayList<ItemDomain> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(ItemDomain.class));
                    }
                }
                if(!list.isEmpty())
                {
                    binding.recyclerViewPopular.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.HORIZONTAL, false));
                    RecyclerView.Adapter adapter = new PopularAdapter(list);
                    binding.recyclerViewPopular.setAdapter(adapter);

                }
                binding.progressBar5.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initRec() {
        DatabaseReference myRef = database.getReference("Recommended");
        binding.progressBar4.setVisibility(View.VISIBLE);

        ArrayList<ItemDomain> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot issue:snapshot.getChildren()){
                        list.add(issue.getValue(ItemDomain.class));
                    }
                }
                if(!list.isEmpty())
                {
                    binding.recyclerViewRecommended.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL, false));
                    RecyclerView.Adapter adapter = new RecommendedAdapter(list);
                    binding.recyclerViewRecommended.setAdapter(adapter);
                }
                binding.progressBar4.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initLocation() {
        DatabaseReference ref = database.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot issue:snapshot.getChildren())
                    {
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}