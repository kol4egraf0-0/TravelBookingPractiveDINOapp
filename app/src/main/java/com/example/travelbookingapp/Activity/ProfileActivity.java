package com.example.travelbookingapp.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelbookingapp.Helper.BottomNavHelper;
import com.example.travelbookingapp.R;
import com.example.travelbookingapp.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ProfileActivity extends BaseActivity {
    private ActivityProfileBinding binding;
    private FirebaseAuth auth;
    ChipNavigationBar bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            binding.emailInput.setText(user.getEmail());
            binding.nameInput.setText(user.getDisplayName() != null ? user.getDisplayName() : "Михаил");
        }
        bottomNavigation = findViewById(R.id.chipNav);
        BottomNavHelper.setupBottomNav(this, bottomNavigation);
    }
}
