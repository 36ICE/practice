package com.example.myspring;

//@Configuration
//public class SpringServiceConfiguration extends WebMvcConfigurationSupport {
//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        // to  avoid HttpMediaTypeNotAcceptableException on standalone tomcat
//        configurer.favorPathExtension(false);
//    }
//
//    /**
//     * 解决url后缀包含特殊字符
//     * @param configurer
//     */
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.setUseSuffixPatternMatch(false);
//    }
//}