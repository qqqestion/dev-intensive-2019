package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrEmpty() || fullName.isNullOrBlank()) {
            return Pair(null, null)
        }
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val transMap = hashMapOf(
            Pair("а", "a"),
            Pair("б", "b"),
            Pair("в", "v"),
            Pair("г", "g"),
            Pair("д", "d"),
            Pair("е", "e"),
            Pair("ё", "e"),
            Pair("ж", "zh"),
            Pair("з", "z"),
            Pair("и", "i"),
            Pair("й", "i"),
            Pair("к", "k"),
            Pair("л", "l"),
            Pair("м", "m"),
            Pair("н", "n"),
            Pair("о", "o"),
            Pair("п", "p"),
            Pair("р", "r"),
            Pair("с", "s"),
            Pair("т", "t"),
            Pair("у", "u"),
            Pair("ф", "f"),
            Pair("х", "h"),
            Pair("ц", "c"),
            Pair("ч", "ch"),
            Pair("ш", "sh"),
            Pair("щ", "sh'"),
            Pair("ъ", ""),
            Pair("ы", "i"),
            Pair("ь", ""),
            Pair("э", "e"),
            Pair("ю", "yu"),
            Pair("я", "ya"),
            Pair(" ", divider)
        )
        var newString = ""
        for (char in payload) {
            if (transMap.containsKey(char.toLowerCase().toString())) {
                newString += if (char.isUpperCase()) {
                    transMap[char.toLowerCase().toString()]!!.capitalize()
                } else {
                    transMap[char.toLowerCase().toString()]
                }
            } else {
                newString += char
            }
        }
        return newString
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val trimmedFirstName = firstName?.trim()
        val trimmedLastName = lastName?.trim()
        var initials = "${if (trimmedFirstName?.getOrNull(0)
                ?.toUpperCase() == null
        ) "" else trimmedFirstName?.get(0)
            ?.toUpperCase().toString()}${if (trimmedLastName?.getOrNull(0)
                ?.toUpperCase() == null
        ) "" else trimmedLastName?.get(0)
            ?.toUpperCase().toString()}"
        return if (initials == "") {
            null
        } else {
            initials
        }
    }
}