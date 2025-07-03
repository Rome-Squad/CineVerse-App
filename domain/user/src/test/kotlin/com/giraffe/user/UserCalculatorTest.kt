package com.giraffe.user

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class UserCalculatorTest {
    val calculator = UserCalculator()

    @Test
    fun `should return 8 when user pass 4 , 4`() {
        //given
        val x = 4
        val y = 4
        //when
        val result = calculator.plus(x, y)
        //then
        assertThat(result).isEqualTo(8)
    }
}