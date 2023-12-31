@file:JsExport

package krono

import kotlinx.JsExport

interface TemporalComparable<in E> : Comparable<E> {
    fun isBefore(other: E): Boolean = this < other
    fun isAfter(other: E): Boolean = this > other
}