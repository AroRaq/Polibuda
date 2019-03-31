package com.example.bmi_app

import com.example.bmi_app.logic.BMI_kgcm
import com.example.bmi_app.logic.BMI_lbsin
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.junit.Assert
import java.lang.IllegalArgumentException

class CalculatorTest : StringSpec() {
    init {
        "for valid mass and height in metric return bmi" {
            val bmi = BMI_kgcm(65.0, 170.0)
            bmi.calcBMI() shouldBeAround 22.491
        }
        "for limit mass and height in metric return bmi" {
            val bmi = BMI_kgcm(20.0, 80.0)
            bmi.calcBMI() shouldBeAround 31.25
        }
        "for mass below 20 kg throw exception" {
            val bmi = BMI_kgcm(19.9, 170.0)
            shouldThrow<IllegalArgumentException> {
                bmi.calcBMI()
            }
        }
        "for height below 80 cm throw exception" {
            val bmi = BMI_kgcm(100.0, 79.9)
            shouldThrow<IllegalArgumentException> {
                bmi.calcBMI()
            }
        }
        "for valid mass and height in imperial return bmi" {
            val bmi = BMI_lbsin(154.0, 70.0)
            bmi.calcBMI() shouldBeAround 22.094
        }

        "for limit mass and height in imperial return bmi" {
            val bmi = BMI_lbsin(44.0, 31.0)
            bmi.calcBMI() shouldBeAround 32.187
        }
        "for mass below 44 lbs throw exception" {
            val bmi = BMI_lbsin(43.9, 170.0)
            shouldThrow<IllegalArgumentException> {
                bmi.calcBMI()
            }
        }
        "for height below 31 throw exception" {
            val bmi = BMI_kgcm(100.0, 30.9)
            shouldThrow<IllegalArgumentException> {
                bmi.calcBMI()
            }
        }
    }


    private infix fun Double.shouldBeAround(value: Double) {
        Assert.assertEquals(value, this, 0.001)
    }
}

