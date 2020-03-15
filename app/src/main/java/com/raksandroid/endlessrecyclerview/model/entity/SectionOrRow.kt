package com.raksandroid.endlessrecyclerview.model.entity

// In a fuller example, this would probably hold more data than just strings.
class SectionOrRow {
    var row: String? = null
        private set
    var section: String? = null
        private set
    internal var isRow = false

    fun isRow(): Boolean {
        return isRow
    }

    companion object {
        fun createRow(row: String?): SectionOrRow {
            val ret =
                SectionOrRow()
            ret.row = row
            ret.isRow = true
            return ret
        }

        fun createSection(section: String?): SectionOrRow {
            val ret =
                SectionOrRow()
            ret.section = section
            ret.isRow = false
            return ret
        }
    }
}