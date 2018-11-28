package com.razvanpopescu.firebaseauth.models

class User{

    var email: String? = null
    var password: String? = null
    var username: String? = null

    constructor(
        email: String?,
        password: String?,
        username: String?)
    {
        this.email = email
        this.password = password
        this.username = username
    }

}