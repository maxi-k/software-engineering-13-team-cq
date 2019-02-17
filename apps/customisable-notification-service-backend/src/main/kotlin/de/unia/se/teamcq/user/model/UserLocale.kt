package de.unia.se.teamcq.user.model

import java.util.Locale

enum class UserLocale(val localeFormat: Locale) {
    DE(Locale.GERMAN),
    EN(Locale.ENGLISH)
}
