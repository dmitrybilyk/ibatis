package com.ibatis.scorecardmodel.bo;

import java.net.ProtocolFamily;

/**
 * Created by dmitry on 27.12.14.
 */
public class PagingLoadConfig {
    private Integer limit;
    private Integer offset;
    private Object sortField;
    private ProtocolFamily sortDir;

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public Object getSortField() {
        return sortField;
    }

    public ProtocolFamily getSortDir() {
        return sortDir;
    }
}
