package gin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * 
 * @TableName qm_trash
 */
@TableName(value ="qm_trash")
public class QmTrash {
    /**
     * 
     */
    @TableId
    private Object id;

    /**
     * 
     */
    private Object sourceId;

    /**
     * 
     */
    private String sourceType;

    /**
     * 
     */
    private Object userId;

    /**
     * 
     */
    private String originalData;

    /**
     * 
     */
    private Date expiredAt;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    public Object getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * 
     */
    public Object getSourceId() {
        return sourceId;
    }

    /**
     * 
     */
    public void setSourceId(Object sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 
     */
    public Object getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(Object userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public String getOriginalData() {
        return originalData;
    }

    /**
     * 
     */
    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    /**
     * 
     */
    public Date getExpiredAt() {
        return expiredAt;
    }

    /**
     * 
     */
    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    /**
     * 
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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
        QmTrash other = (QmTrash) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSourceId() == null ? other.getSourceId() == null : this.getSourceId().equals(other.getSourceId()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOriginalData() == null ? other.getOriginalData() == null : this.getOriginalData().equals(other.getOriginalData()))
            && (this.getExpiredAt() == null ? other.getExpiredAt() == null : this.getExpiredAt().equals(other.getExpiredAt()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSourceId() == null) ? 0 : getSourceId().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOriginalData() == null) ? 0 : getOriginalData().hashCode());
        result = prime * result + ((getExpiredAt() == null) ? 0 : getExpiredAt().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceId=").append(sourceId);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", userId=").append(userId);
        sb.append(", originalData=").append(originalData);
        sb.append(", expiredAt=").append(expiredAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}