package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.butul0ve.spacexchecker.adapter.LaunchesAdapter
import com.github.butul0ve.spacexchecker.db.model.Launch
import com.github.butul0ve.spacexchecker.mvp.interactor.UpcomingMvpInteractor
import com.github.butul0ve.spacexchecker.mvp.view.UpcomingView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class UpcomingPresenter @Inject constructor(override val interactor: UpcomingMvpInteractor) :
        BasePresenter<UpcomingView>(interactor) {

    private lateinit var adapter: LaunchesAdapter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getData()
    }

    fun getData() {
        if (viewState != null) {

            disposable.add(interactor.getUpcomingLaunchesFromDb()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        adapter = LaunchesAdapter(it, viewState)
                        viewState.setAdapter(adapter)
                    })

            viewState.showProgressBar()

            interactor.getUpcomingLaunchesFromServer()
                    .subscribeOn(Schedulers.io())
                    .map {
                        disposable.add(interactor.replaceUpcomingLaunches(it)
                                .subscribe())
                        it
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserver())
        }
    }

    fun openYoutubePlayerActivity(position: Int) {
        if (viewState != null) {
            if (::adapter.isInitialized) {
                val launch = adapter.getLaunchById(position)
                launch.links.videoLink?.let { viewState.openYoutube(it) }
            }
        }
    }

    private fun getObserver() : SingleObserver<List<Launch>> {
        return object : SingleObserver<List<Launch>> {

            override fun onSuccess(t: List<Launch>) {
                if (viewState == null) return

                adapter = LaunchesAdapter(t, viewState)
                viewState.setAdapter(adapter)
                viewState.hideProgressBar()
                viewState.hideTryAgainButton()
            }

            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                Timber.e(e)
                if (viewState == null) return

                viewState.hideProgressBar()
                viewState.showTryAgainButton()
            }
        }
    }
}