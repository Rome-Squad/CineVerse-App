package com.giraffe.movies

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CalculatorTest {
    val calculator = Calculator()

    @Test
    fun `should return 6 when user pass 5 , 1`() {
        //given
        val x = 5
        val y = 1
        //when
        val result = calculator.plus(x, y)
        //then
        assertThat(result).isEqualTo(6)
    }
}