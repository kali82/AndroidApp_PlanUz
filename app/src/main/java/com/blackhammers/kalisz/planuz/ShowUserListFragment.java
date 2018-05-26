package com.blackhammers.kalisz.planuz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowUserListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowUserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowUserListFragment extends Fragment {
    RecyclerView showUserList;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM_USER_LIST = "subjectsList";
    private List<Subjects> subjectsList;


   // private OnFragmentInteractionListener mListener;

    public ShowUserListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowUserListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowUserListFragment newInstance(List<Subjects> subjectsList) {
        ShowUserListFragment fragment = new ShowUserListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER_LIST, (Serializable) subjectsList);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            subjectsList = (List<Subjects>) getArguments().getSerializable(ARG_PARAM_USER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_show_user_list, container, false);
        defineView(view);
        return view;
    }

    private void defineView(View view){
        showUserList=view.findViewById(R.id.show_userL_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        showUserList.setLayoutManager(layoutManager);

        UserListAdapter listAdapter=new UserListAdapter(subjectsList);
        showUserList.setAdapter(listAdapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
