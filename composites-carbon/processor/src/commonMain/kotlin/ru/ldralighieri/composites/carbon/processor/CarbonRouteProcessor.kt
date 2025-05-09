/*
 * Copyright 2023 Vladimir Raupov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ldralighieri.composites.carbon.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import ru.ldralighieri.composites.carbon.core.CarbonRoute

/**
 * Carbon ksp [SymbolProcessor]
 */
internal class CarbonRouteProcessor(
    private val parser: CarbonRouteDataParser,
    private val generator: CarbonRouteGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(CarbonRoute::class.java.canonicalName)
            .toList()
            .filterIsInstance<KSClassDeclaration>()
            .forEach(::parse)

        return emptyList()
    }

    private fun parse(symbol: KSClassDeclaration) {
        when (val result = parser.parse(symbol)) {
            is CarbonRouteDataParser.Result.Success -> generator.generate(result.data)
            is CarbonRouteDataParser.Result.Failure -> logger.error(result.message, result.symbol)
        }
    }
}
