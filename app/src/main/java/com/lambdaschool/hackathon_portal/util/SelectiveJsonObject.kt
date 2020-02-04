package com.lambdaschool.hackathon_portal.util

import android.widget.EditText
import com.google.gson.JsonObject
import timber.log.Timber

private val selectiveJsonObjectList: MutableList<SelectiveJsonObject> = mutableListOf()
private val TAG = "SELECTIVE_JSON_OBJECT"

/**
 * Builds a gson.JsonObject of only fields that have been changed by a user instead of sending
 * an entire data class with information that doesn't need to be updated.
 *
 * Most useful when making PUT requests to a Backend.
 *
 * Example:
 *
 * button_save.setOnClickListener {
 *      val selectiveJsonObject = SelectiveJsonObject.Builder()
 *                                      .add("field_1", edit_text_view_1, "sample", false)
 *                                      .add("field_2", edit_text_view_2, 5, false)
 *                                      .add("field_3", edit_text_view_3, someObject.name, true)
 *                                      .add("field_4", edit_text_view_4, someObject.age, true)
 *                                      .add("field_5", switchState, someObject.isEnabled)
 *                                      .add("field_6", someObject.intValue, 4)
 *                                      .build()
 *
 *      if (selectiveJsonObject != null) {
 *          updateBackend(selectiveJsonObject)
 *      }
 * }
 * */
sealed class SelectiveJsonObject {

    class Builder: SelectiveJsonObject() {

        /**
         * COMPARE Boolean against a Boolean:
         *
         * Adds Boolean values to the list to check for changes; if found, it will add the
         * gson.JsonObject property when built.
         *
         * @param jsonField: String
         * @param newValue: Boolean
         * @param originalValue: Boolean?
         *
         * @return Builder::class
         * */
        fun add(jsonField: String,
                newValue: Boolean,
                originalValue: Boolean?): Builder {
            selectiveJsonObjectList.add(CompareBooleans(jsonField, newValue, originalValue))
            return Builder()
        }

        /**
         * COMPARE EditText field against a String?:
         *
         * Adds an EditText view to the list to check for changes; if found, it will add the
         * gson.JsonObject property when built.
         *
         * NOTE: The input type of that EditText field will produce a gson.JsonObject field
         * value that is a String.
         *
         * @param jsonField: String
         * @param newValue: EditText
         * @param originalValue: String?
         * @param checkIfEmpty: Boolean
         *
         * @return Builder::class
         * */
        fun add(jsonField: String,
                newValue: EditText,
                originalValue: String?,
                checkIfEmpty: Boolean): Builder {
            selectiveJsonObjectList.add(CompareEditTextWithString(jsonField, newValue, originalValue, checkIfEmpty))
            return Builder()
        }

        /**
         * COMPARE EditText field against an Int:
         *
         * Adds an EditText view to the list to check for changes; if found, it will add the
         * gson.JsonObject property when built.
         *
         * NOTE: The input type of that EditText field will produce a gson.JsonObject field
         * value that is an Int.
         *
         * *** Be sure to elect on the EditText view `android:inputType="number"` ***
         *
         * @param jsonField: String
         * @param newValue: EditText
         * @param originalValue: Int
         * @param checkIfEmpty: Boolean
         *
         * @return Builder::class
         * */
        fun add(jsonField: String,
                newValue: EditText,
                originalValue: Int,
                checkIfEmpty: Boolean): Builder {
            selectiveJsonObjectList.add(CompareEditTextWithInt(jsonField, newValue, originalValue, checkIfEmpty))
            return Builder()
        }

        /**
         * COMPARE Int against an Int?:
         *
         * Adds Int values to the list to check for changes; if found, it will add the
         * gson.JsonObject property when built.
         *
         * @param jsonField: String
         * @param newValue: Int
         * @param originalValue: Int?
         *
         * @return Builder::class
         * */
        fun add(jsonField: String,
                newValue: Int,
                originalValue: Int?): Builder {
            selectiveJsonObjectList.add(CompareInts(jsonField, newValue, originalValue))
            return Builder()
        }

        /**
         * COMPARE String against a String?:
         *
         * Adds String values to the list to check for changes; if found, it will add the
         * gson.JsonObject property when built.
         *
         * @param jsonField: String
         * @param newValue: String
         * @param originalValue: String?
         * @param checkIfEmpty: Boolean
         *
         * @return Builder::class
         * */
        fun add(jsonField: String,
                newValue: String,
                originalValue: String?,
                checkIfEmpty: Boolean): Builder {
            selectiveJsonObjectList.add(CompareStrings(jsonField, newValue, originalValue, checkIfEmpty))
            return Builder()
        }

