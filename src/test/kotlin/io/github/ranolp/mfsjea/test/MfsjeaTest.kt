package io.github.ranolp.mfsjea.test

import io.github.ranolp.mfsjea.Mfsjea
import org.junit.Assert.assertEquals
import org.junit.Test

class MfsjeaTest {

    @Test
    fun `test dubeolsik`() {
        val qwerty_dubeolsik = Mfsjea.jeamfsAuto("znjxl - enqjftlr dlqslek.")
        assertEquals(qwerty_dubeolsik.str, "쿼티 - 두벌식 입니다.")
        assertEquals(qwerty_dubeolsik.alphabetName, "Qwerty")
        assertEquals(qwerty_dubeolsik.hangulName, "두벌식 표준")
        val dvorak_dubeolsik = Mfsjea.jeamfsAuto("itoege;npt eto ahpehefv")
        assertEquals(dvorak_dubeolsik.alphabetName, "Dvorak")
        assertEquals(dvorak_dubeolsik.hangulName, "두벌식 표준")
        assertEquals(dvorak_dubeolsik.str, "한영키가 안 먹어요.")
        assertEquals(Mfsjea.jeamfsAuto("0vwupxndjv kcogwksx").str, "콜맥으로 세벌식")
    }
}
