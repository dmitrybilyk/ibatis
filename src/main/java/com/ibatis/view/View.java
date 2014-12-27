package com.ibatis.view;

import com.learn.model.BaseBean;

import java.util.List;
import java.util.Objects;

/**
 * Created by dmitry on 27.12.14.
 */
public interface View {
    public void viewEntity(List<? extends BaseBean> list);
}
