package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // نمایشگر ماشین حساب
    private lateinit var display: TextView
    // عدد فعلی که کاربر وارد می‌کند
    private var currentNumber = ""
    // عدد اول برای عملیات ریاضی
    private var firstNumber = ""
    // نوع عملیات ریاضی (جمع، تفریق، ضرب، تقسیم)
    private var operation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // اتصال نمایشگر به کد
        display = findViewById(R.id.display)

        // لیست دکمه‌های اعداد
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        // تنظیم کلیک برای دکمه‌های اعداد
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                // اضافه کردن عدد به عدد فعلی
                currentNumber += (it as Button).text
                // نمایش عدد در نمایشگر
                display.text = currentNumber
            }
        }

        // تنظیم کلیک برای دکمه‌های عملیات ریاضی
        findViewById<Button>(R.id.btnPlus).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperation("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperation("/") }

        // دکمه پاک کردن
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            // پاک کردن همه متغیرها
            currentNumber = ""
            firstNumber = ""
            operation = ""
            // نمایش صفر در نمایشگر
            display.text = "0"
        }

        // دکمه مساوی
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            // اگر هر دو عدد وارد شده باشند، محاسبه انجام شود
            if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()) {
                calculate()
            }
        }

        // دکمه نقطه اعشار
        findViewById<Button>(R.id.btnDot).setOnClickListener {
            // اگر نقطه قبلاً وارد نشده باشد
            if (!currentNumber.contains(".")) {
                // اگر عدد خالی است، صفر اضافه شود
                if (currentNumber.isEmpty()) {
                    currentNumber = "0"
                }
                // اضافه کردن نقطه
                currentNumber += "."
                // نمایش عدد در نمایشگر
                display.text = currentNumber
            }
        }
    }

    // تنظیم عملیات ریاضی
    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            // ذخیره عدد فعلی به عنوان عدد اول
            firstNumber = currentNumber
            // پاک کردن عدد فعلی برای وارد کردن عدد دوم
            currentNumber = ""
            // ذخیره نوع عملیات
            operation = op
        }
    }

    // محاسبه نتیجه
    private fun calculate() {
        // تبدیل اعداد به اعشاری
        val num1 = firstNumber.toDouble()
        val num2 = currentNumber.toDouble()
        var result = 0.0

        // انجام عملیات بر اساس نوع آن
        when (operation) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "×" -> result = num1 * num2
            "/" -> {
                // جلوگیری از تقسیم بر صفر
                if (num2 != 0.0) {
                    result = num1 / num2
                } else {
                    display.text = "خطا"
                    return
                }
            }
        }

        // تبدیل نتیجه به رشته
        // اگر عدد صحیح است، صفرهای اعشار حذف شود
        currentNumber = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }

        // نمایش نتیجه
        display.text = currentNumber
        // ذخیره نتیجه به عنوان عدد اول برای عملیات بعدی
        firstNumber = currentNumber
    }
} 