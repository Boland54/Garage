package org.wit.garage.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class GarageMemStore : GarageStore, AnkoLogger {

    val garages = ArrayList<GarageModel>()

    override fun findAll(): List<GarageModel> {
        return garages
    }

    override fun create(garage: GarageModel) {
        garage.id = getId()
        garages.add(garage)
        logAll()
    }

    override fun update(garage: GarageModel) {
        var foundGarage: GarageModel? = garages.find { p -> p.id == garage.id }
        if (foundGarage != null) {
            foundGarage.make= garage.make
            foundGarage.model = garage.model
            foundGarage.year = garage.year
            foundGarage.litre = garage.litre
            foundGarage.doors = garage.doors
            foundGarage.image = garage.image
            foundGarage.lat = garage.lat
            foundGarage.lng = garage.lng
            foundGarage.zoom = garage.zoom
            logAll();
        }
    }

    override fun delete(garage: GarageModel) {
        garages.remove(garage)
    }

    fun logAll(){
        garages.forEach {info("${it}")}
    }

}