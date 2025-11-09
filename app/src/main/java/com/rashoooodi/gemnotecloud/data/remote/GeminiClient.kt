package com.rashoooodi.gemnotecloud.data.remote
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit
class GeminiClient {
  private val http = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
  private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"

  fun summarize(transcript: String, model: String, apiKey: String): String {
    if (apiKey.isBlank()) return "Provide a Gemini API key in Settings to enable summaries."
    val prompt = "Summarize the following transcript in 3-5 bullets and keep it concise: $transcript"
    return callGemini(prompt, model, apiKey)
  }

  fun tags(transcript: String, model: String, apiKey: String): List<String> {
    if (apiKey.isBlank()) return emptyList()
    val prompt = "Generate up to 5 short tags (1-2 words) separated by commas for: $transcript"
    val response = callGemini(prompt, model, apiKey)
    return response.split(',').map { it.trim() }.filter { it.isNotEmpty() }
  }

  private fun callGemini(prompt: String, model: String, apiKey: String): String {
    val payload = JSONObject().apply {
      put("contents", listOf(mapOf("parts" to listOf(mapOf("text" to prompt)))))
    }
    val url = "$baseUrl/$model:generateContent?key=${apiKey.urlEncoded()}"
    val request = Request.Builder()
      .url(url)
      .post(RequestBody.create("application/json".toMediaType(), payload.toString()))
      .build()
    http.newCall(request).execute().use { res ->
      if (!res.isSuccessful) {
        return "Gemini request failed: HTTP ${res.code}"
      }
      val body = res.body?.string().orEmpty()
      val text = JSONObject(body).optJSONArray("candidates")?.optJSONObject(0)
        ?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text", "")
      return text?.takeIf { it.isNotBlank() } ?: ""
    }
  }

  private fun String.urlEncoded(): String = java.net.URLEncoder.encode(this, java.nio.charset.StandardCharsets.UTF_8.name())
}
