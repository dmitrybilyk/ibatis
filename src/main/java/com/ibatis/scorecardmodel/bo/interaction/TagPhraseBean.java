package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

public class TagPhraseBean extends BaseBean {
  private static final long serialVersionUID = -2220918218164854490L;

  private Integer phraseid;
  private Integer tagid;

  public Integer getPhraseid() {
    return phraseid;
  }

  public void setPhraseid(Integer phraseid) {
    this.phraseid = phraseid;
  }

  public Integer getTagid() {
    return tagid;
  }

  public void setTagid(Integer tagid) {
    this.tagid = tagid;
  }

  @Override
  public BaseBean newInstance() {
    return new TagPhraseBean();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    return new ArrayList<SearchOrder>();
  }

}