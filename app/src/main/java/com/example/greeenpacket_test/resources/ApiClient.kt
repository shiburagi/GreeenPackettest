package com.example.greeenpacket_test.resources

import android.util.Log
import com.example.greeenpacket_test.models.User
import com.google.gson.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Type
import java.util.*
import java.util.function.Consumer


class ApiClient {

    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URL = "https://dummy-research.firebaseio.com";
        val client: Retrofit?
            get() {
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
                return retrofit
            }
    }
}

class UserDeserializer : JsonDeserializer<User?> {
    @ExperimentalStdlibApi
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): User {
        val jsonObject: JsonObject = json.asJsonObject;

        val user: User = User()
        val fields: Array<Field> = user.javaClass.declaredFields

        fields.forEach {
            if (jsonObject.has(it.name)) {
                it.isAccessible = true
                val jsonValue: JsonElement = jsonObject[it.name]
                var value: Any? = null;
                if (jsonValue.isJsonPrimitive) {
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
                } else {
                    value = jsonValue
                }
                try {
                    it.set(user, value);
//                    User::class.java.getDeclaredMethod("set${it.name.capitalize(Locale.getDefault())}").invoke(user, value)
                } catch (e: Exception) {
                    Log.e("User",e.message)
                }
            }
        }

        return user
    }
}