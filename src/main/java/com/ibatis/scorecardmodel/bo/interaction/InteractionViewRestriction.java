package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.evaluation.ExternalDataBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchBO;

import java.util.HashSet;
import java.util.Set;

/**
 * Radek Mensik, 25.9.13
 */
public class InteractionViewRestriction {
  private SearchBO baseSearch = new SearchBO();
  private Set<UserBO> canViewUsers = new HashSet<UserBO>();
  private Set<SpeechTagSearchDTO> wantedTags = new HashSet<SpeechTagSearchDTO>();
  private Set<SpeechTagSearchDTO> unWantedTags = new HashSet<SpeechTagSearchDTO>();
  private Set<ExternalDataBO> externalDataBO = new HashSet<ExternalDataBO>();


  public SearchBO getBaseSearch() {
    return baseSearch;
  }

  public void setBaseSearch(SearchBO baseSearch) {
    this.baseSearch = baseSearch;
  }

  public Set<UserBO> getCanViewUsers() {
    return canViewUsers;
  }

  public void setCanViewUsers(Set<UserBO> canViewUsers) {
    this.canViewUsers = canViewUsers;
  }

  public Set<SpeechTagSearchDTO> getWantedTags() {
    return wantedTags;
  }

  public void setWantedTags(Set<SpeechTagSearchDTO> wantedTags) {
    this.wantedTags = wantedTags;
  }

  public Set<SpeechTagSearchDTO> getUnWantedTags() {
    return unWantedTags;
  }

  public void setUnWantedTags(Set<SpeechTagSearchDTO> unWantedTags) {
    this.unWantedTags = unWantedTags;
  }

  public Set<ExternalDataBO> getExternalDataBO() {
    return externalDataBO;
  }

  public void setExternalDataBO(Set<ExternalDataBO> externalDataBO) {
    this.externalDataBO = externalDataBO;
  }
}
