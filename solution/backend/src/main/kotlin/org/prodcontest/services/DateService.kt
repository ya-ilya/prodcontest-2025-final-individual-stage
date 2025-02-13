package org.prodcontest.services

import org.springframework.stereotype.Service

@Service
class DateService {
    private var currentDate: Int = 0

    fun getCurrentDate() = currentDate
    fun setCurrentDate(date: Int) {
        this.currentDate = date
    }
}