package com.tianyi.helmet.server.entity.power;

import com.tianyi.helmet.server.entity.IdEntity;

import java.time.LocalDateTime;

/**
 * 角色信息
 * Created by wenxinyan on 2018/10/9.
 */
public class Role extends IdEntity {
    private LocalDateTime createTime;
    private String roleName;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
