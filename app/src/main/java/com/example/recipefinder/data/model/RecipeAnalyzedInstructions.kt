package com.example.recipefinder.data.model

import com.example.recipefinder.model.RecipeAnalyzedInstructionsItemVo
import com.example.recipefinder.model.StepVo

fun RecipeAnalyzedInstructionsItemVo.toInternalRecipeAnalyzedInstructionsItem(): RecipeAnalyzedInstructions {
    return RecipeAnalyzedInstructions(
        steps = this.steps.toStepsInternalModel()
    )
}

fun List<StepVo>.toStepsInternalModel(): List<Step> {
    val map: List<Step> = this.map { stepVo: StepVo ->
        stepVo.toStepInternalModel()
    }
    return map
}

fun StepVo.toStepInternalModel(): Step {
    return Step(
        number = this.number,
        step = this.step
    )
}

// internal
data class RecipeAnalyzedInstructions(
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String
)
