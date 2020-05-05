package org.wit.garage.activities

import android.content.Intent
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
import org.wit.garage.helpers.readImage
import org.wit.garage.helpers.readImageFromPath
import org.wit.garage.helpers.showImagePicker
import org.wit.garage.main.MainApp
import org.wit.garage.models.GarageModel
import org.wit.garage.models.Location


class GarageActivity : AppCompatActivity(), AnkoLogger {

    var garage = GarageModel()
    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage)
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("cars_edit")) {
            edit = true
            garage = intent.extras.getParcelable<GarageModel>("cars_edit")
            garageMake.setText(garage.make)
            model.setText(garage.model)
            year.setText(garage.year)
            litre.setText(garage.litre)
            doors.setText(garage.doors)
            placemarkImage.setImageBitmap(readImageFromPath(this, garage.image))
            if (garage.image != null){
                chooseImage.setText(R.string.change_garage_image)
            }
            btnAdd.setText(R.string.save_garage)
        }

        garageLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (garage.zoom != 0f) {
                location.lat =  garage.lat
                location.lng = garage.lng
                location.zoom = garage.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        btnAdd.setOnClickListener() {
            garage.make = garageMake.text.toString()
            garage.model = model.text.toString()
            garage.year = year.text.toString()
            garage.litre = litre.text.toString()
            garage.doors = doors.text.toString()

            if (garage.make.isEmpty() or garage.model.isEmpty() or garage.year.isEmpty() or garage.litre.isEmpty() or
            garage.doors.isEmpty()) {
                toast(R.string.enter_garage_title)
            } else {
                if (edit) {
                    app.garages.update(garage.copy())
                } else {
                    app.garages.create(garage.copy())
                }
            }
            info("add Button Pressed: $garageMake")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_garage, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.garages.delete(garage)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    garage.image = data.getData().toString()
                    placemarkImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_garage_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    garage.lat = location.lat
                    garage.lng = location.lng
                    garage.zoom = location.zoom
                }
            }
        }
    }
}