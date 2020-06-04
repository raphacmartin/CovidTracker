package com.unilago.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.unilago.covidtracker.ui.SelecionarEstadoFragment;

public class MainActivity extends AppCompatActivity {

    private static AppCompatActivity navigationController;
    private static Context context;
    private static final Fragment HOME_FRAGMENT = new SelecionarEstadoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationController = this;
        context = getApplicationContext();

        setFragment(HOME_FRAGMENT);
    }

    public static void setFragment(Fragment fragment) {
        setFragment(fragment, new Bundle());
    }

    public static void setFragment(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        FragmentManager fragmentManager = navigationController.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = navigationController.getSupportFragmentManager();
        Fragment home_fragment = fragmentManager.findFragmentByTag(HOME_FRAGMENT.getClass().getName());
        if (home_fragment != null && home_fragment.isVisible()) {
            showToast("Você já está ta tela inicial");
            return;
        }
        super.onBackPressed();
    }

    public static void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
