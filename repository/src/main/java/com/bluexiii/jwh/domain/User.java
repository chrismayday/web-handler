package com.bluexiii.jwh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@ApiModel("用户")
@Entity
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名(英文)", required = true)
    @Pattern(regexp = "^\\w+$",message = "用户名只能有数字英文及下划线")
    @Column(unique = true, nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    @Column(length = 128)
    @Size(max = 128, message = "长度不能大于128位字符")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @Column(unique = true, length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String email;

    @ApiModelProperty(value = "手机号")
    @Column(unique = true, length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String phone;

    @ApiModelProperty(value = "用户中文名")
    @Column(length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String fullname;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_rela",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "depart_id")
    private Depart depart;


    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String fullname) {
        this.username = username;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                ", depart=" + depart +
                '}';
    }
}
