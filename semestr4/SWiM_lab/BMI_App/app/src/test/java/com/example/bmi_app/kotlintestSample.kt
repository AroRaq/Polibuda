package com.example.bmi_app

import com.example.bmi_app.logic.BMI_kgcm
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.junit.Assert
import java.lang.IllegalArgumentException

class KotlinTextSample : StringSpec() {
    init {
        "for valid mass and height return bmi" {
            val bmi = BMI_kgcm(65.0, 170.0)
            bmi.calcBMI() shouldBeAround 22.491
        }
        "for mass below 20 throw exception" {
            val bmi = BMI_kgcm(10.0, 170.0)
            shouldThrow<IllegalArgumentException> {
                bmi.calcBMI()
            }
        }
    }


    private infix fun Double.shouldBeAround(value: Double) {
        Assert.assertEquals(value, this, 0.001)
    }
}

