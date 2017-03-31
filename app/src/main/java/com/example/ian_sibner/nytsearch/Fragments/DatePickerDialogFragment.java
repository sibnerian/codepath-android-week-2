package com.example.ian_sibner.nytsearch.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
        GregorianCalendar beginDate = (GregorianCalendar) args.getSerializable("calendar");
        if (beginDate == null) {
            beginDate = new GregorianCalendar();
        }

        int year = beginDate.get(Calendar.YEAR);
        int month = beginDate.get(Calendar.MONTH);
        int day = beginDate.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}