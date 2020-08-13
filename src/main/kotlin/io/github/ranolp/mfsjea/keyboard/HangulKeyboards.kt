package io.github.ranolp.mfsjea.keyboard

import io.github.ranolp.mfsjea.Combinator
import java.text.Normalizer

private val HANGUL_SYLLABLE_2 = "([ㄱ-ㅎ])([ㅏ-ㅣ]{1,2})([ㄱ-ㅎ]{0,2})(?![ㅏ-ㅣ])".toRegex()
private val HANGUL_SYLLABLE_3 = "([ᄀ-ᄒ]+)([ᅡ-ᅵ]+)([ᆨ-ᇂ]*)".toRegex()

private const val STD_CHO = "ᄀᄁᄂᄃᄄᄅᄆᄇᄈᄉᄊᄋᄌᄍᄎᄏᄐᄑᄒ"
private const val STD_JUNG = "ᅡᅢᅣᅤᅥᅦᅧᅨᅩᅪᅫᅬᅭᅮᅯᅰᅱᅲᅳᅴᅵ"
private const val STD_JONG = "ᆨᆩᆪᆫᆬᆭᆮᆯᆰᆱᆲᆳᆴᆵᆶᆷᆸᆹᆺᆻᆼᆽᆾᆿᇀᇁᇂ"

private const val COMPAT_CHO = "ㄱㄲㄳㄴㄵㄶㄷㄸㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅃㅄㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"
private const val COMPAT_JUNG = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ"

private const val CONVERT_CHO = "ᄀᄁ ᄂ  ᄃᄄᄅ       ᄆᄇᄈ ᄉᄊᄋᄌᄍᄎᄏᄐᄑᄒ"
private const val CONVERT_JONG = "ᆨᆩᆪᆫᆬᆭᆮ ᆯᆰᆱᆲᆳᆴᆵᆶᆷᆸ ᆹᆺᆻᆼᆽ ᆾᆿᇀᇁᇂ"

private fun convertCompatToStdCho(c: String): String =
    c.map { CONVERT_CHO[COMPAT_CHO.indexOf(it)] }.joinToString("")

private fun convertCompatToStdJung(c: String): String =
    c.map { STD_JUNG[COMPAT_JUNG.indexOf(it)] }.joinToString("")

private fun convertCompatToStdJong(c: String): String =
    if (c.isEmpty()) ""
    else c.map { CONVERT_JONG[COMPAT_CHO.indexOf(it)] }.joinToString("")

private fun convertStdToCompat(s: String): String = s.map { c ->
    listOf(
        Pair(CONVERT_CHO.indexOf(c), COMPAT_CHO),
        Pair(STD_JUNG.indexOf(c), COMPAT_JUNG),
        Pair(CONVERT_JONG.indexOf(c), COMPAT_CHO)
    ).filter { it.first != -1 }.map { it.second[it.first] }.firstOrNull() ?: c
}.joinToString("")

/**
 * Dubeol standard(두벌식 표준) keyboard.
 */
object DubeolStandardKeyboard : OutputKeyboard("두벌식 표준", TextSet.HANGUL_DUBEOL) {
    private val JUNG_TABLE = mapOf(
        "ㅗㅏ" to 'ㅘ',
        "ㅗㅐ" to 'ㅙ',
        "ㅗㅣ" to 'ㅚ',
        "ㅜㅓ" to 'ㅝ',
        "ㅜㅔ" to 'ㅞ',
        "ㅜㅣ" to 'ㅟ',
        "ㅡㅣ" to 'ㅢ'
    )
    private val JONG_TABLE = mapOf(
        "ㄱㅅ" to 'ㄳ',
        "ㄴㅈ" to 'ㄵ',
        "ㄴㅎ" to 'ㄶ',
        "ㄹㄱ" to 'ㄺ',
        "ㄹㅁ" to 'ㄻ',
        "ㄹㅂ" to 'ㄼ',
        "ㄹㅅ" to 'ㄽ',
        "ㄹㅌ" to 'ㄾ',
        "ㄹㅍ" to 'ㄿ',
        "ㄹㅎ" to 'ㅀ',
        "ㅂㅅ" to 'ㅄ'
    )
    override val combinator = object : Combinator {
        override fun combine(sentence: String): String {
            return sentence.replace(HANGUL_SYLLABLE_2) {
                val (cho, jung, jong) = it.destructured

                val convertedCho = convertCompatToStdCho(cho)
                val convertedJung = convertCompatToStdJung(JUNG_TABLE[jung]?.toString() ?: jung)
                val convertedJong = convertCompatToStdJong(JONG_TABLE[jong]?.toString() ?: jong)

                convertStdToCompat(
                    Normalizer.normalize(
                        convertedCho + convertedJung + convertedJong,
                        Normalizer.Form.NFC
                    )
                )
            }
        }
    }


    private const val LAYOUT =
        " `~1!2@3#4$5%6^7&8*9(0)-_=+\\|ㅂㅃㅈㅉㄷㄸㄱㄲㅅㅆㅛㅛㅕㅕㅑㅑㅐㅒㅔㅖ[{]}ㅁㅁㄴㄴㅇㅇㄹㄹㅎㅎㅗㅗㅓㅓㅏㅏㅣㅣ;:\'\"ㅋㅋㅌㅌㅊㅊㅍㅍㅠㅠㅜㅜㅡㅡ,<.>/?"

