package com.example.ian_sibner.nytsearch.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ian_sibner.nytsearch.R;
import com.example.ian_sibner.nytsearch.models.Filters;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class FiltersDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialogFragment datePickerDialogFragment;
    private TextView tvPickBeginDate;
    private Spinner spnrSortOrder;
    private CheckBox chkbxArts;
    private CheckBox chkbxFashion;
    private CheckBox chkbxSports;
    private Button btnSave;
    Filters filters;
    private EditFiltersDialogListener listener;

    public FiltersDialogFragment() {
        this.listener =  null;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        filters.beginDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        tvPickBeginDate.setText((monthOfYear + 1) + " / " + dayOfMonth + " / " + year);
        datePickerDialogFragment.dismiss();
    }

    public interface EditFiltersDialogListener {
        void onFinishedEditFilters(Filters filters);
    }

    public void setEditorFiltersDialogListener(EditFiltersDialogListener listener) {
        this.listener = listener;
    }

    public static FiltersDialogFragment newInstance(Filters filters) {
        FiltersDialogFragment fragment = new FiltersDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("filters", Parcels.wrap(filters));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_filters, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        filters = (Filters) Parcels.unwrap(arguments.getParcelable("filters"));

        tvPickBeginDate = (TextView) view.findViewById(R.id.tv_pick_begin_date);
        spnrSortOrder = (Spinner) view.findViewById(R.id.spnr_sort_type);
        chkbxArts = (CheckBox) view.findViewById(R.id.checkbox_arts);
        chkbxFashion = (CheckBox) view.findViewById(R.id.checkbox_fashion);
        chkbxSports = (CheckBox) view.findViewById(R.id.checkbox_sports);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        getDialog().setTitle("Edit Filters");
        setupDatePickerClickListener();
        setupSaveButton();
        // TODO: setup filters

    }

    private void setupDatePickerClickListener() {
        final DatePickerDialog.OnDateSetListener listener = this;
        tvPickBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                datePickerDialogFragment = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("year", filters.beginDate.get(Calendar.YEAR));
                args.putInt("month", filters.beginDate.get(Calendar.MONTH));
                args.putInt("day", filters.beginDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialogFragment.setArguments(args);
                datePickerDialogFragment.setListener(listener);
                datePickerDialogFragment.show(fm, "fragment_select_begin_date");
            }
        });
    }

    private Set<String> buildNewsDesksSet() {
        Set<String> newsDesks = new HashSet<>();
        if (chkbxArts.isChecked()) { newsDesks.add("Arts"); }
        if (chkbxFashion.isChecked()) { newsDesks.add("Fashion"); }
        if (chkbxSports.isChecked()) { newsDesks.add("Sports"); }
        return newsDesks;
    }

    private void setupSaveButton() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    String sortOrder = spnrSortOrder.getSelectedItem().toString();
                    Set<String> newsDesks = buildNewsDesksSet();
                    Filters f = new Filters(sortOrder, newsDesks, filters.beginDate, filters.query);
                    listener.onFinishedEditFilters(f);
                }
            }
        });
    }
}
