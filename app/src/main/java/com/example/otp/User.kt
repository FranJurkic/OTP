package com.example.otp

import android.text.Editable
import java.io.Serializable

class User(username: String) : Serializable {

    var Username: String

    init {
        Username = username
    }
}