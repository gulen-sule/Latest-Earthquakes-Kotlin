package com.example.latestearthquakes.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.latestearthquakes.data.model.DepremModel
import com.example.latestearthquakes.R
import com.example.latestearthquakes.data.model.Depremler
import com.example.latestearthquakes.databinding.ItemDepremBinding

class DepremlerAdapter(private var depremlerList: DepremModel, val Onclick: (Depremler) -> Unit) :
    RecyclerView.Adapter<DepremlerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: ItemDepremBinding) : RecyclerView.ViewHolder(itemView.root) {
        var eventBinding: ItemDepremBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val depremBinding: ItemDepremBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_deprem, parent, false)
        return ViewHolder(depremBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deprem = depremlerList.depremler?.get(position)
        holder.eventBinding.depremData = deprem
       // holder.eventBinding.depremSaat.text= deprem!!.saat.toString().substring(0,5) sor
        setColor(deprem!!.buyukluk.ml!!.toDouble(), holder)
        holder.eventBinding.itemLayout.setOnClickListener {
            Onclick(deprem)
        }
    }
    override fun getItemCount(): Int {
        return depremlerList.depremler!!.size
    }

    private fun setColor(value: Double, holder: ViewHolder) {

        when {
            value > 5.0 -> holder.eventBinding.depremBuyukluk.setBackgroundResource(R.drawable.big_earthquake_drawable)
            value > 3.0 -> holder.eventBinding.depremBuyukluk.setBackgroundResource(R.drawable.middle_earthquake_drawable)
            else -> holder.eventBinding.depremBuyukluk.setBackgroundResource(R.drawable.little_earthquake_drawable)
        }
    }
}