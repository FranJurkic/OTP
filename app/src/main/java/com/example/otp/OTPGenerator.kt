package com.example.otp

import java.util.*

class OTPGenerator {

    lateinit var password: String
    var passwordLength: Int = 6

    val rand = Random()

    fun generateNewPassword() {
        password = getRandomString(passwordLength)
        formatPassword()
    }

    fun formatPassword() {
        var tempPassword = ""
        for (i in 0 until passwordLength) {
            tempPassword += password[i] + " "
        }
        password = tempPassword
    }

    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }.joinToString("")
    }
}