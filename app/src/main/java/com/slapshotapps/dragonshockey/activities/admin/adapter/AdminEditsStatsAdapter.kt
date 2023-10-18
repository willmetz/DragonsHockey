package com.slapshotapps.dragonshockey.activities.admin.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.slapshotapps.dragonshockey.activities.admin.viewmodels.AdminStatsViewModel
import com.slapshotapps.dragonshockey.databinding.ViewAdminStatsGoalieCardBinding
import com.slapshotapps.dragonshockey.databinding.ViewAdminStatsPlayerCardBinding
import com.slapshotapps.dragonshockey.models.PlayerPosition

/**
 * Created on 11/1/16.
 */
class AdminEditsStatsAdapter(val stats: ArrayList<AdminStatsViewModel>) :
        RecyclerView.Adapter<ViewHolder>() {

    fun getPlayerStats(): ArrayList<AdminStatsViewModel> = stats

    fun statsChanged(): Boolean {
        for (adminStatsViewModel in stats) {
            if (adminStatsViewModel.isDirty) {
                return true
            }
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType){
            ADMIN_VIEW_GOALIE -> {
                val binding = ViewAdminStatsGoalieCardBinding.inflate(inflater, parent, false)
                AdminGoalieViewHolder(binding, TextChangeHelper(AdminStatField.PenaltyMinutes),
                        TextChangeHelper(AdminStatField.GoalsAgainst),
                        CheckedChangedHelper())
            }
            else ->{
                val binding = ViewAdminStatsPlayerCardBinding.inflate(inflater, parent, false)
                AdminPlayerViewHolder(binding, TextChangeHelper(AdminStatField.PenaltyMinutes),
                        TextChangeHelper(AdminStatField.Goals), TextChangeHelper(AdminStatField.Assists),
                        CheckedChangedHelper())
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(isGoalie(position)){
            (holder as AdminGoalieViewHolder).bind(stats[position])
        }else{
            (holder as AdminPlayerViewHolder).bind(stats[position])
        }
    }
    override fun getItemCount(): Int {
        return stats.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(isGoalie(position)){
            ADMIN_VIEW_GOALIE
        }else{
            ADMIN_VIEW_PLAYER
        }
    }

    private fun isGoalie(position: Int): Boolean {
        return stats[position].position === PlayerPosition.GOALIE
    }

    inner class AdminGoalieViewHolder(private val binding: ViewAdminStatsGoalieCardBinding,
                                      private val pimWatcher: TextChangeHelper,
                                      private val goalsAgainstWatcher: TextChangeHelper,
                                      private val isPresentListener : CheckedChangedHelper) :
            ViewHolder(binding.root){
                fun bind(viewModel: AdminStatsViewModel){
                    binding.playerPresent.setOnCheckedChangeListener(null)
                    binding.pimInput.editText?.removeTextChangedListener(pimWatcher)
                    binding.goalAgainstInput.editText?.removeTextChangedListener(goalsAgainstWatcher)

                    binding.playerName.text = viewModel.playerName
                    binding.playerNumber.text = viewModel.playerNumber
                    binding.pimInput.editText?.setText(viewModel.penaltyMinutes)
                    binding.goalAgainstInput.editText?.setText(viewModel.goalsAgainst)
                    binding.playerPresent.isChecked = viewModel.presence

                    pimWatcher.holder = this
                    binding.pimInput.editText?.addTextChangedListener(pimWatcher)

                    goalsAgainstWatcher.holder = this
                    binding.goalAgainstInput.editText?.addTextChangedListener(goalsAgainstWatcher)

                    isPresentListener.holder = this
                    binding.playerPresent.setOnCheckedChangeListener(isPresentListener)
                }
    }

    inner class AdminPlayerViewHolder(private val binding: ViewAdminStatsPlayerCardBinding,
                                      private val pimWatcher: TextChangeHelper,
                                      private val goalWatcher: TextChangeHelper,
                                      private val assistWatcher: TextChangeHelper,
                                      private val isPresentListener : CheckedChangedHelper) :
            ViewHolder(binding.root){
        fun bind(viewModel: AdminStatsViewModel){
            binding.pimInput.editText?.removeTextChangedListener(pimWatcher)
            binding.goalInput.editText?.removeTextChangedListener(goalWatcher)
            binding.assistInput.editText?.removeTextChangedListener(assistWatcher)
            binding.playerPresent.setOnCheckedChangeListener(null)

            binding.playerName.text = viewModel.playerName
            binding.playerNumber.text = viewModel.playerNumber
            binding.pimInput.editText?.setText(viewModel.penaltyMinutes)
            binding.goalInput.editText?.setText(viewModel.goals)
            binding.assistInput.editText?.setText(viewModel.assists)
            binding.playerPresent.isChecked = viewModel.presence

            pimWatcher.holder = this
            binding.pimInput.editText?.addTextChangedListener(pimWatcher)

            goalWatcher.holder = this
            binding.goalInput.editText?.addTextChangedListener(goalWatcher)

            assistWatcher.holder = this
            binding.assistInput.editText?.addTextChangedListener(assistWatcher)

            isPresentListener.holder = this
            binding.playerPresent.setOnCheckedChangeListener(isPresentListener)
        }
    }

    enum class AdminStatField{
        Goals,
        Assists,
        PenaltyMinutes,
        GoalsAgainst
    }

    inner class TextChangeHelper(private val field: AdminStatField): TextWatcher{

        var holder: ViewHolder? = null
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //no-op
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //no-op
        }

        override fun afterTextChanged(editable: Editable?) {
            holder?.let {
                when(field){
                    AdminStatField.Goals -> stats[it.adapterPosition].goals = editable?.toString() ?: "0"
                    AdminStatField.Assists -> stats[it.adapterPosition].assists = editable?.toString() ?: "0"
                    AdminStatField.PenaltyMinutes -> stats[it.adapterPosition].penaltyMinutes = editable?.toString() ?: "0"
                    AdminStatField.GoalsAgainst -> stats[it.adapterPosition].goalsAgainst = editable?.toString() ?: "0"
                }
            }

        }
    }

    inner class CheckedChangedHelper(): OnCheckedChangeListener{
        var holder: ViewHolder? = null
        override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
            holder?.let {
                stats[it.adapterPosition].presence = isChecked
            }
        }
    }



    companion object{
        const val ADMIN_VIEW_GOALIE = 1
        const val ADMIN_VIEW_PLAYER = 2
    }
}