package home.work.homework.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import home.work.homework.api.exception.ExceptionHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

public abstract class BaseControllerTest {

    protected MockMvc mockMvc;

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new
            MappingJackson2HttpMessageConverter();

    public BaseControllerTest() {
    }

    public void setUp(Object mockedController) {
        mappingJackson2HttpMessageConverter.setObjectMapper(new ObjectMapper()
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        );
        mockMvc = MockMvcBuilders.standaloneSetup(mockedController)
                .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver() {
                    @Override
                    protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
                                                                                      final Exception exception) {
                        Method method = new ExceptionHandlerMethodResolver(ExceptionHandling.class).resolveMethod(exception);
                        if (method != null) {
                            return new ServletInvocableHandlerMethod(new ExceptionHandling(), method);
                        }
                        return super.getExceptionHandlerMethod(handlerMethod, exception);
                    }
                })
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .build();
    }
}
