package com.example.habittrackerapp.habit

import com.example.habittrackerapp.R

/**
 * Class that holds the type of habits. Each one has a name and an image id.
 */
sealed class HabitType(val name : String, val id : Int) {
    object Chores: HabitType("Chores", R.drawable.bucket_303265_1280 )

    object Health: HabitType("Health", R.drawable.vegetables_7540182_1280)

    object Fitness: HabitType("Fitness", R.drawable.home_gym_6037677_1280)

    object Reading: HabitType("Reading", R.drawable.black_and_white_4270982_1280)

    object Sleep: HabitType("Sleep", R.drawable.kitten_1456832_1280)

    object Organization: HabitType("Organization", R.drawable.checklist_2023731_1280)

    object Other: HabitType("Other", R.drawable.brain_5983810_1280)

}

