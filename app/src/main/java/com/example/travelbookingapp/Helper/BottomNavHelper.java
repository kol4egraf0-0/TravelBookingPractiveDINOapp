package com.example.travelbookingapp.Helper;

import android.app.Activity;
import android.content.Intent;

import com.example.travelbookingapp.Activity.ProfileActivity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.example.travelbookingapp.Activity.MainActivity;
import com.example.travelbookingapp.R;

public class BottomNavHelper {
    public static void setupBottomNav(Activity activity, ChipNavigationBar bottomNavigation) {
        bottomNavigation.setItemSelected(R.id.explorer, true);

        bottomNavigation.setOnItemSelectedListener(id -> {
            Intent intent = null;

            if (id == R.id.explorer) {
                if (!(activity instanceof MainActivity)) {
                    intent = new Intent(activity, MainActivity.class);
                }
            } else if (id == R.id.profile) {
                if (!(activity instanceof ProfileActivity)) {
                    intent = new Intent(activity, ProfileActivity.class);
                }
            }
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
            }
        });
    }
}
