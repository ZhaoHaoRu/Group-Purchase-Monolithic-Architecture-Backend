package com.example.groupbuy.entity;

import com.example.groupbuy.config.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * 实现UserDetails接口，用户登录时，系统会根据用户名，
 * 从存储设备查找该用户的密码及权限等，将其组装成一个UserDetails对象。
 * 并用UserDetails中的数据对用户进行认证，决定其输入的用户名/密码是否正确。
 */
@Data
@Getter
@Setter
@Entity
@Table(name = "user_for_detail")
@Comment("用户表")
@ApiModel("用户权限认证和加密密码")
public class UserForDetail implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("主键")
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(value = "用户Id")
    private User user;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    public UserForDetail(Integer id, User user, String username, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.username = username;
//        this.userId = userId;
//        this.userName = userName;
    }

    public UserForDetail(){}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
