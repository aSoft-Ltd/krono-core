@file:Suppress("NOTHING_TO_INLINE")

package krono

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

inline fun Clock.currentJavaLocalDateTime() = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentMillisAsLong()), ZoneId.of("UTC"))

inline fun LocalDate.toLocalDate() = java.time.LocalDate.of(year, monthNumber, dayOfMonth)

inline fun java.time.LocalDate.toLocalDate() = LocalDate(year, month.value, dayOfMonth)