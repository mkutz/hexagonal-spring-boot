package io.github.mkutz.order.adapter.`in`.rest

import io.github.mkutz.order.adapter.KafkaContainerConfiguration
import io.github.mkutz.order.adapter.PostgresContainerConfiguration
import io.github.mkutz.order.adapter.out.persistence.ArticleEntity
import io.github.mkutz.order.adapter.out.persistence.ArticleRepository
import java.math.BigDecimal
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.json.JsonMapper

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("adapter-test")
@Import(PostgresContainerConfiguration::class, KafkaContainerConfiguration::class)
class OrderControllerTest
@Autowired
constructor(
  private val mockMvc: MockMvc,
  private val articleRepository: ArticleRepository,
  private val jsonMapper: JsonMapper,
) {

  private lateinit var testArticle: ArticleEntity

  @BeforeEach
  fun setUp() {
    testArticle =
      articleRepository.save(
        ArticleEntity(
          id = UUID.randomUUID(),
          name = "Test Article",
          priceAmount = BigDecimal("19.99"),
          priceCurrency = "EUR",
        )
      )
  }

  @Test
  fun `POST creates order and returns 201`() {
    val requestBody =
      """
            {
                "items": [
                    {"articleId": "${testArticle.id}", "quantity": 2}
                ]
            }
        """
        .trimIndent()

    mockMvc
      .perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON).content(requestBody))
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.status").value("CREATED"))
      .andExpect(jsonPath("$.items").isArray)
      .andExpect(jsonPath("$.items[0].articleId").value(testArticle.id.toString()))
      .andExpect(jsonPath("$.items[0].quantity").value(2))
  }

  @Test
  fun `GET returns order when exists`() {
    val createResponse =
      mockMvc
        .perform(
          post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{"items": [{"articleId": "${testArticle.id}", "quantity": 1}]}""")
        )
        .andReturn()

    val orderId = jsonMapper.readTree(createResponse.response.contentAsString).get("id").asText()

    mockMvc
      .perform(get("/api/orders/$orderId"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.id").value(orderId))
      .andExpect(jsonPath("$.status").value("CREATED"))
  }

  @Test
  fun `GET returns 404 when order does not exist`() {
    val nonExistentId = UUID.randomUUID()

    mockMvc.perform(get("/api/orders/$nonExistentId")).andExpect(status().isNotFound)
  }

  @Test
  fun `DELETE cancels order`() {
    val createResponse =
      mockMvc
        .perform(
          post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""{"items": [{"articleId": "${testArticle.id}", "quantity": 1}]}""")
        )
        .andReturn()

    val orderId = jsonMapper.readTree(createResponse.response.contentAsString).get("id").asText()

    mockMvc
      .perform(delete("/api/orders/$orderId"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.id").value(orderId))
      .andExpect(jsonPath("$.status").value("CANCELLED"))
  }
}
