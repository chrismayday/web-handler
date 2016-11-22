package com.bluexiii.jwh.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bluexiii on 16/8/25.
 */
@ApiModel("部门")
@Entity
public class Depart extends BaseEntity {

    @ApiModelProperty(value = "部门名称",required = true)
    @Column(nullable = false, length = 32)
    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 32, message = "名称长度范围为2-32位字符")
    private String departName;

    @ApiModelProperty(value = "部门级别")
    @Column
    private int departLevel;

    @ApiModelProperty(value = "区号")
    @Column(length = 32)
    @Size(max = 32, message = "长度不能大于32位字符")
    private String eparchyCode;

    @ApiModelProperty(value = "上级部门")
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Depart parentDepart;

    @ApiModelProperty(value = "用户")
    @OneToMany(mappedBy = "depart", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Depart() {
    }

    public Depart(String departName) {
        this.departName = departName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getDepartLevel() {
        return departLevel;
    }

    public void setDepartLevel(int departLevel) {
        this.departLevel = departLevel;
    }

    public String getEparchyCode() {
        return eparchyCode;
    }

    public void setEparchyCode(String eparchyCode) {
        this.eparchyCode = eparchyCode;
    }

    public Depart getParentDepart() {
        return parentDepart;
    }

    public void setParentDepart(Depart parentDepart) {
        this.parentDepart = parentDepart;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Depart{" +
                "departName='" + departName + '\'' +
                ", departLevel=" + departLevel +
                ", eparchyCode='" + eparchyCode + '\'' +
//                ", parentDepart=" + parentDepart +
//                ", users=" + users +
                '}';
    }
}
