package com.starry.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @TableName group_code_mapping
 */
@TableName(value ="group_code_mapping")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupCodeMappingDO implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 组
     */
    private Long groupId;

    /**
     * 短链标题
     */
    private String title;

    /**
     * 原始url地址
     */
    private String originalUrl;

    /**
     * 短链域名
     */
    private String domain;

    /**
     * 短链压缩码
     */
    private String code;

    /**
     * 长链的md5码，方便查找
     */
    private String sign;

    /**
     * 过期时间，长久就是-1
     */
    private Date expired;

    /**
     * 账号唯一编号
     */
    private Long accountNo;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 0是默认，1是删除
     */
    private Integer del;

    /**
     * 状态，lock是锁定不可用，active是可用
     */
    private String state;

    /**
     * 链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石
     */
    private String linkType;

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
        GroupCodeMappingDO other = (GroupCodeMappingDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getOriginalUrl() == null ? other.getOriginalUrl() == null : this.getOriginalUrl().equals(other.getOriginalUrl()))
            && (this.getDomain() == null ? other.getDomain() == null : this.getDomain().equals(other.getDomain()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getSign() == null ? other.getSign() == null : this.getSign().equals(other.getSign()))
            && (this.getExpired() == null ? other.getExpired() == null : this.getExpired().equals(other.getExpired()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getDel() == null ? other.getDel() == null : this.getDel().equals(other.getDel()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getLinkType() == null ? other.getLinkType() == null : this.getLinkType().equals(other.getLinkType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getOriginalUrl() == null) ? 0 : getOriginalUrl().hashCode());
        result = prime * result + ((getDomain() == null) ? 0 : getDomain().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getSign() == null) ? 0 : getSign().hashCode());
        result = prime * result + ((getExpired() == null) ? 0 : getExpired().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getDel() == null) ? 0 : getDel().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getLinkType() == null) ? 0 : getLinkType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupId=").append(groupId);
        sb.append(", title=").append(title);
        sb.append(", originalUrl=").append(originalUrl);
        sb.append(", domain=").append(domain);
        sb.append(", code=").append(code);
        sb.append(", sign=").append(sign);
        sb.append(", expired=").append(expired);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", del=").append(del);
        sb.append(", state=").append(state);
        sb.append(", linkType=").append(linkType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}