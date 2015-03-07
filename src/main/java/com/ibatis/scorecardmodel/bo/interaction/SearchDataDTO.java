package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.evaluation.ExternalDataBO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: Stanislav Valenta
 * Date: 31.7.11
 */
public class SearchDataDTO implements Serializable {
  private static final long serialVersionUID = 5124646887070614444L;

  private Integer ccGroupId;
  private Integer userId;
  private String clientPhone;
  private HoursRange hoursRange;
  private DateRange dateRange;
  private String description;
  private Set<SpeechTagSearchDTO> wantedTags = new HashSet<SpeechTagSearchDTO>();
  private Set<SpeechTagSearchDTO> unwantedTags = new HashSet<SpeechTagSearchDTO>();
  private Set<ExternalDataBO> externalDataBOs = new HashSet<ExternalDataBO>();

  public Integer getCcGroupId() {
    return ccGroupId;
  }

  public void setCcGroupId(Integer ccGroupId) {
    this.ccGroupId = ccGroupId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public void setClientPhone(String clientPhone) {
    this.clientPhone = clientPhone;
  }

  public String getClientPhone() {
    return clientPhone;
  }

  public HoursRange getHoursRange() {
    return hoursRange;
  }

  public void setHoursRange(HoursRange hoursRange) {
    this.hoursRange = hoursRange;
  }

  public boolean isNull() {
    return ccGroupId == null && userId == null && clientPhone == null && hoursRange == null && (dateRange == null || dateRange.isDefault())
            && (wantedTags == null || wantedTags.isEmpty()) && (unwantedTags == null || unwantedTags.isEmpty()) && description == null;
  }

  public DateRange getDateRange() {
    return dateRange;
  }

  public void setDateRange(DateRange dateRange) {
    this.dateRange = dateRange;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<SpeechTagSearchDTO> getWantedTags() {
    return wantedTags;
  }

  public void setWantedTags(HashSet<SpeechTagSearchDTO> wantedTags) {
    this.wantedTags = wantedTags;
  }

  public void addWantedTag(SpeechTagSearchDTO wantedTag) {
    this.wantedTags.add(wantedTag);
  }

  public Set<SpeechTagSearchDTO> getUnwantedTags() {
    return unwantedTags;
  }

  public void setUnwantedTags(HashSet<SpeechTagSearchDTO> unwantedTags) {
    this.unwantedTags = unwantedTags;
  }

  public void addUnWantedTag(SpeechTagSearchDTO wantedTag) {
    this.unwantedTags.add(wantedTag);
  }

  public Set<ExternalDataBO> getExternalDataBOs() {
    return externalDataBOs;
  }

  public void addExternalDataBO(ExternalDataBO externalDataBO) {
    externalDataBOs.add(externalDataBO);
  }

  public void setExternalDataBOs(Set<ExternalDataBO> externalDataBOs) {
    this.externalDataBOs = externalDataBOs;
  }
}
