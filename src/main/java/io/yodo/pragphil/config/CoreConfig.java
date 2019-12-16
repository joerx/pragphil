package io.yodo.pragphil.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.SessionCookieConfig;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableRedisHttpSession
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
    @DependsOn("flyway")
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        logger.debug("Loading session manager bean");

        Properties props = new Properties();
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds);
        sf.setPackagesToScan("io.yodo.pragphil.core.domain.entity");
        sf.setHibernateProperties(props);

        return sf;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sess) {
        HibernateTransactionManager m = new HibernateTransactionManager();
        m.setSessionFactory(sess);
        return m;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        logger.debug("Loading flyway bean");
        return Flyway
            .configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .locations("classpath:/db-migrations")
            .load();
    }

    // Redis connection stuff
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        String port = Objects.requireNonNull(env.getProperty("redis.port"));
        String host = Objects.requireNonNull(env.getProperty("redis.host"));
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, Integer.parseInt(port)));
    }

    // Creating our own CookieSerializer to avoid running into this issue:
    // https://github.com/spring-projects/spring-framework/issues/22319
    @Bean
    public CookieSerializer cookieSerializer(ServletContext ctx) {
        logger.debug("Creating cookie serializer");
        DefaultCookieSerializer cs = new DefaultCookieSerializer();

        try {
            SessionCookieConfig cfg = ctx.getSessionCookieConfig();
            logger.debug("Cookie domain: " + cfg.getDomain());
            logger.debug("Cookie path: " + cfg.getPath());

            cs.setCookieName(cfg.getName());
            cs.setDomainName(cfg.getDomain());
            cs.setCookiePath(cfg.getPath());
            cs.setCookieMaxAge(cfg.getMaxAge());
        } catch (UnsupportedOperationException e) {
            logger.info("Failed to get default session cookie config, will fall back to defaults");
            logger.debug("Setting cookie path to '" + ctx.getContextPath() + "'");

            cs.setCookieName("PRAGPHIL_SESSID");
            cs.setCookiePath(ctx.getContextPath());
        }

        cs.setRememberMeRequestAttribute(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR);
        return cs;
    }
}
