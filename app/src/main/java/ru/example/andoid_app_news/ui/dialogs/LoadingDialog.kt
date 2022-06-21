package ru.example.andoid_app_news.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.example.andoid_app_news.R

class LoadingDialog(
    private val inflater: LayoutInflater,
    private val context: Context?,
    private val viewGroup: ViewGroup?
) {

    private var alertDialog: AlertDialog? = null

    fun show() {
        val builder = AlertDialog.Builder(context)
            .setView(inflater.inflate(R.layout.loading_dialog, viewGroup))
            .setCancelable(true)

        alertDialog = builder.create()
        alertDialog?.show()
    }

    fun close() {
        alertDialog?.dismiss()
    }
}