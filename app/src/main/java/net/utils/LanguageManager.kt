package net.utils

import android.text.TextUtils

open class LanguageManager {
    companion object {
        private var instance: LanguageManager? = null
            get() {
                if (field == null) {
                    field = LanguageManager()
                }
                return field
            }

        @Synchronized
        fun get(): LanguageManager {
            return instance!!
        }
    }

    fun getLanguage(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["Chinese"] = "zh"
        map["Arabic"] = "ar"
        map["Catalan"] = "ca"
        map["Czech"] = "cs"
        map["danish"] = "da"
        map["German"] = "de"
        map["Greek"] = "el"
        map["English"] = "en"
        map["Esperanto"] = "eo"
        map["Spanish"] = "es"
        map["Basque"] = "eu"
        map["farsi"] = "fa"
        map["Finnish"] = "fi"
        map["French"] = "fr"
        map["Hebrew"] = "he"
        map["Croatian"] = "hr"
        map["Hungarian"] = "hu"
        map["Bahasa Indonesia"] = "id"
        map["Italy"] = "it"
        map["Japanese"] = "ja"
        map["Kartuli"] = "ka"
        map["Korean"] = "ko"
        map["Lithuanian"] = "lt"
        map["Macedonian"] = "mk"
        map["Malaysia"] = "ms"
        map["Dutch"] = "nl"
        map["Norwegian"] = "no"
        map["polish"] = "pl"
        map["Portugal"] = "pt"
        map["Romanian"] = "ro"
        map["Russian"] = "ru"
        map["Slovenian"] = "sl"
        map["Swedish"] = "sv"
        map["Tamil"] = "ta"
        map["Thai"] = "th"
        map["Turkish"] = "tr"
        map["Ukrainian"] = "uk"
        map["Vietnamese"] = "vi"
        map["Cantonese"] = "zh-yue"
        return map
    }

    fun getAllCountry(data: HashMap<String, String>): ArrayList<String> {
        val list = ArrayList<String>()
        if (data.size != 0) {
            for (item: String in data.keys) {
                list.add(item)
            }
        }
        return list
    }

    fun getCode(data: HashMap<String, String>, country: String): String {
        var code = ""
        if (TextUtils.isEmpty(country))
            return ""
        for (item: String in data.keys) {
            if (TextUtils.equals(item, country)) {
                code = data[item]!!
                break
            }
        }
        return code
    }

    fun getCountry(data: HashMap<String, String>, code: String): String {
        var country = ""
        if (TextUtils.isEmpty(code))
            return ""
        for (item: String in data.keys) {
            if (TextUtils.equals(code, data[item])) {
                country = item
            }
        }
        return country
    }
}