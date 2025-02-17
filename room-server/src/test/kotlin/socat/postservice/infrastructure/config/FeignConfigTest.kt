package socat.postservice.infrastructure.config

import com.room.roomservice.global.config.FeignConfig
import feign.RequestTemplate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.assertj.core.api.Assertions.assertThat

class FeignConfigTest {

    private lateinit var feignConfig: FeignConfig
    private lateinit var requestTemplate: RequestTemplate
    private lateinit var mockRequest: MockHttpServletRequest

    @BeforeEach
    fun setUp() {
        feignConfig = FeignConfig()
        requestTemplate = RequestTemplate()
        mockRequest = MockHttpServletRequest()
        
        // RequestContextHolder 설정
        val attributes = ServletRequestAttributes(mockRequest)
        RequestContextHolder.setRequestAttributes(attributes)
    }

    @Test
    fun `Authorization 헤더가 있을 경우 Feign 요청에 헤더가 포함되어야 한다`() {
        // given
        val authToken = "Bearer test-token"
        mockRequest.addHeader("Authorization", authToken)

        // when
        val interceptor = feignConfig.requestInterceptor()
        interceptor.apply(requestTemplate)

        // then
        assertThat(requestTemplate.headers()["Authorization"])
            .isNotNull
            .contains(authToken)
    }

    @Test
    fun `Authorization 헤더가 없을 경우 Feign 요청에 헤더가 포함되지 않아야 한다`() {
        // when
        val interceptor = feignConfig.requestInterceptor()
        interceptor.apply(requestTemplate)

        // then
        assertThat(requestTemplate.headers()["Authorization"]).isNull()
    }

    @Test
    fun `RequestInterceptor Bean이 생성되어야 한다`() {
        // when
        val interceptor = feignConfig.requestInterceptor()

        // then
        assertThat(interceptor).isNotNull
    }
}