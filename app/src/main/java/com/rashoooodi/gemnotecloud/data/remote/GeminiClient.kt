package com.rashoooodi.gemnotecloud.data.remote
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit
class GeminiClient(private val apiKey: String) {
  private val http = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
  private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models"
  fun summarize(transcript: String, model: String): String {
    val prompt = "Summarize the following transcript in 3-5 bullets and keep it concise: $transcript"
    val bodyJson = JSONObject(); bodyJson.put("contents", listOf(mapOf("parts" to listOf(mapOf("text" to prompt)))))
    val req = Request.Builder().url("$baseUrl/$model:generateContent").header("Authorization", "Bearer $apiKey")
      .post(RequestBody.create("application/json".toMediaType(), bodyJson.toString())).build()
    http.newCall(req).execute().use { res ->
      val text = JSONObject(res.body?.string() ?: "{}").optJSONArray("candidates")?.optJSONObject(0)
        ?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text", "") ?: ""
      return text
    }
  }
  fun tags(transcript: String, model: String): List<String> {
    val prompt = "Generate up to 5 short tags (1-2 words) separated by commas for: $transcript"
    val bodyJson = JSONObject(); bodyJson.put("contents", listOf(mapOf("parts" to listOf(mapOf("text" to prompt)))))
    val req = Request.Builder().url("$baseUrl/$model:generateContent").header("Authorization", "Bearer $apiKey")
      .post(RequestBody.create("application/json".toMediaType(), bodyJson.toString())).build()
    http.newCall(req).execute().use { res ->
      val text = JSONObject(res.body?.string() ?: "{}").optJSONArray("candidates")?.optJSONObject(0)
        ?.optJSONObject("content")?.optJSONArray("parts")?.optJSONObject(0)?.optString("text", "") ?: ""
      return text.split(',').map { it.trim() }.filter { it.isNotEmpty() }
    }
  }
}
