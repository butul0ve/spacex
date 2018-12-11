package com.example.butul0ve.spacex.db

import com.example.butul0ve.spacex.db.dao.RocketDao
import com.example.butul0ve.spacex.db.model.Rocket
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class RocketsRepository @Inject constructor(val rocketDao: RocketDao) {

    fun getAllRocketsFromDb(): Flowable<List<Rocket>> {
        return rocketDao.getAll()
    }

    fun deleteAllRockets(): Completable {
        return Completable.fromAction { rocketDao.deleteAll() }
    }

    fun insertRocket(rocket: Rocket): Completable {
        return Completable.fromAction { rocketDao.insert(rocket) }
    }

    fun insertRockets(rockets: List<Rocket>): Completable {
        return Completable.fromAction { rocketDao.insert(rockets) }
    }
}