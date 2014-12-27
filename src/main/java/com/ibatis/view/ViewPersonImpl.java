package com.ibatis.view;

import com.learn.model.BaseBean;

import java.util.List;

/**
 * Created by dmitry on 27.12.14.
 */
public class ViewPersonImpl implements View {

    @Override
    public void viewEntity(List<? extends BaseBean> list) {
        for (BaseBean baseBean : list) {
            System.out.println(baseBean);
        }
    }
}
