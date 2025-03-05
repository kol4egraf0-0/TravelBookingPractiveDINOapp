package com.example.travelbookingapp.Helper;

import android.app.Activity;
import android.content.Intent;
import com.example.travelbookingapp.Activity.ProfileActivity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.example.travelbookingapp.Activity.MainActivity;
import com.example.travelbookingapp.R;
import java.util.HashMap;
import java.util.Map;

public class BottomNavHelper {
    private static final Map<Class<? extends Activity>, Integer> activityToNavItem = new HashMap<>();
    private static final Map<Integer, Class<? extends Activity>> navItemToActivity = new HashMap<>();

    static {
        activityToNavItem.put(MainActivity.class, R.id.explorer);
        activityToNavItem.put(ProfileActivity.class, R.id.profile);

        navItemToActivity.put(R.id.explorer, MainActivity.class);
        navItemToActivity.put(R.id.profile, ProfileActivity.class);
    }

    public static void setupBottomNav(Activity activity, ChipNavigationBar bottomNavigation) {
        Integer selectedItem = activityToNavItem.get(activity.getClass());
        if (selectedItem != null) {
            bottomNavigation.setItemSelected(selectedItem, true);
        }

        bottomNavigation.setOnItemSelectedListener(id -> {
            Class<? extends Activity> targetActivity = navItemToActivity.get(id);
            if (targetActivity != null && !activity.getClass().equals(targetActivity)) {
                Intent intent = new Intent(activity, targetActivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
            }
        });
    }
}