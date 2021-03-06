/* Copyright 2017-present The KotlinNLP Authors. All Rights Reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 * ------------------------------------------------------------------*/

package com.kotlinnlp.linguisticdescription.sentence.token

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import com.kotlinnlp.linguisticdescription.POSTag
import com.kotlinnlp.linguisticdescription.morphology.Morphology
import com.kotlinnlp.linguisticdescription.morphology.ScoredSingleMorphology
import com.kotlinnlp.linguisticdescription.morphology.SingleMorphology
import com.kotlinnlp.linguisticdescription.sentence.token.properties.*
import com.kotlinnlp.linguisticdescription.syntax.SyntacticDependency
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * A token with morphological and syntactic properties.
 */
sealed class MorphoSynToken : TokenIdentificable {

  /**
   * The type of token.
   *
   * @property annotation the type annotation
   */
  enum class Type(val annotation: String) {
    Word("WORD"),
    Trace("TRACE"),
    WordTrace("WORD-TRACE"),
    WordComposite("WORD-COMPOSITE")
  }

  /**
   * The label that defines the type of this token.
   */
  abstract val type: Type

  /**
   * The list of single morphologies (flattened in case of composite token).
   */
  abstract val flatMorphologies: List<SingleMorphology>

  /**
   * The list of flattened syntactic relations.
   */
  abstract val flatSyntacticRelations: List<SyntacticRelation>

  /**
   * The flattened list of POS.
   */
  abstract val flatPOS: List<POSTag>

  /**
   * The syntactic relation.
   */
  abstract val syntacticRelation: SyntacticRelation

  /**
   * @return the JSON object that represents this token
   */
  abstract fun toJSON(): JsonObject

  /**
   * @param token another morpho-syn token
   * @param weakMatch whether to allows the match between undefined properties or not (default false)
   *
   * @return true if this token matches at least one morphology of the given one, otherwise false
   */
  fun agreeMorphology(token: MorphoSynToken, weakMatch: Boolean = false): Boolean {
    val tokenMorphologies: List<SingleMorphology> = token.flatMorphologies
    return this.flatMorphologies.any { tokenMorphologies.any { tMorpho -> tMorpho.agree(it, weakMatch = weakMatch) } }
  }

  /**
   * @param syntaxClass the [KClass] of a [SyntacticDependency]
   *
   * @return true if this token is a dependent of the given type, otherwise false
   */
  fun <T : SyntacticDependency.Base>isDependentAs(syntaxClass: KClass<T>): Boolean =
    this.flatSyntacticRelations.any { it.dependency::class.isSubclassOf(syntaxClass) }

  /**
   * @param prefix a prefix to prepend to each line that composes the string representation of this token
   *
   * @return a prefixed string representation of this token
   */
  fun toString(prefix: String): String = prefix + this.toString().replace("\n", "\n" + prefix)

  /**
   * A single [MorphoSynToken].
   */
  @Suppress("PropertyName")
  abstract class Single : MorphoSynToken(), ScoredMorphoToken, SyntacticToken {

    /**
     * The Part-Of-Speech.
     */
    override val pos: POSTag? get() = this._pos

    /**
     * A list containing the single POS of this token.
     */
    override val flatPOS: List<POSTag> get() = this._pos?.let { listOf(it) } ?: listOf()

    /**
     * The list of scored single morphologies, sorted by descending score.
     */
    override val morphologies: List<ScoredSingleMorphology> get() = this._morphologies.toList()

    /**
     * The list of possible scored single morphologies of context, sorted by descending score (can be empty).
     */
    override val contextMorphologies: List<ScoredSingleMorphology> get() = this._contextMorphologies.toList()

    /**
     * The list of single morphologies.
     * This property should be accessed only if this token contains only [Morphology.Type.Single] morphologies.
     */
    override val flatMorphologies: List<SingleMorphology> get() = this._morphologies.map { it.value }

    /**
     * The syntactic relation.
     */
    override val syntacticRelation: SyntacticRelation get() = this._syntacticRelation

    /**
     * The list of syntactic relations. It contains always one single element.
     */
    override val flatSyntacticRelations: List<SyntacticRelation> get() = listOf(this._syntacticRelation)

    /**
     * The list of co-references (can be null).
     */
    override val coReferences: List<CoReference> get() = this._coReferences.toList()

    /**
     * The list of semantic relations (can be null).
     */
    override val semanticRelations: List<SemanticRelation> get() = this._semanticRelations.toList()

    /**
     * The variable Part-Of-Speech.
     */
    protected var _pos: POSTag? = null

    /**
     * The mutable list of scored morphologies, sorted by descending score.
     */
    protected val _morphologies: MutableList<ScoredSingleMorphology> = mutableListOf()

    /**
     * The list of possible scored single morphologies of context, sorted by descending score (can be empty).
     */
    protected val _contextMorphologies: MutableList<ScoredSingleMorphology> = mutableListOf()

    /**
     * The variable syntactic relation.
     */
    protected lateinit var _syntacticRelation: SyntacticRelation

    /**
     * The mutable list of co-references.
     */
    protected val _coReferences: MutableList<CoReference> = mutableListOf()

    /**
     * The mutable list of semantic relations.
     */
    protected val _semanticRelations: MutableList<SemanticRelation> = mutableListOf()

    /**
     * Update the [pos] replacing it with a given one.
     *
     * @param pos the POS with which to replace the current one
     */
    fun updatePOS(pos: POSTag) {
      this._pos = pos
    }

