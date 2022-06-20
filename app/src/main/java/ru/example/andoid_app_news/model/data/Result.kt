package ru.example.andoid_app_news.model.data

class Result<T>(
    var status: Status,
    var value: T
) {
    enum class Status {
        DEFAULT, LOADING, SUCCESSED, FAILED
    }
}