/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.sentence.token

import com.kotlinnlp.linguisticdescription.morphology.Morphology
import com.kotlinnlp.linguisticdescription.sentence.token.properties.*

/**
 * A word token.
 *
 * @property id the id of the token, unique within its sentence
 * @property head the head of the token
 * @property deprel the dependency relation with the [head]
 * @property coReferences the list of co-references (can be null)
 * @property descendantsCount the amount of descendants of the token
 * @property semanticRelations the list of semantic relations (can be null)
 * @property surface the surface information
 * @property position the position of the token
 * @property diathesis the diathesis property (defined for verbs, otherwise null)
 * @property morphology the morphology of the token itself
 * @property contextMorphology the morphology of the token within a context (e.g. multi-words)
 */
data class Word(
  override val id: Int,
  override val head: Head,
  override val deprel: String,
  override val coReferences: List<CoReference>?,
  override val descendantsCount: Int,
  override val semanticRelations: List<SemanticRelation>?,
  val surface: Surface,
  val position: Position,
  val diathesis: Diathesis?, // null by default, only verbs have it defined
  val morphology: Morphology,
  val contextMorphology: Morphology
) : Token {

  /**
   * @return a string representation of this token
   */
  override fun toString(): String = """
    [%d] '%s' %s %s
        %s
        head: %s
        corefs: %s
        descendants: %d
        semantic relations: %s
        diathesis: %s
  """.trimIndent().format(
    this.id,
    this.surface.form,
    this.morphology.type.annotation,
    this.deprel,
    this.morphology,
    this.head,
    this.coReferences?.joinToString(separator = ", ") ?: "None",
    this.descendantsCount,
    this.semanticRelations?.joinToString(separator = ", ") ?: "None",
    this.diathesis ?: "None"
  )
}