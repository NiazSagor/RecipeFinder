package com.example.recipefinder.data.model

import com.example.recipefinder.model.RecipeAnalyzedInstructionsItemVo
import com.example.recipefinder.model.StepVo


fun List<RecipeAnalyzedInstructionsItemVo>.toRecipeAnalyzedInstructionsInternalModel(): List<RecipeAnalyzedInstructions> {
    return this.map {
        it.toInternalRecipeAnalyzedInstructionsItem()
    }
}

fun RecipeAnalyzedInstructionsItemVo.toInternalRecipeAnalyzedInstructionsItem(): RecipeAnalyzedInstructions {
    return RecipeAnalyzedInstructions(
        steps = this.steps.toStepsInternalModel()
    )
}

fun List<StepVo>.toStepsInternalModel(): List<Step> {
    return this.map {
        it.toStepInternalModel()
    }
}

fun StepVo.toStepInternalModel(): Step {
    return Step(
        number = this.number,
        step = this.step
    )
}

data class RecipeAnalyzedInstructions(
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String
)
