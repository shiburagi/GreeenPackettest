package com.example.greeenpacket_test.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import java.lang.StringBuilder

class UserDetailViewModel : ViewModel() {

    fun parseDuties(duties: Any?): String {
        val builder = StringBuilder()
        if (duties is JsonArray) {
            val jsonArray: JsonArray = duties as JsonArray
            jsonArray.forEach {
                if (it.isJsonPrimitive)
                    builder.append(it.asString)
                else if (it.isJsonObject) {
                    val jsonObject = it.asJsonObject
                    jsonObject.keySet().forEach { key ->
                        val jsonElement = jsonObject.get(key);
                        if (jsonElement.isJsonPrimitive) {
                            builder.append(key).append(" ").append(jsonElement.asString)
                        } else if (jsonElement.isJsonArray) {
                            val array = jsonElement.asJsonArray
                            var i = 0;
                            array.forEach { element ->
                                if (i++ == 0)
                                    builder.append(key).append(" ")
                                        .append(element.asString)
                                else
                                    builder.append(" ")
                                        .append(element.asString)
                            }
                        }
                    }


                }

            }
        } else if (duties != null) {
            builder.append(duties)
        }

        return builder.toString()
    }
}