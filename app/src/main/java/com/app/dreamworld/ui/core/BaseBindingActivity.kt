package com.app.dreamworld.ui.core

import androidx.viewbinding.ViewBinding


/**
 * This class have common methods used ny all the activities in the app
 *
 * @param T
 */
abstract class BaseBindingActivity<T : ViewBinding> : BaseActivity() {
    lateinit var binding: T

    fun setBindingView(viewBinding: T) {

        binding = viewBinding


        setContentView(binding.root)
        initComponents()
        setClickListener()
    }

    abstract fun initComponents()
    abstract fun setClickListener()


}