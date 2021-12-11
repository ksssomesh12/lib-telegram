package io.ktln.lib.telegram

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

val gson = Gson()
val regexCamelCase = "(?<=[a-zA-Z])[A-Z]".toRegex()
val regexSnakeCase = "_[a-zA-Z]".toRegex()

/**
 Convert a String from camel case to snake case
 */
fun String.camelToSnakeCase(): String {
    return regexCamelCase.replace(this) {
        "_${it.value}".lowercase()
    }
}

/**
 Convert a String from snake case to camel case
 */
fun String.snakeToCamelCase(): String {
    return regexSnakeCase.replace(this) {
        it.value.replace("_", "").uppercase().replaceFirstChar { res ->
            if (res.isLowerCase()) res.titlecase(Locale.getDefault()) else res.toString()
        }
    }
}

/**
 Convert a String (JSON formatted) to a Map
 */
fun String.toMap(): Map<String, Any> {
    return gson.fromJson(this, object : TypeToken<Map<String, Any>>() {}.type)
}

/**
 Convert a Map to a Data Class
 */
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

/**
 Convert a Data Class to a Map
 */
fun <T> T.toMap(): Map<String, Any> {
    return convert()
}

/**
 Convert an Object of type I to type O
 */
inline fun <I, reified O> I.convert(): O {
    return gson.fromJson(gson.toJson(this), object : TypeToken<O>() {}.type)
}