        /**
         * Builds the gson.GsonObject when called and clears the selectiveJsonObjectList variable.
         *
         * @return JsonObject
         *   -OR-
         * @return null
         * */
        fun build(): JsonObject? {
            val jsonObject = buildJsonObject(selectiveJsonObjectList)
            selectiveJsonObjectList.clear()
            return jsonObject
        }

        /**
         * Temporary solution until app wide logging can be incorporated.
         * */
        private fun logDebug(message: String) {
            Timber.d(message)
        }

        /**
         * Builds a gson.JsonObject of only fields that the user has changed such that only those changes
         * are sent to the Backend.
         *
         * Can specify to check for empty values in some cases so that if there was a difference between
         * the oldValue and newValue, but that newValue is an empty string, it will not be added to the
         * gson.JsonObject if you don't want it to be (most useful when using with EditText fields).
         *
         * If you elect to check for empty strings with an EditText view, it will automatically
         * display an error in that EditText view and return a `null` instead of the gson.JsonObject.
         *
         * @param selectiveJsonObjectList : MutableList<SelectiveJsonObject>
         * @return gson.JsonObject
         *   -OR-
         * @return null
         * */
        private fun buildJsonObject(selectiveJsonObjectList: MutableList<SelectiveJsonObject>): JsonObject? {
            var isEmpty = false
            val jsonObject = JsonObject()

            selectiveJsonObjectList.forEach {
                when (it) {

                    is CompareBooleans -> {
                        if (it.jsonField.isNotEmpty()) {

                            if (it.originalValue != it.newValue) {
                                jsonObject.addProperty(it.jsonField, it.newValue)
                            } // else do nothing

                        } else {
                            logDebug("jsonField is empty")
                        }
                    }

                    is CompareEditTextWithString -> {
                        if (it.jsonField.isNotEmpty()) {
                            val etString = it.newValue.text.toString()

                            if (etString != it.originalValue) {

                                if (it.checkIfEmpty) {

                                    if (etString.isNotEmpty()) {
                                        jsonObject.addProperty(it.jsonField, etString)
                                    } else {
                                        isEmpty = true
                                        it.newValue.error = "Required"
                                    }

                                } else {
                                    jsonObject.addProperty(it.jsonField, etString)
                                }

                            } // else do nothing

                        } else {
                            logDebug("jsonField is empty")
                        }
                    }

                    is CompareEditTextWithInt -> {
                        if (it.jsonField.isNotEmpty()) {
                            val etString = it.newValue.text.toString()

                            if (etString.isNotEmpty()) {

                                if (etString.toInt() != it.originalValue) {
                                    jsonObject.addProperty(it.jsonField, etString.toInt())
                                } // else do nothing

                            } else if (it.checkIfEmpty) {
                                isEmpty = true
                                it.newValue.error = "Required"
                            } // else do nothing

                        } else {
                            logDebug("jsonField is empty")
                        }
                    }

                    is CompareInts -> {
                        if (it.jsonField.isNotEmpty()) {

                            if (it.originalValue != it.newValue) {
                                jsonObject.addProperty(it.jsonField, it.newValue)
                            } // else do nothing

                        } else {
                            logDebug("jsonField is empty")
                        }
                    }

                    is CompareStrings -> {
                        if (it.jsonField.isNotEmpty()) {

                            if (it.originalValue != it.newValue) {

                                if (it.checkIfEmpty) {

                                    if (it.newValue.isNotEmpty()) {
                                        jsonObject.addProperty(it.jsonField, it.newValue)
                                    } else {
                                        isEmpty = true
                                    }

                                } else {
                                    jsonObject.addProperty(it.jsonField, it.newValue)
                                }
                            }

                        } else {
                            logDebug("jsonField is empty")
                        }
                    }
                }
            }

            return when {
                isEmpty -> null
                (jsonObject.size() > 0) -> jsonObject
                else -> null
            }
        }

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    private inner class CompareBooleans(
        val jsonField: String,
        val newValue: Boolean,
        val originalValue: Boolean?
    ) : SelectiveJsonObject()

    private inner class CompareEditTextWithString(
        val jsonField: String,
        val newValue: EditText,
        val originalValue: String?,
        val checkIfEmpty: Boolean
    ) : SelectiveJsonObject()

    private inner class CompareEditTextWithInt(
        val jsonField: String,
        val newValue: EditText,
        val originalValue: Int?,
        val checkIfEmpty: Boolean
    ) : SelectiveJsonObject()

    private inner class CompareInts(
        val jsonField: String,
        val newValue: Int,
        val originalValue: Int?
    ) : SelectiveJsonObject()

    private inner class CompareStrings(
        val jsonField: String,
        val newValue: String,
        val originalValue: String?,
        val checkIfEmpty: Boolean
    ) : SelectiveJsonObject()
}