package com.example.alessioc.tournament;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SchemeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SchemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchemeFragment extends Fragment implements View.OnClickListener {

    private static ArrayList<ArrayList> completeList;

    private OnFragmentInteractionListener mListener;
    private TableLayout schemeTable;
    private TextView partName;
    private TableLayout turnTable;

    private FloatingActionButton nextTurn;
    private RadioGroup radioGroup;
    private ArrayList<String> nameList;


    public SchemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SchemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SchemeFragment newInstance(ArrayList<ArrayList> list) {
        SchemeFragment fragment = new SchemeFragment();

        completeList = list;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.turn==1) {
            ArrayList<String> arrayList = (ArrayList) completeList.get(0);
            Collections.shuffle(arrayList);
            completeList.set(0, arrayList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scheme, container, false);

        initSchemeTable(view);

        nextTurn = (FloatingActionButton) view.findViewById(R.id.next_turn_floatingActionButton);
        nextTurn.setOnClickListener(this);

        return view;
    }


    private void initSchemeTable(View view) {
        schemeTable = (TableLayout) view.findViewById(R.id.scheme_table);
        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        //turnTable.setId(turn);


        for (int k = 0; k < completeList.size(); k++) {
            turnTable = new TableLayout(getActivity());
            turnTable.setGravity(Gravity.CENTER_VERTICAL);
            nameList = ((ArrayList) completeList.get(k));
            int j = 0;
            if (nameList.size() == 1) {
                TableRow partecipantRow = new TableRow(getActivity());
                TableRow.LayoutParams prPar = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                partecipantRow.setLayoutParams(prPar);
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.VERTICAL);

                TextView partName = new TextView(getActivity());
                partName.setText(nameList.get(j++));
                partName.setTextSize(14);
                ll.addView(partName);
                partecipantRow.addView(ll);
                turnTable.addView(partecipantRow);
            } else {
                if (k == MainActivity.turn - 1) {
                    for (int i = 0; i < nameList.size() / 2; i++) {


                        radioGroup = new RadioGroup(getActivity());

                        TableRow partecipantRow = new TableRow(getActivity());
                        TableRow.LayoutParams prPar = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        partecipantRow.setLayoutParams(prPar);
                        LinearLayout ll = new LinearLayout(getActivity());
                        TypedArray array = getContext().getTheme()
                                .obtainStyledAttributes(new int[] {android.R.attr.listDivider});
                        Drawable draw = array.getDrawable(0);
                        ll.setDividerDrawable(draw);
                        ll.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
                        ll.setOrientation(LinearLayout.VERTICAL);


                        RadioButton partName = new RadioButton(getActivity());
                        partName.setText(nameList.get(j++));
                        partName.setTextSize(14);
                        radioGroup.addView(partName);

                        partName = new RadioButton(getActivity());
                        partName.setText(nameList.get(j++));
                        partName.setTextSize(14);
                        radioGroup.addView(partName);

                        ll.addView(radioGroup);
                        partecipantRow.addView(ll);
                        turnTable.addView(partecipantRow, i);
                    }
                } else {
                    for (int i = 0; i < nameList.size() / 2; i++) {

                        TableRow partecipantRow = new TableRow(getActivity());
                        TableRow.LayoutParams prPar = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        partecipantRow.setLayoutParams(prPar);
                        LinearLayout ll = new LinearLayout(getActivity());
                        ll.setOrientation(LinearLayout.VERTICAL);
                        TypedArray array = getContext().getTheme()
                                .obtainStyledAttributes(new int[] {android.R.attr.listDivider});
                        Drawable draw = array.getDrawable(0);
                        ll.setDividerDrawable(draw);
                        ll.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
                        ll.setOrientation(LinearLayout.VERTICAL);

                        TextView partName = new TextView(getActivity());
                        partName.setText(nameList.get(j++));
                        partName.setTextSize(14);

                        ll.addView(partName);

                        partName = new TextView(getActivity());
                        partName.setText(nameList.get(j++));
                        partName.setTextSize(14);
                        ll.addView(partName);

                        partecipantRow.addView(ll);
                        turnTable.addView(partecipantRow);

                    }

                }
            }

            row.addView(turnTable);
        }
        schemeTable.addView(row);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        TableRow turnsRow = (TableRow) schemeTable.getChildAt(0);

        TableLayout lastTurnTable = (TableLayout) turnsRow.getChildAt(MainActivity.turn - 1);

/*
        TableLayout nextTurnTable = new TableLayout(getActivity());
*/
        ArrayList<String> arrayList = new ArrayList<String>();

        int j = 0;


        for (int i = 0; i < lastTurnTable.getChildCount(); i++) {
            TableRow partRow = (TableRow) lastTurnTable.getChildAt(i);
            LinearLayout linearLayout = (LinearLayout) partRow.getChildAt(0);
            RadioGroup matchGroup = (RadioGroup) linearLayout.getChildAt(0);
            RadioButton firstParetecipantRadio = (RadioButton) matchGroup.getChildAt(0);
            RadioButton secondParetecipantRadio = (RadioButton) matchGroup.getChildAt(1);

            if (!firstParetecipantRadio.isChecked() && !secondParetecipantRadio.isChecked()) {
                Toast.makeText(getActivity(), "Not all match results have been defined!", Toast.LENGTH_LONG).show();
                return;
            }

            arrayList.add((String) (firstParetecipantRadio.isChecked() ? firstParetecipantRadio.getText() : secondParetecipantRadio.getText()));
            if (lastTurnTable.getChildCount() == 1) {

                ((MainActivity) getActivity()).callWinnerTransaction((String) (firstParetecipantRadio.isChecked() ? firstParetecipantRadio.getText() : secondParetecipantRadio.getText()));

            }



        }
        if (lastTurnTable.getChildCount() == 1) {
            return;

        } else {

            MainActivity.turn++;
            completeList.add(arrayList);
            ((MainActivity) getActivity()).callSchemeTransaction(completeList);
        }

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