    /**
     * Add a new morphology.
     *
     * @param morphology the morphology to add
     */
    fun addMorphology(morphology: ScoredSingleMorphology) {
      this._morphologies.add(morphology)
    }

    /**
     * Remove the morphology at a given index.
     *
     * @param index an index of a morphology in [morphologies]
     */
    fun removeMorphology(index: Int) {
      this._morphologies.removeAt(index)
    }

    /**
     * Remove all the morphologies.
     */
    fun removeAllMorphologies() {
      this._morphologies.clear()
    }

    /**
     * Add a new context morphology.
     *
     * @param morphology the morphology to add
     */
    fun addContextMorphology(morphology: ScoredSingleMorphology) {
      this._contextMorphologies.add(morphology)
    }

    /**
     * Remove the context morphology at a given index.
     *
     * @param index an index of a morphology in [contextMorphologies]
     */
    fun removeContextMorphology(index: Int) {
      this._contextMorphologies.removeAt(index)
    }

    /**
     * Remove all the context morphologies.
     */
    fun removeAllContextMorphologies() {
      this._contextMorphologies.clear()
    }

    /**
     * Update the [syntacticRelation] replacing it with a given one.
     *
     * @param syntacticRelation the syntactic relation with which to replace the current one
     */
    fun updateSyntacticRelation(syntacticRelation: SyntacticRelation) {
      this._syntacticRelation = syntacticRelation
    }

    /**
     * Add a new co-reference.
     *
     * @param coReference the co-reference to add
     */
    fun addCoReference(coReference: CoReference) {
      this._coReferences.add(coReference)
    }

    /**
     * Remove the co-reference at a given index.
     * It is required that the [coReferences] list has been set.
     *
     * @param index the index of a co-reference in [coReferences]
     */
    fun removeCoReference(index: Int) {
      this._coReferences.removeAt(index)
    }

    /**
     * Remove all the co-references.
     */
    fun removeAllCoreferences() {
      this._coReferences.clear()
    }

    /**
     * Add a new semantic relation.
     *
     * @param semanticRelation the semantic relation to add
     */
    fun addSemanticRelation(semanticRelation: SemanticRelation) {
      this._semanticRelations.add(semanticRelation)
    }

    /**
     * Remove the semantic relation at a given index.
     * It is required that the [semanticRelations] list has been set.
     *
     * @param index the index of a semantic relation in [semanticRelations]
     */
    fun removeSemanticRelation(index: Int) {
      this._semanticRelations.removeAt(index)
    }

    /**
     * Remove all the semantic relations.
     */
    fun removeAllSemanticRelations() {
      this._semanticRelations.clear()
    }

    /**
     * @return the JSON object that represents this token
     */
    override fun toJSON(): JsonObject {

      val jsonObject = json {

        val self = this@Single

        require(self.pos is POSTag.Base) {
          "The JSON representation of a token cannot be get if the property 'pos' is not a POSTag.Base."
        }

        obj(
          "id" to self.id,
          "type" to self.type.annotation,
          "posBase" to (self.pos as POSTag.Base).type.baseAnnotation,
          "dependency" to self.syntacticRelation.toJSON(),
          "coReferences" to array(self.coReferences.map { it.toJSON() }),
          "semanticRelations" to array(self.semanticRelations.map { it.toString() }),
          "morphology" to array(self.morphologies.map { it.toJSON() })
        )
      }

      if (this is FormToken) {
        jsonObject["form"] = this.form
        jsonObject["translitForm"] = this.form // TODO: set it properly
      }

      if (this is Positionable) {
        jsonObject["position"] = this.position.toJSON()
      }

      return jsonObject
    }
  }

  /**
   * A [MorphoSynToken] with more single components.
   *
   * @property id the id of the token, unique within its sentence
   * @property form the form of the token
   * @property position the position of the token
   */
  class Composite(
    override val id: Int,
    override val form: String,
    override val position: Position,
    val components: List<Word>
  ) : RealToken, TokenIdentificable, MorphoSynToken() {

    /**
     * The label that defines the type of this token.
     */
    override val type: Type = Type.WordComposite

    /**
     * The set of ids of the components.
     */
    val componentsIds: Set<Int> by lazy { this.components.asSequence().map { it.id }.toSet() }

    /**
     * The list of syntactic relations. It contains always more than one element.
     */
    override val flatSyntacticRelations: List<SyntacticRelation> get() = this.components.map { it.syntacticRelation }

    /**
     * The list of the flattened single morphologies of the components.
     */
    override val flatMorphologies: List<SingleMorphology> get() =
      this.components.flatMap { component ->  component.morphologies.map { it.value } }

    /**
     * A list containing the single POS of this token.
     */
    override val flatPOS: List<POSTag> get() = this.components.mapNotNull { it.pos }

    /**
     * The syntactic relation.
     */
    override val syntacticRelation: SyntacticRelation get() =
      this.components.first { it.syntacticRelation.governor !in this.componentsIds }.syntacticRelation

    /**
     * @return a string representation of this token
     */
    override fun toString(): String = """
    [%d] '%s'
        components:
          %s
  """.trimIndent().format(
      this.id,
      this.form,
      this.components.joinToString("\n") { it.toString(prefix = "      ") }
    )

    /**
     * @return the JSON object that represents this token
     */
    override fun toJSON(): JsonObject = json {

      val self = this@Composite

      obj(
        "id" to self.id,
        "type" to self.type.annotation,
        "form" to self.form,
        "translitForm" to self.form, // TODO: set it properly
        "position" to self.position.toJSON(),
        "components" to array(self.components.map { it.toJSON() })
      )
    }
  }
}
