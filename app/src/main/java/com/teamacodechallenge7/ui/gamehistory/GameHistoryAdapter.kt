package com.teamacodechallenge7.ui.gamehistory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.model.GetBattle

class GameHistoryAdapter(
    private val arrayList: List<GetBattle.Data>,
    val context: Context
) : RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMode: TextView = itemView.findViewById(R.id.tvMode)
        var tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skor, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modeBattle = arrayList[position].mode
        val result = arrayList[position].result
        holder.tvMode.text = modeBattle
        holder.tvResult.text = result
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}
