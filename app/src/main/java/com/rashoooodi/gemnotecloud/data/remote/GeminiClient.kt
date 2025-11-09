package com.rashoooodi.gemnotecloud.data.remote

import com.rashoooodi.gemnotecloud.data.settings.GeminiSettingsStore
import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

class GeminiClient(
    private val http: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) {

    private val baseUrl: HttpUrl =
        "https://generativelanguage.googleapis.com/v1beta/models".toHttpUrl()

    fun summarize(transcript: String, model: String, apiKey: String): String {
        val sanitizedKey = apiKey.trim()
        if (sanitizedKey.isEmpty()) {
            return "Provide a Gemini API key in Settings to enable summaries."
        }

        val prompt =
            "Summarize the following transcript in 3-5 bullets and keep it concise: $transcript"

        return callGemini(prompt, model.trim(), sanitizedKey)
            .getOrElse { error ->
                "Gemini request failed: ${error.message ?: "unexpected error"}"
            }
    }

    fun tags(transcript: String, model: String, apiKey: String): List<String> {
        val sanitizedKey = apiKey.trim()
        if (sanitizedKey.isEmpty()) {
            return emptyList()
        }

        val prompt = "Generate up to 5 short tags (1-2 words) separated by commas for: $transcript"

        val response = callGemini(prompt, model.trim(), sanitizedKey)
            .getOrElse { return emptyList() }

        return response
            .split(',', '\n')
            .map { it.trim().trimStart('-', '•', '#') }
            .filter { it.isNotEmpty() }
    }

    private fun callGemini(prompt: String, model: String, apiKey: String): Result<String> {
        val payload = JSONObject().apply {
            put("contents", listOf(mapOf("parts" to listOf(mapOf("text" to prompt)))))
        }

        val request = Request.Builder()
            .url(buildUrl(model, apiKey))
            .post(payload.toString().toRequestBody("application/json".toMediaType()))
            .header("Accept", "application/json")
            .build()

        return try {
            http.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return Result.failure(IOException("HTTP ${response.code}"))
                }

                val body = response.body?.string().orEmpty()
                if (body.isBlank()) {
                    return Result.failure(IOException("empty response"))
                }

                val text = JSONObject(body)
                    .optJSONArray("candidates")
                    ?.optJSONObject(0)
                    ?.optJSONObject("content")
                    ?.optJSONArray("parts")
                    ?.optJSONObject(0)
                    ?.optString("text", "")
                    ?.trim()

                text?.takeIf { it.isNotEmpty() }
                    ?.let { Result.success(it) }
                    ?: Result.failure(IOException("missing response text"))
            }
        } catch (ioException: IOException) {
            Result.failure(ioException)
        } catch (jsonException: JSONException) {
            Result.failure(jsonException)
        }
    }

    private fun buildUrl(model: String, apiKey: String): HttpUrl {
        val safeModel = model.ifBlank { GeminiSettingsStore.DEFAULT_MODEL }
        return baseUrl.newBuilder()
            .addPathSegment("$safeModel:generateContent")
            .addQueryParameter("key", apiKey)
            .build()
    }
}
