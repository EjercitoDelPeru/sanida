package org.ep.sanida.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //If this endpoint is a Spring MVC endpoint,
        // please use requestMatchers(MvcRequestMatcher);
        httpSecurity

                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).hasRole("USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasRole("ADMIN")
                        // if you use authority to manage a context, use: hasAuthority("ADMIN")



                        //Do it in the template view and at the side side which is my case.
                        .anyRequest().authenticated()
                )
                   .formLogin(withDefaults());//default form spring used
             /*   .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );*/
        httpSecurity.rememberMe(withDefaults());

       /* httpSecurity.logout((logout) ->
                logout.deleteCookies("remove")
                        .invalidateHttpSession(false)
                        .logoutUrl("/custom-logout").permitAll()
                        .logoutSuccessUrl("/logout-success").permitAll()
        );*/

        httpSecurity
                // sample exception handling customization
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/access-denied")
                );

        //httpSecurity.userDetailsService(userDetailServiceImpl);
        httpSecurity.userDetailsService(userDetailsService(passwordEncoder()));
       // httpSecurity.userDetailsService(userDetailServiceImpl);//calling of userDetails Strategy to be used

        return httpSecurity.build();
    }

@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService(passwordEncoder()));
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();

    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("admin")
                .password(encoder.encode("123")) 
                .roles("ADMIN", "USER") 
                //.authorities("hello","hello-secured")
                .authorities("READ", "CREATE")
                .build());
        userDetailsList.add(User.withUsername("user")
        .password(encoder.encode("123")) 
                .roles("USER")
                //.authorities("hello")
                .authorities("USER")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }


/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //If this endpoint is a Spring MVC endpoint,
        // please use requestMatchers(MvcRequestMatcher);
        httpSecurity

                .authorizeHttpRequests((http) -> {
                        http.requestMatchers(AntPathRequestMatcher.antMatcher("/webjars/**")).permitAll();
                        http.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
                        http.requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).hasRole("USER");
                        http.requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasRole("ADMIN");
                        // if you use authority to manage a context, use: hasAuthority("ADMIN")
                        //Do it in the template view and at the side side which is my case.

                        // Configurar los endpoints publicos
                    http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();

                    // Cofnigurar los endpoints privados
                    http.requestMatchers(HttpMethod.POST, "/auth/post").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch").hasAnyAuthority("REFACTOR");

                    // Configurar el resto de endpoint - NO ESPECIFICADOS
                    //http.anyRequest().denyAll();
                        http.anyRequest().authenticated();
    })
                   .formLogin(withDefaults());//default form spring used

        httpSecurity.rememberMe(withDefaults());



        httpSecurity
                // sample exception handling customization
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/access-denied")
                );


        return httpSecurity.build();
    }
         */

}
