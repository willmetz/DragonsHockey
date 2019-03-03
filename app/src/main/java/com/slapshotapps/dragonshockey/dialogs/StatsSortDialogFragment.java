package com.slapshotapps.dragonshockey.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class StatsSortDialogFragment extends DialogFragment {

    public interface StatsSortSelectionListener {
        void onSortOptionSelected(StatSortSelection sortSelection);
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
            statSortOptions[0].getStatName(), statSortOptions[1].getStatName(),
            statSortOptions[2].getStatName(), statSortOptions[3].getStatName(), statSortOptions[4].getStatName()
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle("Stat Sorting")
            .setSingleChoiceItems(items,
                currentSelection != null ? currentSelection.getIndex()
                    : StatSortSelection.Points.getIndex(),
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
