package com.example.alessioc.tournament;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InitialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InitialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InitialFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText numPartText;
    private FloatingActionButton createTournButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private NumberPicker numPartPicker;
    private String[] nPartValues;

    public InitialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment InitialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InitialFragment newInstance() {
        InitialFragment fragment = new InitialFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        View view=inflater.inflate(R.layout.fragment_initial, container, false);
        // Inflate the layout for this fragment
        //numPartText = (EditText) view.findViewById(R.id.editText);
        nPartValues=new String[10];
        setNPartValues();

        numPartPicker= (NumberPicker) view.findViewById(R.id.numberPicker);
        numPartPicker.setMaxValue(10);
        numPartPicker.setMinValue(1);

        numPartPicker.setDisplayedValues(nPartValues);

        createTournButton = (FloatingActionButton) view.findViewById(R.id.create_tournament_floatingActionButton);
        createTournButton.setOnClickListener(this);

        /*createTournButton = (Button) view.findViewById(R.id.create_tournament_button);
        createTournButton.setOnClickListener(this);*/
        return view;
    }

    private void setNPartValues() {
        int n=2;
        for (int i=0;i<10;i++) {
            nPartValues[i] = ""+(n);
            n=n*2;
        }
    }

    /*private int powerOfTwo(int n, int power){
        if (n>0){
            power=power*2;
            n--;
            powerOfTwo(n,power);
        }
        return power;
    }*/

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
        /*if (!numPartText.getText().toString().equals("")) {
            switch (view.getId()) {
                case R.id.create_tournament_button:
                    if (verifyNum()) {
                        ((MainActivity)getActivity()).callPartecipantsTransaction(Integer.valueOf(numPartText.getText().toString()));
                    } else {
                        Toast.makeText(getActivity(), "Insert a power of 2 number", Toast.LENGTH_LONG).show();
                    }
                    break;

            }
        }else{
            Toast.makeText(getActivity(), "Insert a number", Toast.LENGTH_LONG).show();

        }*/
        ((MainActivity)getActivity()).callPartecipantsTransaction(Integer.valueOf(nPartValues[numPartPicker.getValue()-1]));
    }

    /*private boolean verifyNum() {
        double input = Integer.valueOf(numPartText.getText().toString());

        while (((input != 2) && input % 2 == 0) || input == 1) {
            input = input / 2;
        }

        return input == 2;

    }
*/
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
