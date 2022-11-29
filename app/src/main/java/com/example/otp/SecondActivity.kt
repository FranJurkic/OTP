package com.example.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.otp.OTPGenerator


class SecondActivity : AppCompatActivity() {

    private lateinit var usernameText: TextView
    private lateinit var passwordText: TextView
    private lateinit var timerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        usernameText = findViewById(R.id.usernameText)
        passwordText = findViewById(R.id.passwordText)
        timerText = findViewById(R.id.timerText)

        usernameText.text = intent.getStringExtra("username")

        newPassword()
    }

    fun newPassword() {
        val otpGenerator = OTPGenerator()
        otpGenerator.generateNewPassword()

        passwordText.text = otpGenerator.password

        object : CountDownTimer(20000, 1000) {

            var sec: Long = 0
            var min: Long = 0

            override fun onTick(millisUntilFinished: Long) {
                sec = (millisUntilFinished / 1000) % 60
                min = millisUntilFinished / (1000 * 60) % 60
                timerText.text = "${String.format("%02d", min)}:${String.format("%02d", sec)}"
            }

            override fun onFinish() {
                newPassword()
            }
        }.start()
    }
}
