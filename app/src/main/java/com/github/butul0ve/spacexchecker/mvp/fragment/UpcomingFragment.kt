package com.github.butul0ve.spacexchecker.mvp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.butul0ve.spacexchecker.R
import com.github.butul0ve.spacexchecker.SpaceXApp
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.mvp.interactor.UpcomingMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.presenter.UpcomingPresenter
import com.github.butul0ve.spacexchecker.mvp.view.UpcomingView
import com.github.butul0ve.spacexchecker.ui.BaseFragment
import javax.inject.Inject

class UpcomingFragment : BaseFragment(), UpcomingView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dragonsRecycler: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var tryAgainButton: Button

    private var toast: Toast? = null

    @Inject
    lateinit var interactor: UpcomingMvpInteractor

    @InjectPresenter
    lateinit var upcomingPresenter: UpcomingPresenter

    @ProvidePresenter
    fun providePresenter() = UpcomingPresenter(interactor)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        SpaceXApp.netComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.base_fragment, container, false)
        dragonsRecycler = view.findViewById(R.id.recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)
        tryAgainButton = view.findViewById(R.id.try_again_button)
        tryAgainButton.setOnClickListener { upcomingPresenter.getData() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)
    }

    override fun setAdapter(adapter: LaunchesAdapter) {
        dragonsRecycler.adapter = adapter
    }

    override fun showProgressBar() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        upcomingPresenter.getData()
    }

    override fun showTryAgainButton() {
        tryAgainButton.visibility = View.VISIBLE
    }

    override fun hideTryAgainButton() {
        tryAgainButton.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast.makeText(activity, "soon", Toast.LENGTH_SHORT)
        toast!!.show()
    }
}