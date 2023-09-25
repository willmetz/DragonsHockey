package com.slapshotapps.dragonshockey.activities.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
                AdminGoalieViewHolder(binding)
            }
            else ->{
                val binding = ViewAdminStatsPlayerCardBinding.inflate(inflater, parent, false)
                AdminPlayerViewHolder(binding)
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

    inner class AdminGoalieViewHolder(private val binding: ViewAdminStatsGoalieCardBinding) :
            ViewHolder(binding.root){
                fun bind(viewModel: AdminStatsViewModel){
                    binding.playerName.text = viewModel.playerName
                    binding.playerNumber.text = viewModel.playerNumber
                    binding.pimInput.editText?.setText(viewModel.penaltyMinutes)
                    binding.goalAgainstInput.editText?.setText(viewModel.playerNumber)
                    binding.pimInput.editText?.addTextChangedListener{
                        viewModel.penaltyMinutes = it?.toString() ?: "0"
                    }
                    binding.goalAgainstInput.editText?.addTextChangedListener {
                        viewModel.goalsAgainst = it?.toString() ?: "0"
                    }
                }
    }

    inner class AdminPlayerViewHolder(private val binding: ViewAdminStatsPlayerCardBinding) :
            ViewHolder(binding.root){
        fun bind(viewModel: AdminStatsViewModel){
            binding.playerName.text = viewModel.playerName
            binding.playerNumber.text = viewModel.playerNumber
            binding.pimInput.editText?.setText(viewModel.penaltyMinutes)
            binding.goalInput.editText?.setText(viewModel.goals)
            binding.assistInput.editText?.setText(viewModel.assists)

            binding.pimInput.editText?.addTextChangedListener{
                viewModel.penaltyMinutes = it?.toString() ?: "0"
            }
            binding.goalInput.editText?.addTextChangedListener{
                viewModel.goals = it?.toString() ?: "0"
            }
            binding.assistInput.editText?.addTextChangedListener{
                viewModel.assists = it?.toString() ?: "0"
            }
        }
    }

    companion object{
        const val ADMIN_VIEW_GOALIE = 1
        const val ADMIN_VIEW_PLAYER = 2
    }
}