package com.slapshotapps.dragonshockey.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class StatsSortDialogFragment : DialogFragment() {

  private val statSortOptions = arrayOf(StatSortSelection.GamesPlayed, StatSortSelection.Goals,
      StatSortSelection.Assists, StatSortSelection.Points, StatSortSelection.PenaltyMinutes)

  private var listener: StatsSortSelectionListener? = null

  interface StatsSortSelectionListener {
    fun onSortOptionSelected(sortSelection: StatSortSelection)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    val currentSelection = arguments!!.getSerializable(CURRENT_SORT_OPTION_KEY) as StatSortSelection

    val items = arrayOf(statSortOptions[0].statName, statSortOptions[1].statName,
        statSortOptions[2].statName, statSortOptions[3].statName, statSortOptions[4].statName)

    val builder = AlertDialog.Builder(context!!).setTitle("Stat Sorting")
        .setSingleChoiceItems(items, currentSelection.index) { _, which ->
          if (listener != null) {
            listener!!.onSortOptionSelected(statSortOptions[which])
            dismiss()
          }
        }

    return builder.create()
  }

  fun setListener(listener: StatsSortSelectionListener) {
    this.listener = listener
  }

  companion object {

    private val CURRENT_SORT_OPTION_KEY = "currentSortOption"

    fun newInstance(currentSortSelection: StatSortSelection): StatsSortDialogFragment {
      val args = Bundle()
      val fragment = StatsSortDialogFragment()

      args.putSerializable(CURRENT_SORT_OPTION_KEY, currentSortSelection)
      fragment.arguments = args
      return fragment
    }
  }
}
