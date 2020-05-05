package org.wit.garage.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_garage_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.garage.R
import org.wit.garage.main.MainApp
import org.wit.garage.models.GarageModel


class GarageListActivity : AppCompatActivity(), GarageListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garage_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadGarages()

        //enable action bar and set title
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.item_add ->
                        startActivityForResult<GarageActivity>(0)
                    R.id.item_map ->
                        startActivityForResult<LocationActivity>(0)
                }

                return super.onOptionsItemSelected(item)
        }


    override fun onGarageClick(garage: GarageModel) {
        startActivityForResult(intentFor<GarageActivity>().putExtra("cars_edit", garage), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadGarages()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadGarages() {
        showGarages(app.garages.findAll())
    }

    fun showGarages (garages: List<GarageModel>) {
        recyclerView.adapter = GarageAdapter(garages, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}