package com.example.alessioc.tournament;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WinnerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WinnerFragment extends Fragment implements View.OnClickListener {
    private static String winner;

    private OnFragmentInteractionListener mListener;

    public WinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WinnerFragment newInstance(String name) {
        WinnerFragment fragment = new WinnerFragment();
        Bundle args = new Bundle();
        winner=name;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_winner, container, false);
        TextView winnerTextView=view.findViewById(R.id.winner_textView);
        winnerTextView.setText(winner);
        FloatingActionButton newTournament=view.findViewById(R.id.new_tournament_floatingActionButton);
        newTournament.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view ;
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
        MainActivity.turn=1;
        ((MainActivity)getActivity()).callInitialFragment();
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
