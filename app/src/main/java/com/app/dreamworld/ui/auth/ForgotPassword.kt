package com.app.dreamworld.ui.auth
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dreamworld.databinding.ForgotPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ForgotPassword : BottomSheetDialogFragment() {

    private lateinit var binding : ForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
         container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    binding = ForgotPasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}