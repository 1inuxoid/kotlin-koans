package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

val monthLength: Array<Int> = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

fun MyDate.nextDate(): MyDate {
    when {
        dayOfMonth < monthLength[month - 1]
        || (this.year % 4 == 0 && this.month == 2 && dayOfMonth == monthLength[1])
        -> return MyDate(year, month, dayOfMonth + 1)

        month < 12 -> return MyDate(year, month + 1, 1)
        else -> MyDate(year + 1, 1, 1)
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override fun contains(value: MyDate): Boolean {
        return start <= value && value <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> {
        return object: Iterator<MyDate> {
            var currentDate = start

            override fun hasNext(): Boolean {
                return currentDate <= endInclusive
            }

            override fun next(): MyDate {
                if (hasNext()) currentDate = currentDate.nextDate()
                return currentDate
            }
        }
    }
}
