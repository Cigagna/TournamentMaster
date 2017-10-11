package com.example.alessioc.tournament;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartecipantsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartecipantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartecipantsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int nPartecipants;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText partName;
    private FloatingActionButton confirmPartButton;
    private TableLayout partTable;
    private ArrayList<ArrayList> completeList;

    public PartecipantsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param num Parameter 1.
     * @return A new instance of fragment PartecipantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartecipantsFragment newInstance(int num) {
        PartecipantsFragment fragment = new PartecipantsFragment();
        nPartecipants = num;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partecipants, container, false);

        initPartTable(view);

        completeList=new ArrayList<ArrayList>();
        confirmPartButton = (FloatingActionButton) view.findViewById(R.id.confirm_partecipants_floatingActionButton);
        confirmPartButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    private void initPartTable(View view) {
        partTable = (TableLayout) view.findViewById(R.id.partecipants_table);

        for (int i = 0; i < nPartecipants; i++) {

            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row.setLayoutParams(lp);
            partName = new EditText(getActivity());
            //partName.setWidth(FrameLayout.LayoutParams.FILL_PARENT);
            //partName.setText();
            partName.setHint("Insert name ...");
            row.addView(partName);
            partTable.addView(row, i);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (PartecipantsFragment.OnFragmentInteractionListener) context;
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
        ArrayList nameList=new ArrayList<String>();

        for (int i = 0; i < partTable.getChildCount(); i++) {
            View rowView = partTable.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow row = (TableRow) rowView;
                EditText partName = (EditText) (row.getChildAt(0));
                if (!partName.getText().toString().equals("")) {
                    nameList.add(partName.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Don't leave blanck names", Toast.LENGTH_LONG).show();
                    return;

                }
            }
        }

        completeList.add(nameList);
        ((MainActivity)getActivity()).callSchemeTransaction(completeList);


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
