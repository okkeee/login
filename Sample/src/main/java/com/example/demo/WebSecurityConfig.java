package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



/**
 * SpringSecurityを利用するための設定クラス
 * ログイン処理でのパラメータ、画面遷移や認証処理でのデータアクセス先を設定する
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//UserDetailsServiceを利用出来るように＠Autowiredしておく
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //認証用パスワードはハッシュ化して扱うためPasswordをハッシュ化する際に必要なBCryptPasswordEncoder()を返すメソッドを作成しておく。
    @Bean
    public PasswordEncoder passwordEncoder() {

    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	
	//これはハッシュ化済みの値をDBに登録する確認用に出力させるコード//
	String password = "shimizu";
        String digest = bCryptPasswordEncoder.encode(password);
        System.out.println("ハッシュ値 = " + digest);
	///////////////////////////////////////////////////////////////

        return new BCryptPasswordEncoder();
    }


    /**
     * 認可設定を無視するリクエストを設定
     * 静的リソース(image,javascript,css)を認可処理の対象から除外する
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        		.antMatchers("/resources/**");
    }

    /**
     * 認証・認可の情報を設定する
     * SpringSecurityのconfigureメソッドをオーバーライドしています。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/login").permitAll()
                .anyRequest().authenticated();
        		
        http
        	.formLogin()
        		.loginPage("/login")//ログインページとして使用するurlを設定する
        		.usernameParameter("username")//Usernameのパラメータとして使用する項目のnameを設定する
    			.passwordParameter("password")//Passwordのパラメータとして使用する項目のnameを設定する
                .failureUrl("/error1")//エラー発生時として使用するurlを設定する
                .defaultSuccessUrl("/sample", true)
                .permitAll();//エラー発生画面も未認証でアクセス出来るようにしないといけない。(この記述がないと指定のurlに遷移せずloginにリダイレクトされる)

    }

    /**
     * 認証時に利用するデータソースを定義する設定メソッド
     * ここではDBから取得したユーザ情報をuserDetailsServiceへセットすることで認証時の比較情報としている
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
    //UserDetailsServiceを設定してDaoAuthenticationProviderを有効化する
     auth.userDetailsService(userDetailsService).
     //上記作成のエンコードを設定しハッシュ化する
     passwordEncoder(passwordEncoder());
    }
}