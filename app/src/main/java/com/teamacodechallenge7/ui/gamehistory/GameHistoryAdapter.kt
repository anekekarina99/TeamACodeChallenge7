package com.teamacodechallenge7.ui.gamehistory

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.teamacodechallenge7.R
import com.teamacodechallenge7.data.model.GetBattle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GameHistoryAdapter(
    private val arrayList: List<GetBattle.Data>,
    val context: Context
) : RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var tvMode: TextView = itemView.findViewById(R.id.tvMode)
        var tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skor, parent, false)
        )


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = arrayList[position].updatedAt
        val modeBattle = arrayList[position].mode
        val result = arrayList[position].result

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        Locale.ENGLISH
    )
        val dateTime: LocalDate = LocalDate.parse(date, formatter)

        val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val formattedTime = output.format(dateTime)

        holder.tvMode.text = modeBattle
        holder.tvResult.text = result
        holder.tvDate.text = formattedTime

        val formatter = SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone("America/New_York")

        val dateInString = "22-01-2015 10:15:55 AM"
        val date = formatter.parse(dateInString)
        val formattedDateString = formatter.format(date)
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }


}
