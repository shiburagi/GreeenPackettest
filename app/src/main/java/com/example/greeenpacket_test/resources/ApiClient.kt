package com.example.greeenpacket_test.resources

import android.util.Log
import com.example.greeenpacket_test.models.User
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.lang.reflect.Field
import java.lang.reflect.Type

/**
 * Class to provide and maintain retrofit client
 */
class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://dummy-research.firebaseio.com"

        /**
         * Initialize and configure retrofit's client
         */
        val client: Retrofit? by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            val gson: Gson = GsonBuilder()
                .registerTypeAdapter(User::class.java, UserDeserializer())
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            retrofit
        }
    }
}

/**
 * Class to deserialize json data for user object
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UserDeserializer : JsonDeserializer<User?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): User {
        val jsonObject: JsonObject = json.asJsonObject
        val user = User()
        val fields: Array<Field> = user.javaClass.declaredFields
        fields.forEach {
            var name: String = it.name
            try {
                val annotation: SerializedName =
                    it.getAnnotation(SerializedName::class.java) as SerializedName
                name = annotation.value
            } catch (e: Exception) {
            }
            if (jsonObject.has(name)) {
                it.isAccessible = true
                val jsonValue: JsonElement = jsonObject[name]
                var value: Any? = null
                when {
                    jsonValue.isJsonPrimitive -> { // this value is primitive
                        val jsonPrimitive: JsonPrimitive = jsonValue.asJsonPrimitive
                        when {
                            jsonPrimitive.isBoolean -> value =
                                jsonValue.asBoolean
                            jsonPrimitive.isString -> value =
                                jsonValue.asString
                            jsonPrimitive.isNumber -> {
                                value = jsonValue.asNumber
                            }
                        }
                    }
                    jsonValue.isJsonArray -> { // this value is jsonArray
                        val builder = StringBuilder()
                        parseArray(jsonValue, builder)
                        value = builder.toString()
                    }
                    else -> { // otherwise
                        val builder = StringBuilder()
                        parseObject(jsonValue, builder)
                        value = builder.toString()
                    }
                }
                try {
                    it.set(user, value)
                } catch (e: Exception) {
                    Log.e(this.javaClass.simpleName, e.message)
                }
            }
        }

        return user
    }


    /**
     * method to convert [JsonElement] to String
     * @param data json array in format of[JsonElement]
     * @param builder [StringBuilder]
     */
    private fun parseArray(data: JsonElement, builder: StringBuilder) {
        val jsonArray: JsonArray = data.asJsonArray
        jsonArray.forEach {
            if (it.isJsonPrimitive)
                builder.append(it.asString)
            else if (it.isJsonObject) {
                parseObject(it, builder)
            }
        }
    }

    /**
     * method to convert [JsonElement] to String
     * @param data json object in format of [JsonElement]
     * @param builder [StringBuilder]
     */
    private fun parseObject(data: JsonElement, builder: StringBuilder) {
        val jsonObject = data.asJsonObject
        jsonObject.keySet().forEach { key ->
            val jsonElement = jsonObject.get(key)
            if (jsonElement.isJsonPrimitive) {
                builder.append(key).append(" ").append(jsonElement.asString)
            } else if (jsonElement.isJsonArray) {
                builder.append(key).append(" ")
                parseArray(jsonElement, builder)
            }
        }
    }
}