package com.slapshotapps.dragonshockey.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class StatsSortDialogFragment extends DialogFragment {

    public interface StatsSortSelectionListener {
        void onSortOptionSelected(StatSortSelection sortSelection);
    }

    public enum StatSortSelection {
        GamesPlayed("Games Played", 0), Goals("Goals", 1), Assists("Assists", 2),
        Points("Points", 3), PenaltyMinutes("Penalty Minutes", 4);

        String name;
        int index;

        StatSortSelection(String name, int index) {
            this.index = index;
            this.name = name;
        }
    }

    private static final String CURRENT_SORT_OPTION_KEY = "currentSortOption";

    private final StatSortSelection[] statSortOptions = {
        StatSortSelection.GamesPlayed, StatSortSelection.Goals, StatSortSelection.Assists,
        StatSortSelection.Points, StatSortSelection.PenaltyMinutes
    };

    private StatsSortSelectionListener listener;

    public static StatsSortDialogFragment newInstance(StatSortSelection currentSortSelection) {
        Bundle args = new Bundle();
        StatsSortDialogFragment fragment = new StatsSortDialogFragment();

        args.putSerializable(CURRENT_SORT_OPTION_KEY, currentSortSelection);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        StatSortSelection currentSelection =
            (StatSortSelection) getArguments().getSerializable(CURRENT_SORT_OPTION_KEY);

        final String[] items = {
            statSortOptions[0].name, statSortOptions[1].name, statSortOptions[2].name,
            statSortOptions[3].name, statSortOptions[4].name
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Stat Sorting")
            .setSingleChoiceItems(items,
                currentSelection != null ? currentSelection.index : StatSortSelection.Points.index,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onSortOptionSelected(statSortOptions[which]);
                            dismiss();
                        }
                    }
                });

        return builder.create();
    }

    public void setListener(StatsSortSelectionListener listener) {
        this.listener = listener;
    }
}
