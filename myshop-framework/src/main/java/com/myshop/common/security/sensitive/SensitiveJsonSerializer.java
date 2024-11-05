package com.myshop.common.security.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.myshop.common.properties.SystemProperties;
import com.myshop.common.security.AuthUser;
import com.myshop.common.security.context.UserContext;
import com.myshop.common.security.enums.UserEnums;
import com.myshop.common.security.sensitive.enums.SensitiveStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.Objects;

/**
 * Lọc thông tin nhạy cảm khi serialize
 */
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer, ApplicationContextAware {

    private SensitiveStrategy sensitiveStrategy;

    // Cấu hình hệ thống
    private SystemProperties systemProperties;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Xử lý serialize cho trường
        gen.writeString(sensitiveStrategy.maskingFunction().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {

        // Kiểm tra xem có cần xử lý ẩn thông tin hay không
        if (desensitization()) {
            // Lấy enum nhạy cảm
            SensitiveData sensitiveAnnotation = property.getAnnotation(SensitiveData.class);
            // Nếu có chú thích nhạy cảm, áp dụng quy tắc ẩn thông tin
            if (Objects.nonNull(sensitiveAnnotation) && Objects.equals(String.class, property.getType().getRawClass())) {
                this.sensitiveStrategy = sensitiveAnnotation.strategy();
                return this;
            }
        }
        return provider.findValueSerializer(property.getType(), property);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        systemProperties = applicationContext.getBean(SystemProperties.class);
    }

    /**
     * Kiểm tra xem có cần xử lý ẩn thông tin hay không
     *
     * @return
     */
    private boolean desensitization() {

        // Người dùng hiện tại
        AuthUser currentAuthUser = UserContext.getCurrentUser();
        // Mặc định là không ẩn thông tin
        if (currentAuthUser == null) {
            return false;
        }

        // Nếu là cửa hàng
        if (currentAuthUser.getRole().equals(UserEnums.STORE)) {
            // Cửa hàng cần ẩn thông tin, thì xử lý ẩn thông tin
            return systemProperties.getDataMaskingLevel() == 2;
        }


        // Nếu là quản trị viên
        if (currentAuthUser.getRole().equals(UserEnums.MANAGER)) {
            // Quản trị viên cần ẩn thông tin, thì xử lý ẩn thông tin
            return systemProperties.getDataMaskingLevel() >= 1;
        }

        return false;
    }
}