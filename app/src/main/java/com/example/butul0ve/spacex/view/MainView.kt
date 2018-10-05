package com.example.butul0ve.spacex.view

import android.net.Uri
import com.example.butul0ve.spacex.adapter.PastLaunchesAdapter

/**
 * Created by butul0ve on 21.01.18.
 */

interface MainView {

    fun showProgressBar()

    fun hideProgressBar()

    fun openYouTube(uri: Uri)

    fun setAdapter(adapter: PastLaunchesAdapter)

    fun showToast(message: Int)

    fun showButtonTryAgain()

    fun hideButtonTryAgain()
}