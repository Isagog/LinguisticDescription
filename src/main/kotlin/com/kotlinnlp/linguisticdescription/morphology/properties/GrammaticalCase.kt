/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.morphology.properties

/**
 * The 'grammatical case' grammatical property.
 *
 * @property annotation the string used to annotate this property
 */
enum class GrammaticalCase(override val annotation: String) : GrammaticalProperty {
  Undefined("_"),
  Unknown("UNKNOWN"),
  Nominative("SUBJ"),
  Accusative("OBJ"),
  Dative("IOBJ"),
  Vocative("VOC"),
  Partitive("PART"),
  Locative("LOC"),
  Neuter("NEUT"),
  Empty("EMPTY")
}
