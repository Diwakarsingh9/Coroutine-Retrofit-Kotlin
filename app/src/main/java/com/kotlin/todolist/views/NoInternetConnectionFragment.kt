package com.test.diwakarsinghtest.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kotlin.todolist.R
import kotlinx.android.synthetic.main.internet_problem.view.*

class NoInternetConnectionFragment(val fragInternetCondition: Boolean) : Fragment() {

    private lateinit var rootView: View
    private lateinit var retry: TextView
    private var callBack: SimpleCallBack? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SimpleCallBack) {
            callBack = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.internet_problem, container, false)
        initializeViews()
        reloadOnclickListener()
        return rootView
    }

    private fun initializeViews() {
        if (fragInternetCondition) {
            rootView.text_title_error.text =
                this.resources.getString(R.string.no_internet_connection)
            rootView.text_subtitle_error.text =
                this.resources.getString(R.string.an_alien_is_probably_blocking_your_signal)
        } else {
            rootView.text_title_error.text =
                this.resources.getString(R.string.something_went_wrong)
            rootView.text_subtitle_error.text =
                this.resources.getString(R.string.wash_your_hands)
        }
    }

    private fun reloadOnclickListener() {
        rootView.reload.setOnClickListener {
            callBack?.onReload()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callBack = null
    }


}