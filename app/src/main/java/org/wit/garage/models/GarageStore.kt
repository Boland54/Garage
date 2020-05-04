package org.wit.garage.models

interface GarageStore {
    fun findAll(): List<GarageModel>
    fun create(garage: GarageModel)
    fun update(garage: GarageModel)
    fun delete(garage: GarageModel)
}