    override fun getCharacter(code: Int): Char = LAYOUT[code]
}

/**
 * Sebeol 390(세벌식 390) keyboard
 */
object Sebeol390Keyboard : OutputKeyboard("세벌식 390", TextSet.HANGUL_SEBEOL) {
    private val CHO_TABLE = mapOf(
        "ᄀᄀ" to 'ᄁ',
        "ᄃᄃ" to 'ᄄ',
        "ᄇᄇ" to 'ᄈ',
        "ᄉᄉ" to 'ᄊ',
        "ᄌᄌ" to 'ᄍ'
    )
    private val JUNG_TABLE = mapOf(
        "ᅩᅡ" to 'ᅪ',
        "ᅩᅢ" to 'ᅫ',
        "ᅩᅵ" to 'ᅬ',
        "ᅮᅥ" to 'ᅯ',
        "ᅮᅦ" to 'ᅰ',
        "ᅮᅵ" to 'ᅱ',
        "ᅳᅵ" to 'ᅴ'
    )
    private val JONG_TABLE = mapOf(
        "ᆨᆨ" to 'ᆩ',
        "ᆨᆺ" to 'ᆪ',
        "ᆫᆽ" to 'ᆬ',
        "ᆫᇂ" to 'ᆭ',
        "ᆯᆨ" to 'ᆰ',
        "ᆯᆷ" to 'ᆱ',
        "ᆯᆸ" to 'ᆲ',
        "ᆯᆺ" to 'ᆳ',
        "ᆯᇀ" to 'ᆴ',
        "ᆯᇁ" to 'ᆵ',
        "ᆯᇂ" to 'ᆶ',
        "ᆸᆺ" to 'ᆹ',
        "ᆺᆺ" to 'ᆻ'
    )
    override val combinator = object : Combinator {
        override fun combine(sentence: String): String {
            return sentence.replace(HANGUL_SYLLABLE_3) {
                val (cho, jung, jong) = it.destructured

                val convertedCho = CHO_TABLE[cho]?.toString() ?: cho
                val convertedJung = JUNG_TABLE[jung]?.toString() ?: jung
                val convertedJong = JONG_TABLE[jong]?.toString() ?: jong

                convertStdToCompat(
                    Normalizer.normalize(convertedCho + convertedJung + convertedJong, Normalizer.Form.NFC)
                )
            }
        }
    }


    private const val LAYOUT =
        " `~ᇂᆽᆻ@ᆸ#ᅭ\$ᅲ%ᅣ^ᅨ&ᅴ*ᅮ(ᄏ)-_=+\\|ᆺᇁᆯᇀᅧᆿᅢᅤᅥ;ᄅ<ᄃ7ᄆ8ᄎ9ᄑ>[{]}ᆼᆮᆫᆭᅵᆰᅡᆩᅳ/ᄂ\'ᄋ4ᄀ5ᄌ6ᄇ:ᄐ\"ᆷᆾᆨᆹᅦᆱᅩᆶᅮ!ᄉ0ᄒ1,2.3ᅩ?"

    override fun getCharacter(code: Int): Char = LAYOUT[code]
}

/**
 * Sebeol final(세벌식 최종) keyboard
 */
object SebeolFinalKeyboard : OutputKeyboard("세벌식 최종", TextSet.HANGUL_SEBEOL) {
    private val CHO_TABLE = mapOf(
        "ᄀᄀ" to 'ᄁ',
        "ᄃᄃ" to 'ᄄ',
        "ᄇᄇ" to 'ᄈ',
        "ᄉᄉ" to 'ᄊ',
        "ᄌᄌ" to 'ᄍ'
    )
    private val JUNG_TABLE = mapOf(
        "ᅩᅡ" to 'ᅪ',
        "ᅩᅢ" to 'ᅫ',
        "ᅩᅵ" to 'ᅬ',
        "ᅮᅥ" to 'ᅯ',
        "ᅮᅦ" to 'ᅰ',
        "ᅮᅵ" to 'ᅱ',
        "ᅳᅵ" to 'ᅴ'
    )
    override val combinator = object : Combinator {
        override fun combine(sentence: String): String {
            return sentence.replace(HANGUL_SYLLABLE_3) {
                val (cho, jung, jong) = it.destructured

                val convertedCho = CHO_TABLE[cho]?.toString() ?: cho
                val convertedJung = JUNG_TABLE[jung]?.toString() ?: jung

                // STRICT MODE
                convertStdToCompat(
                    Normalizer.normalize(convertedCho + convertedJung + jong, Normalizer.Form.NFC)
                )
            }
        }
    }


    private const val LAYOUT =
        " *※ᇂᆩᆻᆰᆸᆽᅭᆵᅲᆴᅣ=ᅨ“ᅴ”ᅮ\'ᄏ~);>+:\\ᆺᇁᆯᇀᅧᆬᅢᆶᅥᆳᄅ5ᄃ6ᄆ7ᄎ8ᄑ9(%</ᆼᆮᆫᆭᅵᆲᅡᆱᅳᅤᄂ0ᄋ1ᄀ2ᄌ3ᄇ4ᄐ·ᆷᆾᆨᆹᅦᆿᅩᆪᅮ?ᄉ-ᄒ\",,..ᅩ!"

    override fun getCharacter(code: Int): Char = LAYOUT[code]
}
