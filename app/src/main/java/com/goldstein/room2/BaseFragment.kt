package com.goldstein.room2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(getLayout(), container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }


    abstract fun getLayout(): Int
    abstract fun setupViews()


    fun getBaseString(resId: Int): String {

        return resources.getString(resId)

    }

    fun getBaseColor(resId: Int): Int {

        return resources.getColor(resId)

    }



    fun getMainActivity(run: (MainActivity) -> (Unit)) {

        (activity as? MainActivity).let {
            it?.let { it1 ->
                run(it1)
            }
        }
    }
}