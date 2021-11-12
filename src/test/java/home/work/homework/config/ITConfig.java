package home.work.homework.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@ActiveProfiles("test")
@Configuration
@EntityScan("home.work")
@ComponentScan("home.work")
@EnableJpaRepositories("home.work")
public class ITConfig {

    @Bean
    @Primary
    Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();

        return flyway;
    }
}
