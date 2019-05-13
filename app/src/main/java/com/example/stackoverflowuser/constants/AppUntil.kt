package com.example.stackoverflowuser.constants

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by tho nguyen on 2019-05-13.
 */
class AppUntil {

    companion object {
        val SECOND_MILLIS: Long = 1000
        val MINUTE_MILLIS = 60 * SECOND_MILLIS
        val HOUR_MILLIS = 60 * MINUTE_MILLIS
        val DAY_MILLIS = 24 * HOUR_MILLIS
        val WEEK_MILLIS = 7 * DAY_MILLIS
        val MONTH_MILLIS = 30 * DAY_MILLIS
        private val YEAR_MILLIS = 365 * DAY_MILLIS

        fun getRelativeDateTime(timeMillisecond: Long, isAcronym: Boolean): String {
            val diff = System.currentTimeMillis() - timeMillisecond
            return when {
                diff < MINUTE_MILLIS -> if (isAcronym) (diff / 1000).toString() + "s" else "Just now"
                diff < 2 * MINUTE_MILLIS -> if (isAcronym) "1m" else "1 minute ago"
                diff < 50 * MINUTE_MILLIS -> (diff / MINUTE_MILLIS).toString() + if (isAcronym) "m" else " minutes ago"
                diff < 120 * MINUTE_MILLIS -> if (isAcronym) "1h" else "1 hour ago"
                diff < 24 * HOUR_MILLIS -> (diff / HOUR_MILLIS).toString() + if (isAcronym) "h" else " hours ago"
                diff < 48 * HOUR_MILLIS -> if (isAcronym) "1d" else "Yesterday"
                diff < WEEK_MILLIS -> (diff / DAY_MILLIS).toString() + if (isAcronym) "d" else " days ago"
                diff < 2 * WEEK_MILLIS -> if (isAcronym) "1w" else "Last week"
                diff < MONTH_MILLIS -> (diff / WEEK_MILLIS).toString() + if (isAcronym) "w" else " weeks ago"
                diff < 2 * MONTH_MILLIS -> if (isAcronym) "1mo" else "Last month"
                diff < YEAR_MILLIS && diff / MONTH_MILLIS < 12 -> (diff / MONTH_MILLIS).toString() + if (isAcronym) "mo" else " months ago"
                else -> formatDate(timeMillisecond)
            }
        }

        fun formatDate(timeMillisecond: Long): String {
            val date = Date()
            date.time = timeMillisecond
            val simpleDateFormat = SimpleDateFormat(
                    "MMM dd, yyyy",
                    Locale.getDefault()
            )
            return simpleDateFormat.format(date)
        }

    }
}