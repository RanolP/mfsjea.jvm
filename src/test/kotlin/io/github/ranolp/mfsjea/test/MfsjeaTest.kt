package io.github.ranolp.mfsjea.test

import io.github.ranolp.mfsjea.Mfsjea
import io.github.ranolp.mfsjea.keyboard.*
import org.junit.Assert.assertEquals
import org.junit.Test

class MfsjeaTest {
    val mfsjea = Mfsjea.DEFAULT

    @Test
    fun testMfsjea() {
        val qwerty2 = mfsjea.jeamfsAuto("skqhrlrk durrudnj")
        assertEquals(qwerty2.sentence, "나보기가 역겨워")
        assertEquals(qwerty2.source, QwertyKeyboard)
        assertEquals(qwerty2.target, DubeolStandardKeyboard)

        val qwerty3 = mfsjea.jeamfsAuto("kfndw uurjchgs")
        assertEquals(qwerty3.sentence, "가실 때에는")
        assertEquals(qwerty3.source, QwertyKeyboard)
        assertEquals(qwerty3.target, Sebeol390Keyboard)

        val dvorak2 = mfsjea.jeamfsAuto("atueh'yen pden'dor")
        assertEquals(dvorak2.sentence, "말없이 고이보내")
        assertEquals(dvorak2.source, DvorakKeyboard)
        assertEquals(dvorak2.target, DubeolStandardKeyboard)

        val dvorak3 = mfsjea.jeamfsAuto("gifehxfegu")
        assertEquals(dvorak3.sentence, "드리우리다")
        assertEquals(dvorak3.source, DvorakKeyboard)
        assertEquals(dvorak3.target, Sebeol390Keyboard)

        val colemak2 = mfsjea.jeamfsAuto("slsqlrs; supger wirfettyPhc")
        assertEquals(colemak2.sentence, "영변에 약산 진달래꽃")
        assertEquals(colemak2.source, ColemakKeyboard)
        assertEquals(colemak2.target, DubeolStandardKeyboard)

        val colemak3 = mfsjea.jeamfsAuto("ntjdzlltlt etksweswnc oobjsnbjslt")
        assertEquals(colemak3.sentence, "아름따다 가실길에 뿌리우리다")
        assertEquals(colemak3.source, ColemakKeyboard)
        assertEquals(colemak3.target, Sebeol390Keyboard)
    }

    @Test
    fun testNumber() {
        val sebeol390Numbers = mfsjea.jeamfsAuto("NM<>JKLUIO")
        assertEquals(sebeol390Numbers.sentence, "0123456789")
        assertEquals(sebeol390Numbers.target, Sebeol390Keyboard)

        val sebeolFinalNumbers = mfsjea.jeamfsAuto("HJKL:YUIOP")
        assertEquals(sebeolFinalNumbers.sentence, "0123456789")
        assertEquals(sebeolFinalNumbers.target, SebeolFinalKeyboard)

        val sebeol390 = mfsjea.jeamfsAuto("<,MJU,JI>,LJI")
        assertEquals(sebeol390.sentence, "2,147,483,648")
        assertEquals(sebeol390.target, Sebeol390Keyboard)

        val sebeolFinal = mfsjea.jeamfsAuto("K<J:I<:OL<U:O")
        assertEquals(sebeolFinal.sentence, "2,147,483,648")
        assertEquals(sebeolFinal.target, SebeolFinalKeyboard)
    }
}
