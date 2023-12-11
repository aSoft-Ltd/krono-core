@file:JsExport

package krono

import kotlinx.JsExport

data class PresenterPattern(
    val date: String,
    val time: String,
    val dateTime: String
) {
    val formatter = PresenterFormatter(
        pattern = this,
        date = PureDateFormatter(date),
        time = PureTimeFormatter(time),
        dateTime = PureDateTimeFormatter(dateTime)
    )
}