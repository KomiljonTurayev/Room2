package com.goldstein.room2.viewmodel

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.coroutines.cancellation.CancellationException

inline fun <R> resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}

@Keep
class BaseResponse<T>(
    val success: Boolean,
    val data: T,
    val error: ResponseError
)

@Keep
@Parcelize
class ResponseError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
) : Parcelable
