package com.enset.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder password = passwordEncoder();
		System.out.println("------------------------");
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						" select username as principal, password as credentials, active from users where username=?")
				.authoritiesByUsernameQuery(
						"select username as principal, role as role from users_roles where username=?")
				.passwordEncoder(password).rolePrefix("ROLE_");

		System.out.println("Mot de passe : " + passwordEncoder().encode("1234"));
//		auth.inMemoryAuthentication().withUser("user1").password(password.encode("1234")).roles("USER");
//		auth.inMemoryAuthentication().withUser("user2").password(password.encode("1234")).roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password(password.encode("1234")).roles("ADMIN", "USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/login", "/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/delete**/**", "/save**/**", "/form**/**").hasRole("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();

		http.exceptionHandling().accessDeniedPage("/notAuthorize");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
