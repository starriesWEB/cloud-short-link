package com.starry.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName account
 */
@TableName(value ="account")
@Data
public class AccountDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private Long accountNo;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 盐，用于个人敏感信息处理
     */
    private String secret;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户名
     */
    private String username;

    /**
     * 认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样
     */
    private String auth;

    /**
     * 
     */
    private LocalDateTime gmtCreate;

    /**
     * 
     */
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AccountDO other = (AccountDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getHeadImg() == null ? other.getHeadImg() == null : this.getHeadImg().equals(other.getHeadImg()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getPwd() == null ? other.getPwd() == null : this.getPwd().equals(other.getPwd()))
            && (this.getSecret() == null ? other.getSecret() == null : this.getSecret().equals(other.getSecret()))
            && (this.getMail() == null ? other.getMail() == null : this.getMail().equals(other.getMail()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getAuth() == null ? other.getAuth() == null : this.getAuth().equals(other.getAuth()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getHeadImg() == null) ? 0 : getHeadImg().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getPwd() == null) ? 0 : getPwd().hashCode());
        result = prime * result + ((getSecret() == null) ? 0 : getSecret().hashCode());
        result = prime * result + ((getMail() == null) ? 0 : getMail().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getAuth() == null) ? 0 : getAuth().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", headImg=").append(headImg);
        sb.append(", phone=").append(phone);
        sb.append(", pwd=").append(pwd);
        sb.append(", secret=").append(secret);
        sb.append(", mail=").append(mail);
        sb.append(", username=").append(username);
        sb.append(", auth=").append(auth);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}