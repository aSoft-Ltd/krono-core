package krono.internal

import krono.Duration
import krono.DurationUnit
import krono.utils.DayOfWeek
import krono.LocalDate
import krono.LocalDateEpoch
import krono.Month
import krono.days
import krono.numberOfDays
import krono.utils.DayOfYear
import krono.utils.DaysOfMonth
import kotlin.math.floor

@PublishedApi
internal data class LocalDateImpl(
    override val year: Int, override val monthNumber: Int, override val dayOfMonth: Int
) : AbstractDateLike(), LocalDate {

    override val month: Month = Month.values().firstOrNull {
        it.number == monthNumber
    } ?: throw IllegalArgumentException("Can't get month of $monthNumber")

    override val dayOfWeek = DayOfWeek(year, monthNumber, dayOfMonth)

    override val dayOfYear = DayOfYear(year, monthNumber, dayOfMonth)

    override fun atEndOfMonth(): LocalDate = atDate(DaysOfMonth(year, monthNumber))

    override fun atDate(date: Int): LocalDate = LocalDateImpl(year, monthNumber, date)

    //see below Anderson. The commented code was not working properly
//    override fun compareTo(other: LocalDate): Int = (year * 365 + dayOfYear).compareTo((other.year * 365) + other.dayOfYear)
    override fun compareTo(other: LocalDate): Int = (year * 365 + dayOfYear) - ((other.year * 365) + other.dayOfYear)
    override fun minus(other: LocalDate) = Duration(compareTo(other).toDouble(), DurationUnit.Days)

    override fun plus(duration: Duration): LocalDate = when (duration.unit) {
        DurationUnit.Days -> plusDays(duration.value)
        DurationUnit.Weeks -> plusDays(duration.value * 7)
        DurationUnit.Months -> plusMonths(duration.value)
        DurationUnit.Years -> plusYears(duration.value)
        else -> plusDays(duration.inDays)
    }

    private fun plusDays(days: Double): LocalDate {
        if (days == 0.0) return this
        val numberOfDaysInMonth = month.numberOfDays(year)
        if ((dayOfMonth + days) <= numberOfDaysInMonth) {
            return LocalDateImpl(year, monthNumber, dayOfMonth + days.toInt())
        }
        var yearX = year
        var monthX = monthNumber + 1
        if (monthX == 13) {
            monthX = 1
            yearX += 1
        }
        return LocalDateImpl(yearX, monthX, 1).plusDays(dayOfMonth + days - numberOfDaysInMonth - 1)
    }

    private fun plusMonths(months: Double): LocalDate {
        if (months == 0.0) return this
        if (0.0 < months - floor(months)) { // if is fractional months, better use days
            return plusDays(30 * months)
        }
        if ((months + monthNumber) <= 12) {
            return LocalDateImpl(year, (monthNumber + months).toInt(), dayOfMonth)
        }

        if (months <= monthNumber) {
            return LocalDateImpl(year + 1, (monthNumber + months).toInt() % 12, dayOfMonth)
        }

        return LocalDateImpl(year + 1, 1, dayOfMonth).plusMonths(months + monthNumber - 12 - 1)
    }

    private fun plusYears(years: Double): LocalDate {
        if (years == 0.0) return this
        if (0.0 < years - floor(years)) { // if is fractional year. Better use months
            return plusMonths(years * 12)
        }
        return LocalDateImpl(year + years.toInt(), monthNumber, dayOfMonth)
    }

    override fun minus(duration: Duration): LocalDate = when (duration.unit) {
        DurationUnit.Days -> minusDays(duration.value)
        DurationUnit.Weeks -> minusDays(duration.value * 7)
        DurationUnit.Months -> minusMonths(duration.value)
        DurationUnit.Years -> minusYears(duration.value)
        else -> minusDays(duration.inDays)
    }

    private fun minusMonths(months: Double): LocalDate {
        if (months == 0.0) return this
        if (0.0 < months - floor(months)) {
            return minusDays(30 * months)
        }
        if (months <= monthNumber) {
            return LocalDateImpl(year, (monthNumber - months).toInt(), dayOfMonth)
        }
        if (months < 12) {
            val yearX = year - 1
            val monthX = (12 + monthNumber) - months.toInt()
            return LocalDateImpl(yearX, monthX, dayOfMonth)
        }
        return LocalDateImpl(year - 1, monthNumber, dayOfMonth).minusMonths(months - 12)
    }

    private fun minusDays(days: Double): LocalDate {
        if (days == 0.0) return this
        if (days <= dayOfMonth) {
            return LocalDateImpl(year, monthNumber, dayOfMonth - days.toInt())
        }
        var yearX = year
        var monthX = monthNumber - 1
        if (monthX == 0) {
            monthX = 12
            yearX -= 1
        }
        val numberOfDaysInMonth = Month.values()[monthX - 1].numberOfDays(yearX)
        return LocalDateImpl(yearX, monthX, numberOfDaysInMonth).minusDays(days - dayOfMonth)
    }

    private fun minusYears(years: Double): LocalDate {
        if (years == 0.0) return this
        if (0.0 < years - floor(years)) {
            return minusMonths(years * 12)
        }
        return LocalDateImpl(year - years.toInt(), monthNumber, dayOfMonth)
    }

    override fun isBefore(other: LocalDate): Boolean = this < other

    override fun isAfter(other: LocalDate): Boolean = this > other

    private fun diffSeconds(other: LocalDate): Long = compareTo(other).toLong() * 86400

    override fun toEpochMillisAsLong(): Long = diffSeconds(LocalDateEpoch()) * 1000

    override fun toEpochMillisAsDouble(): Double = toEpochMillisAsLong().toDouble()

    override fun toEpochMillisAsInt(): Int = toEpochMillisAsInt()

    override fun toString() = toIsoString()
}