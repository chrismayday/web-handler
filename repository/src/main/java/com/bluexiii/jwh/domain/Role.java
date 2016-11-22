package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@ApiModel("角色")
@Entity
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名称(以ROLE_开头)", required = true)
    @Column(unique = true, nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String roleName;

    @ApiModelProperty(value = "角色解释")
    @Column(length = 64)
    @Size(max = 64, message = "长度不能大于64位字符")
    private String roleDesc;

    @ApiModelProperty(value = "用户")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> users = new HashSet<>();


    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, String roleDesc) {
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
//                ", users=" + users +
                '}';
    }
}
