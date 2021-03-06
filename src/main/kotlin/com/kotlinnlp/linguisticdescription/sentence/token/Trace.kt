/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.sentence.token

import com.kotlinnlp.linguisticdescription.POSTag
import com.kotlinnlp.linguisticdescription.morphology.ScoredSingleMorphology
import com.kotlinnlp.linguisticdescription.sentence.token.properties.CoReference
import com.kotlinnlp.linguisticdescription.sentence.token.properties.SyntacticRelation
import com.kotlinnlp.linguisticdescription.sentence.token.properties.SemanticRelation

/**
 * A trace token.
 *
 * @property id the id of the token, unique within its sentence
 */
class Trace(override val id: Int) : MorphoSynToken.Single() {

  companion object {

    /**
     * Build a [Trace] with the given properties already assigned.
     *
     * @param id the id of the token, unique within its sentence
     * @param pos the Part-Of-Speech
     * @param morphologies the list of scored single morphologies, sorted by descending score
     * @param contextMorphologies the list of scored single morphologies of context, sorted by descending score
     * @param syntacticRelation the syntactic relation with the governor
     * @param coReferences the list of co-references (can be null)
     * @param semanticRelations the list of semantic relations (can be null)
     *
     * @return a new token with the given properties
     */
    operator fun invoke(
      id: Int,
      pos: POSTag?,
      morphologies: List<ScoredSingleMorphology>,
      contextMorphologies: List<ScoredSingleMorphology>,
      syntacticRelation: SyntacticRelation,
      coReferences: List<CoReference>?,
      semanticRelations: List<SemanticRelation>?
    ): Trace {

      val token = Trace(id)

      token._pos = pos
      token._morphologies.addAll(morphologies)
      token._contextMorphologies.addAll(contextMorphologies)
      token._syntacticRelation = syntacticRelation
      coReferences?.let { token._coReferences.addAll(it) }
      semanticRelations?.let { token._semanticRelations.addAll(it) }

      return token
    }
  }

  /**
   * The label that defines the type of this token.
   */
  override val type: Type = Type.Trace

  /**
   * @return a string representation of this token
   */
  override fun toString(): String = """
    [%d] TRACE
        dependency: %s
        co-references: %s
        semantic relations: %s
  """.trimIndent().format(
    this.id,
    this.syntacticRelation,
    this.coReferences.joinToString(separator = ", ").ifBlank { "None" },
    this.semanticRelations.joinToString(separator = ", ").ifBlank { "None" }
  )
}
