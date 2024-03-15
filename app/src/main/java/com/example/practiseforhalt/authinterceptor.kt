package com.example.practiseforhalt
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class authinterceptor(private val sharedPreferences: SharedPreferences, private val requiresAuth: Boolean) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Check if authorization is required for this request
        if (requiresAuth) {
            // Retrieve the token from SharedPreferences
            val token = sharedPreferences.getString("token", "")

            // Add authorization header if token is present
            val requestBuilder = originalRequest.newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val request = requestBuilder.build()
            return chain.proceed(request)
        }

        return chain.proceed(originalRequest)
    }
}
