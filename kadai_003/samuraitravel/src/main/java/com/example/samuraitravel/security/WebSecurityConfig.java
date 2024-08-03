package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	@SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses", "/houses/{id}","/houses/{id}/review/**","/stripe/webhook").permitAll()  // ‚·‚×‚Ä‚Ìƒ†�[ƒU�[‚ÉƒAƒNƒZƒX‚ð‹–‰Â‚·‚éURL           
                                .requestMatchers("/admin/**").hasRole("ADMIN")  // ŠÇ—�ŽÒ‚É‚Ì‚ÝƒAƒNƒZƒX‚ð‹–‰Â‚·‚éURL
                                .anyRequest().authenticated()                   // �ã‹LˆÈŠO‚ÌURL‚Íƒ�ƒOƒCƒ“‚ª•K—v�i‰ïˆõ‚Ü‚½‚ÍŠÇ—�ŽÒ‚Ì‚Ç‚¿‚ç‚Å‚àOK�j
                )
                .formLogin((form) -> form
                                .loginPage("/login")              // ƒ�ƒOƒCƒ“ƒy�[ƒW‚ÌURL
                                .loginProcessingUrl("/login")     // ƒ�ƒOƒCƒ“ƒtƒH�[ƒ€‚Ì‘—�M�æURL
                                .defaultSuccessUrl("/?loggedIn")  // ƒ�ƒOƒCƒ“�¬Œ÷Žž‚ÌƒŠƒ_ƒCƒŒƒNƒg�æURL
                                .failureUrl("/login?error")       // ƒ�ƒOƒCƒ“Ž¸”sŽž‚ÌƒŠƒ_ƒCƒŒƒNƒg�æURL
                                .permitAll()
                )
                .logout((logout) -> logout
                                .logoutSuccessUrl("/?loggedOut")  // ƒ�ƒOƒAƒEƒgŽž‚ÌƒŠƒ_ƒCƒŒƒNƒg�æURL
                                .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/stripe/webhook"));            
            
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
