package org.wit.garage.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_garage.view.*
import org.wit.garage.R
import org.wit.garage.helpers.readImageFromPath
import org.wit.garage.models.GarageModel

interface GarageListener {
    fun onGarageClick(garage: GarageModel)
}

class GarageAdapter constructor(private var garages: List<GarageModel>,
                                   private val listener: GarageListener) : RecyclerView.Adapter<GarageAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_garage, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val garage = garages[holder.adapterPosition]
        holder.bind(garage, listener)
    }

    override fun getItemCount(): Int = garages.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(garage: GarageModel,  listener : GarageListener) {
            itemView.garageMake.text = garage.make
            itemView.model.text = garage.model
            itemView.year.text = garage.year
            itemView.litre.text = garage.litre
            itemView.doors.text = garage.doors
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, garage.image))
            itemView.setOnClickListener { listener.onGarageClick(garage) }
        }
    }
}