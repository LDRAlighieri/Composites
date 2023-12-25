package ru.ldralighieri.composites.carbon.core

@Target(AnnotationTarget.CLASS)
annotation class CarbonRoute(val route: String, val deeplinkSchema: String = "")

// TODO The default value cannot be defined via KSP, so we will need to explicitly specify the
//  value via this annotation
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DefaultValue(val value: String)
