package io.yodo.pragphil.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"io.yodo.pragphil.core", "io.yodo.pragphil.config"})
@PropertySource("classpath:application.properties")
public class CoreConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Environment env;

    @Autowired
    public CoreConfig(Environment env) {
        this.env = env;
    }

    // data source to find, well - data
    @SuppressWarnings("ConstantConditions")
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        // create connection pool
        ComboPooledDataSource ds = new ComboPooledDataSource();
        // fail fast
        ds.setCheckoutTimeout(5000);
        ds.setAcquireRetryAttempts(5);
        ds.setAcquireRetryDelay(1000);

        // set jdbc driver
        ds.setDriverClass(env.getProperty("jdbc.driver"));

        // set database connection props
        String jdbcUrl = env.getProperty("jdbc.url");
        String jdbcUser = env.getProperty("jdbc.user");
        String jdbcPassword = env.getProperty("jdbc.password");

        logger.info("Connecting to " + jdbcUrl + " as " + jdbcUser);

        ds.setJdbcUrl(jdbcUrl);
        ds.setUser(jdbcUser);
        ds.setPassword(jdbcPassword);

        // set connection pool props
        int cpInitPoolSize = env.getProperty("connection.pool.initialPoolSize", Integer.class);
        int cpMinPoolSize = env.getProperty("connection.pool.minPoolSize", Integer.class);
        int cpMaxPoolSize = env.getProperty("connection.pool.maxPoolSize", Integer.class);
        int cpMaxIdleTime = env.getProperty("connection.pool.maxIdleTime", Integer.class);

        ds.setInitialPoolSize(cpInitPoolSize);
        ds.setMinPoolSize(cpMinPoolSize);
        ds.setMaxPoolSize(cpMaxPoolSize);
        ds.setMaxIdleTime(cpMaxIdleTime);

        return ds;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        Properties props = new Properties();
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds);
        sf.setPackagesToScan("io.yodo.pragphil.core.entity");
        sf.setHibernateProperties(props);

        return sf;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sess) {
        HibernateTransactionManager m = new HibernateTransactionManager();
        m.setSessionFactory(sess);
        return m;
    }
}
