

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
// Flutter em execução
        config.addAllowedOrigin("http://localhost:50289");
        config.addAllowedOrigin("http://127.0.0.1:5500"); // Frontend do Live Server
        config.addAllowedMethod("*");  // Permite todos os métodos HTTP (GET, POST, etc)
        config.addAllowedHeader("*");  // Permite todos os cabeçalhos
        config.setAllowCredentials(true); // Permite o envio de cookies, se necessário

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a configuração a todas as rotas

        return new CorsFilter(source);
    }
}
