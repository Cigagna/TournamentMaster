package com.example.alessioc.tournament;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements InitialFragment.OnFragmentInteractionListener, PartecipantsFragment.OnFragmentInteractionListener, SchemeFragment.OnFragmentInteractionListener, WinnerFragment.OnFragmentInteractionListener {

    private EditText numPartText;
    public static int turn = 1;
    public static ArrayList<ArrayList> completeList;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private InitialFragment initialFragment;
    private PartecipantsFragment partecipantsFragment;
    private SchemeFragment schemeFragment;
    private AlertDialog dialog;
    private int statementId;
    private Gson gson;
    private String json;

    private SharedPreferences tournamentSaveState;
    SharedPreferences.Editor editor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        //myToolbar.addView(findViewById(R.id.appbar_menu));
        setSupportActionBar(myToolbar);

        tournamentSaveState = getSharedPreferences(getString(R.string.save_state_key), MODE_PRIVATE);

        View v = findViewById(R.id.fragment_container);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        initialFragment = new InitialFragment();
        fragmentTransaction.add(R.id.fragment_container, initialFragment);
        fragmentTransaction.addToBackStack(null);
        statementId = fragmentTransaction.commit();


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

        fragmentManager.popBackStack(statementId, 0);
        fragmentTransaction = fragmentManager.beginTransaction();

        initialFragment = new InitialFragment();
        fragmentTransaction.replace(R.id.fragment_container, initialFragment);
        fragmentTransaction.addToBackStack(null);


        statementId = fragmentTransaction.commit();
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
        gson = new Gson();
        json = null;
        switch (item.getItemId()) {

            case R.id.save_state_action:
                if (completeList != null) {

                    String message = "A precedent save state has been found\n\nAre you sure you want to overwrite it?";
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    saveTournamentState();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(this, "You have to create a tournament first!", Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.load_state_action:
                json = tournamentSaveState.getString("completeList", "");
                if (gson.fromJson(json, ArrayList.class) != null) {

                    String message = "Are you sure you want to load a precedent save state?";
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    loadTournamentState();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(this, "No tournament state found!", Toast.LENGTH_LONG).show();

                }
                return true;

            case R.id.delete_tournament_action:
                if (completeList != null) {
                    String message = "Are you sure you want to delete this tournament?";
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(message)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteTournament();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(this, "You have to create a tournament first!", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.action_about:
                showAppInfo();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadTournamentState() {
        completeList = gson.fromJson(json, ArrayList.class);
        turn = tournamentSaveState.getInt("turn", 1);
        callSchemeTransaction(completeList);

        Toast.makeText(this, "Tournament state loaded.", Toast.LENGTH_SHORT).show();
    }

    private void saveTournamentState() {
        editor = tournamentSaveState.edit();
        json = gson.toJson(completeList);
        editor.putString("completeList", json);
        editor.putInt("turn", turn);
        editor.commit();

        Toast.makeText(getApplication(), "Tournament state saved.", Toast.LENGTH_SHORT).show();
    }

    private void deleteTournament(){
        turn = 1;
        completeList = null;
        callInitialFragment();
        Toast.makeText(getApplication(), "Tournament deleted.", Toast.LENGTH_SHORT).show();
    }

    private void showAppInfo() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String message =
                    String.format("Author: Alessio Cigagna\nVersion: %1$s ", pInfo.versionName);

            String title = getString(R.string.info_title) + " " + getString(R.string.app_name);

            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(message)
                    .setTitle(title)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the positive button event back to the host activity
                            dialog.dismiss();
                        }
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
