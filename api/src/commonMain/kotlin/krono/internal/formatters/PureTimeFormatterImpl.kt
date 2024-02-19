package krono.internal.formatters

import krono.PureTimeFormatter

@PublishedApi
internal class PureTimeFormatterImpl(private val pattern: String) : PureTimeFormatter {

    override fun formatTime(hour: Int, minute: Int, seconds: Int): String {
        val HH = hour.to2digits
        val H = hour.toString()
        val smallHour = hour % 12
        val hh = smallHour.to2digits
        val h = smallHour.toString()
        val mm = minute.to2digits
        val m = minute.toString()
        val ss = seconds.to2digits
        val s = seconds.toString()
        val ampm = if (HH.toInt() < 12) "am" else "pm"

        return pattern
            .replace("{HH}", HH)
            .replace("{H}", H)
            .replace("{hh}", hh)
            .replace("{h}", h)
            .replace("{mm}", mm)
            .replace("{m}", m)
            .replace("{ss}", ss)
            .replace("{s}", s)
            .replace("{ampm}", ampm)
            .replace("{AMPM}", ampm.uppercase())
    }
}