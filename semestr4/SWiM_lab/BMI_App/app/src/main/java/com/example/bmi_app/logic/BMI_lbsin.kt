package com.example.bmi_app.logic

class BMI_lbsin (var weight: Double, var height: Double) : BMI {
    override fun calcBMI(): Double {
        require(weight >= 44 && height >= 31) {"Wrong data"}
        return weight*703.0/(height*height)
    }
}