package com.bra.common.persistence;


import com.bra.modules.sys.utils.UserUtils;

/**
 * Created by xiaobin on 16/1/21.
 */
public abstract class SaasEntity<T> extends DataEntity<T> {

    public SaasEntity() {
        super();
        this.tenantId = UserUtils.getTenantId();
    }

    public SaasEntity(String id) {
        super(id);
        this.tenantId = UserUtils.getTenantId();
    }

    public void preInsert() {
        this.tenantId = UserUtils.getTenantId();
        super.preInsert();
    }

    public void preUpdate() {
        this.tenantId = UserUtils.getTenantId();
        super.preUpdate();
    }

    protected String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
