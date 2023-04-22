package com.starry.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName traffic_task
 */
@TableName(value ="traffic_task")
@Data
public class TrafficTaskDO implements Serializable {
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
     * 
     */
    private Long trafficId;

    /**
     * 
     */
    private Integer useTimes;

    /**
     * 锁定状态锁定LOCK  完成FINISH-取消CANCEL
     */
    private String lockState;

    /**
     * 唯一标识
     */
    private String messageId;

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
        TrafficTaskDO other = (TrafficTaskDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getTrafficId() == null ? other.getTrafficId() == null : this.getTrafficId().equals(other.getTrafficId()))
            && (this.getUseTimes() == null ? other.getUseTimes() == null : this.getUseTimes().equals(other.getUseTimes()))
            && (this.getLockState() == null ? other.getLockState() == null : this.getLockState().equals(other.getLockState()))
            && (this.getMessageId() == null ? other.getMessageId() == null : this.getMessageId().equals(other.getMessageId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getTrafficId() == null) ? 0 : getTrafficId().hashCode());
        result = prime * result + ((getUseTimes() == null) ? 0 : getUseTimes().hashCode());
        result = prime * result + ((getLockState() == null) ? 0 : getLockState().hashCode());
        result = prime * result + ((getMessageId() == null) ? 0 : getMessageId().hashCode());
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
        sb.append(", trafficId=").append(trafficId);
        sb.append(", useTimes=").append(useTimes);
        sb.append(", lockState=").append(lockState);
        sb.append(", messageId=").append(messageId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}