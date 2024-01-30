package ru.ldralighieri.composites.carbon.processor.ext

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.asTypeName

internal fun Resolver.isEnumType(type: KSType): Boolean {
    val enum: KSType? =
        getClassDeclarationByName(Enum::class.asTypeName().canonicalName)?.asStarProjectedType()

    return enum?.run { isAssignableFrom(type) || makeNullable().isAssignableFrom(type) } ?: false
}
