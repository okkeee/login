package com.example.demo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;




//ユーザー情報Entity

@Entity//エンティティクラスのアノテーション
@Getter//Getterの自動生成のアノテーション
@Setter//Setterの自動生成のアノテーション
@Table(name="user_sample")//テーブル名の指定
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id//idであることをアノテーションで明示する
	@Column(name="id")//DB上のカラム名を指定する
	private int id;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="delflg")
	private int delflg;

	//この下はインターフェースのメソッドを実装する
	
	//ユーザーに与えられる権限リストを返却するメソッド
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return null;
	}
	
	//ユーザー名を返却するメソッド
	@Override
	public String getUsername() {
		return this.username;
	}
	
	//パスワードを返却するメソッド
	@Override
	public String getPassword() {
		return this.password;
	}

	//アカウントの有効期限の状態を判定するメソッド
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//アカウントのロック状態を判定するメソッド
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//資格情報の有効期限の状態を判定するメソッド
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//有効なユーザーか判定するメソッド
	@Override
	public boolean isEnabled() {
		return true;
	}

}