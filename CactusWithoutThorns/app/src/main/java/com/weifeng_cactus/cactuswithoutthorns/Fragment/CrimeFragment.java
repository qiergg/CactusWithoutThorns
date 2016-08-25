package com.weifeng_cactus.cactuswithoutthorns.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.weifeng_cactus.cactuswithoutthorns.R;
import com.weifeng_cactus.cactuswithoutthorns.bean.Crime;
import com.weifeng_cactus.cactuswithoutthorns.model.CrimeLab;

import java.util.Date;
import java.util.UUID;

/**
 * Created by maiya on 16/8/4.
 */
public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private EditText mTitleField;
    private Button mDataButton;
    private CheckBox mSolvedCheckBox;
    private Crime mCrime;
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "date";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mDataButton = (Button) v.findViewById(R.id.crime_date);
        Date date = mCrime.getDate();
        CharSequence format = DateFormat.format("yyyy 年 MM 月 dd 日 hh:mm:dd", date);

//        mDataButton.setText(format);
//        mDataButton.setEnabled(false);
        updateDate();
        mDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
//                DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
                
            }
        });
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public void returnResult() {

        getActivity().setResult(Activity.RESULT_OK, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=Activity.RESULT_OK) return;
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            CharSequence format = DateFormat.format("yyyy-MM-dd", date);
            Log.d("maiya",format+"");
            updateDate();
        }
    }

    public void updateDate() {
        Date date = mCrime.getDate();
        CharSequence format = DateFormat.format("yyyy 年 MM 月 dd 日", date);
        mDataButton.setText(format);
    }
}
