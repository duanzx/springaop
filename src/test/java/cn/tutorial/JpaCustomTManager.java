package cn.tutorial;

public class JpaCustomTManager {

//    @Configuration
//    @EnableJpaRepositories(entityManagerFactoryRef = "epayInvoiceEntityManagerFactory", transactionManagerRef = "epayInvoiceTransactionManager", basePackages = {"cn.neea.admin.epay.invoice.data.domain.repo"})
//    public class InvoiceJpaConfiguration extends EpayAbstractConfiguration {
//        @Bean(name = "epayInvoiceEntityManagerFactory")
//        public LocalContainerEntityManagerFactoryBean ePayInvoiceEntityManagerFactory() {
//            return buildEntityManagerFactory("cn.neea.admin.epay.invoice.data.domain.bean.po");
//        }

//        protected LocalContainerEntityManagerFactoryBean buildEntityManagerFactory(String packageToScan) {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        factoryBean.setJpaProperties(jpaProperties);
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//        factoryBean.setPackagesToScan(new String[]{packageToScan});
//        return factoryBean;
//    }

//    @Configuration
//    @ImportResource(locations = "classpath:conf/neea-admin-data-source.xml")
//    @Order(0)
//    public class JpaConfiguration {
//        @Bean("jpaProperties")
//        public Properties jpaProperties() {
//            Properties jpaProperties = new Properties();
//            jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//            jpaProperties.put("hibernate.show_sql", false);
//            jpaProperties.put("hibernate.format_sql", false);
//            jpaProperties.put("hibernate.jdbc.batch_size", 1000);
//            jpaProperties.put("hibernate.cache.use_second_level_cache", false);
//            jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
//            jpaProperties.put("hibernate.current_session_context_class", SpringSessionContext.class.getName());
//            return jpaProperties;
//        }
//
//        @Bean
//        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//            return new PersistenceExceptionTranslationPostProcessor();
//        }
//    }
}
