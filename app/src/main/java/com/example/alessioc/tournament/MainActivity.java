package com.example.alessioc.tournament;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.icu.util.VersionInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InitialFragment.OnFragmentInteractionListener, PartecipantsFragment.OnFragmentInteractionListener, SchemeFragment.OnFragmentInteractionListener, WinnerFragment.OnFragmentInteractionListener {

    private EditText numPartText;
    public static int turn = 1;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private InitialFragment initialFragment;
    private PartecipantsFragment partecipantsFragment;
    private SchemeFragment schemeFragment;
    private AlertDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //myToolbar.addView(findViewById(R.id.appbar_menu));
        setSupportActionBar(myToolbar);

        View v = findViewById(R.id.fragment_container);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        initialFragment = new InitialFragment();
        fragmentTransaction.add(R.id.fragment_container, initialFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void callPartecipantsTransaction(int num) {

        fragmentTransaction = fragmentManager.beginTransaction();

        PartecipantsFragment partecipantsFragment = PartecipantsFragment.newInstance(num);

        fragmentTransaction.replace(R.id.fragment_container, partecipantsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void callSchemeTransaction(ArrayList<ArrayList> nameList) {

        fragmentTransaction = fragmentManager.beginTransaction();

        schemeFragment = SchemeFragment.newInstance(nameList);

        fragmentTransaction.replace(R.id.fragment_container, schemeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void callWinnerTransaction(String winner) {

        fragmentTransaction = fragmentManager.beginTransaction();

        WinnerFragment winnerFragment = WinnerFragment.newInstance(winner);

        fragmentTransaction.replace(R.id.fragment_container, winnerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void callInitialFragment() {

        fragmentTransaction = fragmentManager.beginTransaction();

        initialFragment = new InitialFragment();

        fragmentTransaction.replace(R.id.fragment_container, initialFragment);
        fragmentTransaction.addToBackStack(null);


        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                showAppInfo();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showAppInfo() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String message =
                    String.format("Author: Alessio Cigagna\nVersion: %1$s ",pInfo.versionName);

            String title = getString(R.string.info_title) +" "+ getString(R.string.app_name);

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(message)
                    .setTitle(title)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the positive button event back to the host activity
                            dialog.dismiss();                        }
                    });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
