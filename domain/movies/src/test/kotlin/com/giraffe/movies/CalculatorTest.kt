package com.giraffe.movies

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class CalculatorTest {
    val calculator = Calculator()

    @Test
    fun `should return 5 when user pass 2 , 3`() {
        //given
        val x = 2
        val y = 2
        //when
        val result = calculator.plus(x, y)
        //then
        assertThat(result).isEqualTo(5)
    }
}