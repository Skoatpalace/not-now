package com.skoatpalace.notnow

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ConfirmDeleteNoteDialogFragment @SuppressLint("ValidFragment")
constructor(val noteTitle: String = "") : DialogFragment() {

    interface ConfirmeDeleteDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    var listener: ConfirmeDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Êtes-vous sûr de vouloir supprimer la note du \"$noteTitle\" ?")
            .setPositiveButton("Supprimer",
                DialogInterface.OnClickListener{ dialog, id -> listener?.onDialogPositiveClick()})
            .setNegativeButton("Annuler",
                DialogInterface.OnClickListener{ dialog, id -> listener?.onDialogNegativeClick()})

        return builder.create()
    }
}