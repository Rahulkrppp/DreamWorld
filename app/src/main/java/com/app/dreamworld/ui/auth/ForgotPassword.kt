package com.app.dreamworld.ui.auth
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.dreamworld.R
import com.app.dreamworld.databinding.ForgotPasswordBinding
import com.app.dreamworld.util.clickWithDebounce
import com.app.dreamworld.util.getTrimText
import com.app.dreamworld.util.isValidEmail
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ForgotPassword : BottomSheetDialogFragment() {

    var sendClickListener: (code: String) -> Unit = {}

    private lateinit var binding : ForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
         container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    binding = ForgotPasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.btnForgotPassword.clickWithDebounce {
          sendClickListener(binding.edtEmail.getTrimText())
           dialog?.dismiss()
       }
    }


}