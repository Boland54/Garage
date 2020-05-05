package org.wit.garage.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.garage.helpers.*
import java.util.*

val JSON_FILE = "garages.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<GarageModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GarageJSONStore : GarageStore, AnkoLogger {

    val context: Context
    var garages = mutableListOf<GarageModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GarageModel> {
        return garages
    }

    override fun create(garage: GarageModel) {
        garage.id = generateRandomId()
        garages.add(garage)
        serialize()
    }

    override fun delete(garage: GarageModel) {
        garages.remove(garage)
        serialize()
    }

    override fun update(garage: GarageModel) {
        val garagesList = findAll() as ArrayList<GarageModel>
        var foundGarage: GarageModel? = garagesList.find { p ->
            p.id == garage.id
        }
            if (foundGarage != null) {
            foundGarage.make = garage.make
            foundGarage.model = garage.model
            foundGarage.year = garage.year
            foundGarage.litre = garage.litre
            foundGarage.doors = garage.doors
            foundGarage.image = garage.image
            foundGarage.lat = garage.lat
            foundGarage.lng = garage.lng
            foundGarage.zoom = garage.zoom
            serialize();
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(garages, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        garages = Gson().fromJson(jsonString, listType)
    }
}