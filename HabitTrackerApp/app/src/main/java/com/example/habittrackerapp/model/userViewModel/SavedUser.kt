package com.example.habittrackerapp.model.userViewModel

/**
 * this is the basic information we need to save of the user to allow them to still be signIn even they
 * stop running the application
 */
data class SavedUser (
    var email: String = "",
    var password: String = "",
)