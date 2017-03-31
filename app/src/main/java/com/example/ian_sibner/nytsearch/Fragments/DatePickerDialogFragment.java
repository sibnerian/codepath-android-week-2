package com.example.ian_sibner.nytsearch.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by ian_sibner on 3/30/17.
 */

public class DatePickerDialogFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        int year = args.getInt("year");
        int month = args.getInt("month");
        int day = args.getInt("day");

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}