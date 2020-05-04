package org.wit.garage.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.garage.models.GarageJSONStore
import org.wit.garage.models.GarageStore


class MainApp : Application(), AnkoLogger {

   // val garages= GarageMemStore()

 //   override fun onCreate() {
  //      super.onCreate()
   //     info("Placemark started")
   // }

    lateinit var garages: GarageStore

    override fun onCreate() {
        super.onCreate()
        garages = GarageJSONStore(applicationContext)
        info("Placemark started")
    }

}