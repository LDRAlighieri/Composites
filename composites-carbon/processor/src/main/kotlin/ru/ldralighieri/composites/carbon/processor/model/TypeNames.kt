package ru.ldralighieri.composites.carbon.processor.model

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import ru.ldralighieri.composites.carbon.core.CarbonRoute

internal val carbonAnnotationTypeName: TypeName = CarbonRoute::class.java.asTypeName()

internal val intTypeName: TypeName = Int::class.asTypeName()
internal val intNullableTypeName: TypeName = intTypeName.copy(nullable = true)

internal val longTypeName: TypeName = Long::class.asTypeName()
internal val longNullableTypeName: TypeName = longTypeName.copy(nullable = true)

internal val floatTypeName: TypeName = Float::class.asTypeName()
internal val floatNullableTypeName: TypeName = floatTypeName.copy(nullable = true)

internal val booleanTypeName: TypeName = Boolean::class.asTypeName()
internal val booleanNullableTypeName: TypeName = booleanTypeName.copy(nullable = true)

internal val stringTypeName: TypeName = String::class.asTypeName()
internal val stringNullableTypeName: TypeName = stringTypeName.copy(nullable = true)

internal val validTypes: List<TypeName> = listOf(
    intTypeName, intNullableTypeName,
    longTypeName, longNullableTypeName,
    floatTypeName, floatNullableTypeName,
    booleanTypeName, booleanNullableTypeName,
    stringTypeName, stringNullableTypeName,
)
