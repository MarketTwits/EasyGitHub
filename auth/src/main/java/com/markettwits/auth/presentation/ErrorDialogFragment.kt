package com.markettwits.auth.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.markettwits.core.R
import dagger.hilt.android.AndroidEntryPoint


internal class ErrorDialogFragment(
    private val message: Int,
    private val viewModel: AuthViewModel
) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.something_error_label))
            .setMessage(requireContext().getString(message))
            .setPositiveButton("ok") { _, _ ->
                viewModel.dismiss()
            }
            .create()

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.dismiss()
    }

    companion object {
        const val TAG = "ErrorDialogFragment"
    }
}