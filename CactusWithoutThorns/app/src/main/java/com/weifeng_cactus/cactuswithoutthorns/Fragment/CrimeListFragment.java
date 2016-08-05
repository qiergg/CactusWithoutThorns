package com.weifeng_cactus.cactuswithoutthorns.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.activity.imp.CrimePagerActivity;
import com.weifeng_cactus.cactuswithoutthorns.bean.Crime;
import com.weifeng_cactus.cactuswithoutthorns.model.CrimeLab;

import java.util.ArrayList;

/**
 * Created by maiya on 16/8/4.
 */
public class CrimeListFragment extends ListFragment {
    private static final int REQUEST_CRIME=1;
    private ArrayList<Crime> mCrimes;
    private static final String TAG = "CrimeListFragment";

    //    public static final String EXTRA_CRIME_ID="com.bignerdranch.android.criminalintent.crime_id";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crime_title);
//        UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
//        ArrayAdapter<Crime> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mCrimes);
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        Crime c = (Crime) (getListAdapter()).getItem(position);
        Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
//        Log.d(TAG, c.getTitle() + "was clicked");
//        Intent i = new Intent(getActivity(), CrimeActivity.class);
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
//        startActivityForResult(i,REQUEST_CRIME);
        startActivity(i);
    }


    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }
            Crime c = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());


            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CRIME){

        }
    }
}
