package moa.moamore.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public com.querydsl.sql.Configuration querydslConfiguration() {
//        SQLTemplates templates = MySQLTemplates.builder().build(); //change to your Templates
//        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
//        SpringExceptionTranslator springExceptionTranslator = new SpringExceptionTranslator();
//        configuration.setExceptionTranslator(springExceptionTranslator);
//        return configuration;
//    }
//
//    @Bean
//    SQLQueryFactory queryFactory() {
//        Provider<Connection> provider = new SpringConnectionProvider(dataSource());
//        SQLQueryFactory sqlQueryFactory = new SQLQueryFactory(querydslConfiguration(), provider);
//        return sqlQueryFactory;
//    }


}

