package com.example.staffapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), DialogEditStaff.DialogInterface {
    private var db: StaffDatabase? = null
    private lateinit var rvListStaff: RecyclerView
    private lateinit var imvDeleteAll: ImageView
    private lateinit var staffs: ArrayList<Staff>
    private lateinit var staffAdapter: StaffAdapter
    private lateinit var staffDAO: StaffDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRoomDB()
        initViews()

        var fabAddNewStaff: FloatingActionButton = findViewById(R.id.fabAddNewStaff)

        fabAddNewStaff.setOnClickListener {
            val dialogAddNewStaff = DialogAddNewStaff()
            dialogAddNewStaff.showDialog(this@MainActivity, db!!)
            dialogAddNewStaff.setDialogInterface(object : DialogAddNewStaff.DialogInterface {
                override fun reloadList(isReload: Boolean) {
                    if (isReload) {
                        Log.d("isReload", isReload.toString())
                        staffs.clear()
                        getAllStaff()
                        staffAdapter.reloadList(staffs)
                    }
                }
            }) // new A() {...}
        }

        getAllStaff()
        setupRecyclerView()
    }

    private fun initRoomDB() {
        // StaffDatabase duoc khoi tao 1 lan va dung o moi class trong app
        db = Room.databaseBuilder(applicationContext, StaffDatabase::class.java, "staff-db")
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    private fun initViews() {
        rvListStaff = findViewById(R.id.rvListStaff)
        imvDeleteAll = findViewById(R.id.imvDeleteAll)
        imvDeleteAll.setOnClickListener {
            staffDAO = db!!.callDAO()
            staffDAO.deleteAllStaff()
            staffs.clear()
            staffAdapter.reloadList(staffs)
            Toast.makeText(this@MainActivity, "Deleted all staff success !", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getAllStaff() {
        staffDAO = db!!.callDAO()
        staffs = ArrayList()
        staffs = staffDAO.getAllStaff() as ArrayList<Staff>
        /*for (item in staffs) {
            Log.d("Staffs", "Fullname: ${item.fullName} " +
                    "| age: ${item.age} | country: ${item.country} | id: ${item.id}")
        }*/
    }

    private fun setupRecyclerView() {
        staffAdapter = StaffAdapter(this@MainActivity, staffs)
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvListStaff.setHasFixedSize(true)
        rvListStaff.layoutManager = manager
        rvListStaff.adapter = staffAdapter
        // Update


        staffAdapter.setOnItemUpdateListener(object : StaffAdapter.ClickListenerUpdate {
            override fun onItemClickUpdate(position: Int, v: View) {
                val dialogEditStaff = DialogEditStaff()
                dialogEditStaff.showDialog(
                    this@MainActivity, db!!, staffs[position].id!!, staffs[position].fullName,
                    staffs[position].age, staffs[position].country
                )
                dialogEditStaff.setDialogInterface(object : DialogEditStaff.DialogInterface {
                    override fun reloadList(isReload: Boolean) {
                        Log.d("isReloadUpdate!!", isReload.toString())
                        staffs.clear()
                        getAllStaff()
                        staffAdapter.reloadList(staffs)
                    }
                })
            }
        })
        // Delete
        staffAdapter.setOnItemDeleteListener(object : StaffAdapter.ClickListenerDelete {
            override fun onItemClickDelete(position: Int, v: View) {
                staffDAO = db!!.callDAO()
                staffDAO.deleteStaff(staffs[position].id!!)
                staffs.clear()
                getAllStaff()
                staffAdapter.reloadList(staffs)
                Toast.makeText(this@MainActivity, "Deleted 1 staff success !", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun reloadList(isReload: Boolean) {
        Log.d("isReloadUpdateActivity", UpdateStaffTemp.isReload.toString())
    }
}