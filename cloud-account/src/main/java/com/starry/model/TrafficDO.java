package com.starry.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName traffic
 */
@TableName(value ="traffic")
@Data
public class TrafficDO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 每天限制多少条，短链
     */
    private Integer dayLimit;

    /**
     * 当天用了多少条，短链
     */
    private Integer dayUsed;

    /**
     * 总次数，活码才用
     */
    private Integer totalLimit;

    /**
     * 账号
     */
    private Long accountNo;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 产品层级：FIRST青铜、SECOND黄金、THIRD钻石
     */
    private String level;

    /**
     * 过期日期
     */
    private LocalDate expiredDate;

    /**
     * 插件类型
     */
    private String pluginType;

    /**
     * 商品主键
     */
    private Long productId;

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
        TrafficDO other = (TrafficDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDayLimit() == null ? other.getDayLimit() == null : this.getDayLimit().equals(other.getDayLimit()))
            && (this.getDayUsed() == null ? other.getDayUsed() == null : this.getDayUsed().equals(other.getDayUsed()))
            && (this.getTotalLimit() == null ? other.getTotalLimit() == null : this.getTotalLimit().equals(other.getTotalLimit()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getOutTradeNo() == null ? other.getOutTradeNo() == null : this.getOutTradeNo().equals(other.getOutTradeNo()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getExpiredDate() == null ? other.getExpiredDate() == null : this.getExpiredDate().equals(other.getExpiredDate()))
            && (this.getPluginType() == null ? other.getPluginType() == null : this.getPluginType().equals(other.getPluginType()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDayLimit() == null) ? 0 : getDayLimit().hashCode());
        result = prime * result + ((getDayUsed() == null) ? 0 : getDayUsed().hashCode());
        result = prime * result + ((getTotalLimit() == null) ? 0 : getTotalLimit().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getOutTradeNo() == null) ? 0 : getOutTradeNo().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getExpiredDate() == null) ? 0 : getExpiredDate().hashCode());
        result = prime * result + ((getPluginType() == null) ? 0 : getPluginType().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
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
        sb.append(", dayLimit=").append(dayLimit);
        sb.append(", dayUsed=").append(dayUsed);
        sb.append(", totalLimit=").append(totalLimit);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", outTradeNo=").append(outTradeNo);
        sb.append(", level=").append(level);
        sb.append(", expiredDate=").append(expiredDate);
        sb.append(", pluginType=").append(pluginType);
        sb.append(", productId=").append(productId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}