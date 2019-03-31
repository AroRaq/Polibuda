package com.example.bmi_app.logic

class BMI_kgcm (var weight: Double, var height: Double) : BMI {
    override fun calcBMI(): Double {
        require(weight >= 20 && height >= 80) {"Wrong data"}
        return weight*10000.0/(height*height)
    }
}