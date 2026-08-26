package gin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * 
 * @TableName qm_auth
 */
@TableName(value ="qm_auth")
public class QmAuth {
    /**
     * 
     */
    @TableId
    private Object id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Boolean voiceMessage;

    /**
     * 
     */
    private Integer trashDays;

    /**
     * 
     */
    private Integer maxStorage;

    /**
     * 
     */
    private Boolean isDefault;

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
    public String getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public Boolean getVoiceMessage() {
        return voiceMessage;
    }

    /**
     * 
     */
    public void setVoiceMessage(Boolean voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    /**
     * 
     */
    public Integer getTrashDays() {
        return trashDays;
    }

    /**
     * 
     */
    public void setTrashDays(Integer trashDays) {
        this.trashDays = trashDays;
    }

    /**
     * 
     */
    public Integer getMaxStorage() {
        return maxStorage;
    }

    /**
     * 
     */
    public void setMaxStorage(Integer maxStorage) {
        this.maxStorage = maxStorage;
    }

    /**
     * 
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * 
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
        QmAuth other = (QmAuth) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getVoiceMessage() == null ? other.getVoiceMessage() == null : this.getVoiceMessage().equals(other.getVoiceMessage()))
            && (this.getTrashDays() == null ? other.getTrashDays() == null : this.getTrashDays().equals(other.getTrashDays()))
            && (this.getMaxStorage() == null ? other.getMaxStorage() == null : this.getMaxStorage().equals(other.getMaxStorage()))
            && (this.getIsDefault() == null ? other.getIsDefault() == null : this.getIsDefault().equals(other.getIsDefault()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getVoiceMessage() == null) ? 0 : getVoiceMessage().hashCode());
        result = prime * result + ((getTrashDays() == null) ? 0 : getTrashDays().hashCode());
        result = prime * result + ((getMaxStorage() == null) ? 0 : getMaxStorage().hashCode());
        result = prime * result + ((getIsDefault() == null) ? 0 : getIsDefault().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", voiceMessage=").append(voiceMessage);
        sb.append(", trashDays=").append(trashDays);
        sb.append(", maxStorage=").append(maxStorage);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}