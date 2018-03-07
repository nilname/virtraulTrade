package com.ooteco.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserCoin extends UserCoinKey implements Serializable {
    private Date createTime;

    private Date modifyTime;

    private BigDecimal totalQuantity;

    private BigDecimal freezeQuantity;

    private static final long serialVersionUID = 1L;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getFreezeQuantity() {
        return freezeQuantity;
    }

    public void setFreezeQuantity(BigDecimal freezeQuantity) {
        this.freezeQuantity = freezeQuantity;
    }
}