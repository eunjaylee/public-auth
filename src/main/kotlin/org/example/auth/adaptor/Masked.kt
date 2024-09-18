package org.example.auth.adaptor

// @JsonSerialize(using = MaskedSerializer::class)
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Masked(
    val maskWith: Char = '*',
    val option: String = "PRE",
)
