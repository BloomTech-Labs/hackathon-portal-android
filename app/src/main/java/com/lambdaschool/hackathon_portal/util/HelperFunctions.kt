package com.lambdaschool.hackathon_portal.util

import com.google.gson.JsonObject
import com.lambdaschool.hackathon_portal.model.SelectiveJsonObjectBuilder


/**
 * Builds a JsonObject of only fields that the user has changed, such that only those fields
 * are sent to the BE to be updated.
 *
 * Can also specify to check for empty strings such that
 *
 * @param comparatorList : List<SelectiveJsonObjectBuilder>
 * @return JsonObject?
 * */
fun _selectiveJsonObjectBuilder(comparatorList: List<SelectiveJsonObjectBuilder>): JsonObject? {
    val jsonObject = JsonObject()

    var counter = 0
    comparatorList.forEach {
        val editTextString = it.editText.text.toString()

        if (editTextString != it.oldString) {
            if (it.checkIfEmpty) {
                if (editTextString.isNotEmpty()) {
                    jsonObject.addProperty(it.jsonField, editTextString)
                    counter++
                } else {
                    it.editText.error = "Field is Required"
                }
            } else {
                jsonObject.addProperty(it.jsonField, editTextString)
                counter++
            }
        }
    }

    return if (counter > 0) {
        jsonObject
    } else {
        null
    }
}