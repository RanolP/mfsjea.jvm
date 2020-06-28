package io.github.ranolp.mfsjea.test

import io.github.ranolp.mfsjea.Mfsjea
import io.github.ranolp.mfsjea.classifier.BracketClassifier
import io.github.ranolp.mfsjea.classifier.WordClassifier
import io.github.ranolp.mfsjea.classifier.with
import io.github.ranolp.mfsjea.grader.*
import io.github.ranolp.mfsjea.keyboard.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MfsjeaTest {
    private val mfsjea = Mfsjea.DEFAULT

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

    @Test
    fun testHangulMixedSentence() {
        val changed = mfsjea.jeamfsAuto("한글 tjRdls 두벌식 znjxl 테tm트")
        assertEquals(changed.sentence, "한글 섞인 두벌식 쿼티 테스트")
        assertEquals(changed.source, QwertyKeyboard)
        assertEquals(changed.target, DubeolStandardKeyboard)

        val changed2 = mfsjea.jeamfsAuto("ㅍㅜㄹㅇㅓㅆㅡㅈㅣㅁㅏ!")
        assertEquals(changed2.sentence, "ㅍㅜㄹㅇㅓㅆㅡㅈㅣㅁㅏ!")

        val changed3 = mfsjea.jeamfsAuto("Aㅏ 이것eh dks됨.")
        assertEquals(changed3.sentence, "Aㅏ 이것도 안됨.")
    }

    @Test
    fun testEscapers() {
        val mfsjea = Mfsjea.DEFAULT.extend(
            classifier = {
                BracketClassifier('[', ']')
            }
        )
        mfsjea.jeamfsAuto("[explorer.exe]는 explorer.exe이 아니라고!!").apply {
            assertEquals(sentence, "explorer.exe는 유게내앧융이 아니라고!!")
        }
        mfsjea.jeamfsAuto("explorer.exe은 [explorer.exe]가 아니라고!!").apply {
            assertEquals(sentence, "유게내앧융은 explorer.exe가 아니라고!!")
        }
        mfsjea.jeamfsAuto("[React]").apply {
            assertEquals(sentence, "React")
        }
        mfsjea.jeamfsAuto("sid [s]").apply {
            assertEquals(sentence, "냥 s")
        }
        mfsjea.jeamfsAuto("[s] sid").apply {
            assertEquals(sentence, "s 냥")
        }
    }

    @Test
    fun testOriginality() {
        assertTrue(mfsjea.jeamfsAuto("test case").score <= 0)
        assertTrue(mfsjea.jeamfsAuto("(?)").score <= 0)
        assertTrue(mfsjea.jeamfsAuto("!!!").score <= 0)
    }

    @Test
    fun testByWordConversion() {
        val mfsjea = Mfsjea.DEFAULT.extend(
            classifier = { it.with(WordClassifier) }
        )
        mfsjea.jeamfsAuto("dlrjtdms test answkddlqslek.").apply {
            assertEquals(sentence, "이것은 test 문장입니다.")
        }
        mfsjea.jeamfsAuto("jekd jd2hgs sentence ugwjgs mfskgwk/f Alphabet ufsjtkf nt!je jd2ng3hduf.").apply {
            assertEquals(sentence, "여기 있는 sentence 들은 한글과 Alphabet 단어가 섞여 있습니다.")
        }
    }

    @Test
    fun testHangulGraders() {
        val hangul2350Mfsjea = Mfsjea.DEFAULT.extend(
            graders = { listOf(Hangul2350Grader, NumberGrader, ParenthesisGrader, IncompleteWordGrader, AsciiGrader) }
        )
        val hangulFrequencyMfsjea = Mfsjea.DEFAULT.extend(
            graders = {
                listOf(
                    HangulFrequencyGrader,
                    NumberGrader,
                    ParenthesisGrader,
                    IncompleteWordGrader,
                    AsciiGrader
                )
            }
        )
        hangulFrequencyMfsjea.jeamfsAuto("jtC jdkts ibjtq...").apply {
            assertNotEquals(sentence, hangul2350Mfsjea.jeamfsAuto("jtC jdkts ibjtq..."))
        }
    }
}
