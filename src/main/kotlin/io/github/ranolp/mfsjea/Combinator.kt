package io.github.ranolp.mfsjea

interface Combinator {
    companion object {
        val NOP = object : Combinator {
            override fun combine(data: String): String = data
        }
    }

    fun combine(data: String): String
}
