package com.giraffe.user

import com.giraffe.series.SeriesCalculator
import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class SeriesCalculatorTest {
    val calculator = SeriesCalculator()

    @Test
    fun `should return 0 when user pass 5 , 5`() {
        //given
        val x = 5
        val y = 5
        //when
        val result = calculator.minus(x, y)
        //then
        assertThat(result).isEqualTo(0)
    }
}