package com.jehutyno.blablacartestvalentinlanfranchi


interface Converter<in I, out O> {
    fun convert(input: I): O
    fun convert(inputs: List<I>?): List<O>? = inputs?.map(::convert)
}