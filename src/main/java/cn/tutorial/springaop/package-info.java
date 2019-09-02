package cn.tutorial.springaop;
/*
* AOP源码解析
*
* 入口 @Aspect
*  AspectJAutoProxyBeanDefinitionParser的parse方法
*  1. 注册或者升级AnnotationAwareAspectJAutoProxyCreator
*  2. 处理proxy-target-class以及expose-proxy属性
*  3. 注册组件并通知，便于监听器进一步处理
*
*
* AOP
* */

/*
@Configuration
public class YamlConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();

        yaml.setResources(new ClassPathResource("conf/invoice.yml"));

        configurer.setProperties(yaml.getObject());
        return configurer;
    }
    <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.framework.version}</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.framework.version}</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.framework.version}</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.framework.version}</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.framework.version}</version>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>${spring.data.jpa.version}</version>
            <optional>true</optional>
            <exclusions>
                <exclusion>
                    <artifactId>aspectjrt</artifactId>
                    <groupId>org.aspectj</groupId>
                </exclusion>
                <exclusion>
                    <artifactId>jcl-over-slf4j</artifactId>
                    <groupId>org.slf4j</groupId>
                </exclusion>
            </exclusions>
        </dependency>
}*/
