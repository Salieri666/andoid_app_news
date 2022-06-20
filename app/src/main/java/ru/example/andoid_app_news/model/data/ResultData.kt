package ru.example.andoid_app_news.model.data

class ResultData<T>(
    var status: Status,
    var value: T?
) {
    enum class Status {
        DEFAULT, LOADING, SUCCESSES, FAILED
    }

    constructor() : this(Status.DEFAULT, null)

    constructor(status: Status) : this(status, null)
}