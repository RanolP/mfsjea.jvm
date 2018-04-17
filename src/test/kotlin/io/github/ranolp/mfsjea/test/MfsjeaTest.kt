package io.github.ranolp.mfsjea.test

import io.github.ranolp.mfsjea.Mfsjea
import org.junit.Assert.assertEquals
import org.junit.Test

class MfsjeaTest {

    @Test
    fun testMfsjea() {
        val qwerty2 = Mfsjea.jeamfsAuto("skqhrlrk durrudnj")
        assertEquals(qwerty2.str, "나보기가 역겨워")
        assertEquals(qwerty2.alphabetName, "Qwerty")
        assertEquals(qwerty2.hangulName, "두벌식 표준")

        val qwerty3 = Mfsjea.jeamfsAuto("kfndw uurjchgs")
        assertEquals(qwerty3.str, "가실 때에는")
        assertEquals(qwerty3.alphabetName, "Qwerty")
        assertEquals(qwerty3.hangulName, "세벌식 390")

        val dvorak2 = Mfsjea.jeamfsAuto("atueh'yen pden'dor")
        assertEquals(dvorak2.str, "말없이 고이보내")
        assertEquals(dvorak2.alphabetName, "Dvorak")
        assertEquals(dvorak2.hangulName, "두벌식 표준")

        val dvorak3 = Mfsjea.jeamfsAuto("gifehxfegu")
        assertEquals(dvorak3.str, "드리우리다")
        assertEquals(dvorak3.alphabetName, "Dvorak")
        assertEquals(dvorak3.hangulName, "세벌식 390")

        val colemak2 = Mfsjea.jeamfsAuto("slsqlrs; supger wirfettyPhc")
        assertEquals(colemak2.str, "영변에 약산 진달래꽃")
        assertEquals(colemak2.alphabetName, "Colemak")
        assertEquals(colemak2.hangulName, "두벌식 표준")

        val colemak3 = Mfsjea.jeamfsAuto("ntjdzlltlt etksweswnc oobjsnbjslt")
        assertEquals(colemak3.str, "아름따다 가실길에 뿌리우리다")
        assertEquals(colemak3.alphabetName, "Colemak")
        assertEquals(colemak3.hangulName, "세벌식 390")
    }
    @Test
    fun testNumber() {
        val sebeol390Numbers = Mfsjea.jeamfsAuto("NM<>JKLUIO")
        assertEquals(sebeol390Numbers.str, "0123456789")
        assertEquals(sebeol390Numbers.alphabetName, "Qwerty")
        assertEquals(sebeol390Numbers.hangulName, "세벌식 390")

        val sebeolFinalNumbers = Mfsjea.jeamfsAuto("HJKL:YUIOP")
        assertEquals(sebeolFinalNumbers.str, "0123456789")
        assertEquals(sebeolFinalNumbers.alphabetName, "Qwerty")
        assertEquals(sebeolFinalNumbers.hangulName, "세벌식 최종")

        val sebeol390 = Mfsjea.jeamfsAuto("<,MJU,JI>,LJI")
        assertEquals(sebeol390.str, "2,147,483,648")
        assertEquals(sebeol390.alphabetName, "Qwerty")
        assertEquals(sebeol390.hangulName, "세벌식 390")

        val sebeolFinal = Mfsjea.jeamfsAuto("K<J:I<:OL<U:O")
        assertEquals(sebeolFinal.str, "2,147,483,648")
        assertEquals(sebeolFinal.alphabetName, "Qwerty")
        assertEquals(sebeolFinal.hangulName, "세벌식 최종")
    }
}
