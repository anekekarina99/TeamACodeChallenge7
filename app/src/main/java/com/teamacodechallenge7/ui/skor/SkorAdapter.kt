package com.teamacodechallenge7.ui.skor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.model.GetBattle


class SkorAdapter(
    private var listSkor: List<GetBattle>,
    val context: Context
) : RecyclerView.Adapter<SkorAdapter.ViewHolder>() {
    private val tag : String = "Skor"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modeBattle = listSkor.get(position).data.get(position).mode
        val result = listSkor[position].data.get(position).result
        holder.tvMode.text = modeBattle
        holder.tvResult.text = result
    }

    override fun getItemCount(): Int {
        return listSkor.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMode: TextView = itemView.findViewById(R.id.tvMode)
        var tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }

}