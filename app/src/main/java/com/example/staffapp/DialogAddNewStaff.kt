package com.example.staffapp

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

/*
* Phải truyền vào StaffDatabase để sử dụng trong class này
* */
class DialogAddNewStaff {
    lateinit var _dialogInterface: DialogInterface

    fun showDialog(context: Context?, staffDatabase: StaffDatabase) { // Activity hoac Fragment hien tai chua dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false) // true or false to close dialog when touch in screen
        dialog.setContentView(R.layout.layout_dialog_add_new_staff)

        val btnAdd: Button = dialog.findViewById(R.id.btnAdd)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
        val edtFullName: EditText = dialog.findViewById(R.id.edtFullName)
        val edtAge: EditText = dialog.findViewById(R.id.edtAge)
        val edtCountry: EditText = dialog.findViewById(R.id.edtCountry)

        btnAdd.setOnClickListener {
            val staffDAO = staffDatabase.callDAO()
            val fullName = edtFullName.text.toString()
            val age = Integer.parseInt(edtAge.text.toString())
            val country = edtCountry.text.toString()
            var staff    = Staff(id = null, fullName = fullName, age = age, country = country)
            staffDAO.addStaff(staff)
            if (_dialogInterface != null) {
                _dialogInterface.reloadList(true) // Bien isReload dung o Activity sau khi nhan btnAdd
                dialog.dismiss();
            }
            Toast.makeText(context, "Add new staff success.", Toast.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            Toast.makeText(context, "Cancel !", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }


        dialog.show()
        val window: Window? = dialog.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    // Ben activity dung de truyen vo 1 instance cua Interface
    fun setDialogInterface(_dialogInterface: DialogInterface) {
        this._dialogInterface = _dialogInterface
    }

    // Interface co 1 ham cho MainActivity dung
    interface DialogInterface {
        fun reloadList(isReload: Boolean)
    }

}