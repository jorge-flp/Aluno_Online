package com.App_Escola.Api.Config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CorsConfigTest {

    @Test
    void allowsThisProjectsPreviewOriginAndRejectsOtherVercelProjects() {
        CorsConfig corsConfig = new CorsConfig();
        ReflectionTestUtils.setField(
                corsConfig,
                "frontendUrl",
                "https://esconlinefront-school-portal.vercel.app"
        );
        ReflectionTestUtils.setField(
                corsConfig,
                "frontendPreviewOriginPattern",
                "https://esconlinefront-school-portal-*.vercel.app"
        );

        CorsConfiguration configuration = corsConfig
                .corsConfigurationSource()
                .getCorsConfiguration(new MockHttpServletRequest("OPTIONS", "/turmas"));

        assertNotNull(configuration);
        assertEquals(
                "https://esconlinefront-school-portal-lp5ya0awj.vercel.app",
                configuration.checkOrigin("https://esconlinefront-school-portal-lp5ya0awj.vercel.app")
        );
        assertNull(configuration.checkOrigin("https://unrelated-project.vercel.app"));
    }
}