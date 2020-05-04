package org.wit.garage.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_garage.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.garage.R
import org.wit.garage.helpers.readImageFromPath
import org.wit.garage.helpers.showImagePicker
import org.wit.garage.main.MainApp
import org.wit.garage.models.GarageModel
import org.wit.garage.models.Location

class LocationActivity: AppCompatActivity(), AnkoLogger {

    var garage = GarageModel()
    lateinit var app: MainApp

    // var location = Location(52.245696, -7.139102, 15f)

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        app = application as MainApp
        edit = true

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_garage, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}