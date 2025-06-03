package com.test.gateway.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

@Configuration
public class OtelConfig {

    /**
     * 初始化阶段：
     *
     * Hooks.enableAutomaticContextPropagation() 注册 Reactor 的全局 Hook。
     * 将 OpenTelemetry 的 Context 与 Reactor 的 ContextView 自动关联。
     */
    @PostConstruct
    public void enableReactorContextPropagation() {
        // 启用 Reactor 的自动上下文传播 traceId
        Hooks.enableAutomaticContextPropagation();
    }

}