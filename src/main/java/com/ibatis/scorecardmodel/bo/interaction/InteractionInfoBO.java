package com.ibatis.scorecardmodel.bo.interaction;



import com.ibatis.scorecardmodel.BaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * User: juan
 * Date: 8/12/13
 */
public class InteractionInfoBO extends BaseBean {
  public enum Fields {
    COUPLE_ID("coupleId"), COUPLE_CALL_ID("callId"), COUPLE_TYPE("coupleType"), COUPLE_PROBLEM_STATUS("problemStatus"),
    COUPLE_DESCRIPTION("description"), COUPLE_SYNCHRONIZED("synchronized"), COUPLE_MIXED("mixed"), COUPLE_DELETED("deleted"),
    COUPLE_RESTORED("restored"), COUPLE_ARCHIVED("archived"), COUPLE_EVALUATED("evaluated"), COUPLE_B_LOCATION("b_location"),
    COUPLE_SID("sid"), COUPLE_CFTYPES("cftypes"), COUPLE_CFCNT("filesCount"), COUPLE_PROTECTED("protected");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  private int callId;
  private int coupleId;
  private String sid;
  private String description;
  private String problemStatus;
  private String synchroFlag;
  private String deleteFlag;
  private String mixerFlag;
  private String restoreFlag;
  private String archiveFlag;
  private String scorecardFlags;
  private boolean deleteProtection;
  private int cFileCount;
  private int cFileTypes;

  private List<MediaFileBO> mediaFiles = new ArrayList<MediaFileBO>();
  private Map<String, String> attachedData = new TreeMap<String, String>();

  public InteractionInfoBO(){}

  public String getArchiveFlag() {
    return archiveFlag;
  }

  public void setArchiveFlag(String archiveFlag) {
    this.archiveFlag = archiveFlag;
  }

  public int getCallId() {
    return callId;
  }

  public void setCallId(int callId) {
    this.callId = callId;
  }

  public int getcFileCount() {
    return cFileCount;
  }

  public void setcFileCount(int cFileCount) {
    this.cFileCount = cFileCount;
  }

  public int getcFileTypes() {
    return cFileTypes;
  }

  public void setcFileTypes(int cFileTypes) {
    this.cFileTypes = cFileTypes;
  }

  public int getCoupleId() {
    return coupleId;
  }

  public void setCoupleId(int coupleId) {
    this.coupleId = coupleId;
  }

  public String getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(String deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public boolean isDeleteProtection() {
    return deleteProtection;
  }

  public void setDeleteProtection(boolean deleteProtection) {
    this.deleteProtection = deleteProtection;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getScorecardFlags() {
    return scorecardFlags;
  }

  public void setScorecardFlags(String scorecardFlags) {
    this.scorecardFlags = scorecardFlags;
  }

  public String getMixerFlag() {
    return mixerFlag;
  }

  public void setMixerFlag(String mixerFlag) {
    this.mixerFlag = mixerFlag;
  }

  public String getProblemStatus() {
    return problemStatus;
  }

  public void setProblemStatus(String problemStatus) {
    this.problemStatus = problemStatus;
  }

  public String getRestoreFlag() {
    return restoreFlag;
  }

  public void setRestoreFlag(String restoreFlag) {
    this.restoreFlag = restoreFlag;
  }

  public String getSynchroFlag() {
    return synchroFlag;
  }

  public void setSynchroFlag(String synchroFlag) {
    this.synchroFlag = synchroFlag;
  }

  public String getAttachedDataProperty(String property) {
    return attachedData.get(property);
  }

  public void addAttachedDataProperty(String key, String property) {
    this.attachedData.put(key, property);
  }

  public void addMediaFile(MediaFileBO mediaFile) {
    this.mediaFiles.add(mediaFile);
  }

  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }

  public List<MediaFileBO> getMediaFiles() {
    return mediaFiles;
  }

  public Map<String, String> getAttachedData() {
    return attachedData;
  }
}
