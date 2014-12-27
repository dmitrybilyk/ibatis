package com.ibatis.scorecardmodel.bo.charts;

import com.ibatis.scorecardmodel.bo.user.RightBO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: juan
 * Date: 10/18/13
 * Time: 9:51 AM
 */
public class LogiReportsEntry implements Serializable {
  private String reportId = "";
  private String localizedName = "";
  private String reportUrl = "";
  private List<RightBO.Rights> permissions = new ArrayList<RightBO.Rights>();

  public LogiReportsEntry() {
    // default constructor just for serialization
  }

  public LogiReportsEntry(String reportId, String reportUrl, List<RightBO.Rights> permissions) {
    this.reportId = reportId;
    this.localizedName = reportId;
    this.reportUrl = reportUrl;
    this.permissions.addAll(permissions);
  }

  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  public String getLocalizedName() {
    return localizedName;
  }

  public void setLocalizedName(String localizedName) {
    this.localizedName = localizedName;
  }

  public String getReportUrl() {
    return reportUrl;
  }

  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }

  public List<RightBO.Rights> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<RightBO.Rights> permissions) {
    this.permissions = permissions;
  }

  @Override
  public boolean equals(Object otherObject) {
    if (!(otherObject instanceof  LogiReportsEntry)) {
      return false;
    }

    LogiReportsEntry otherReport = (LogiReportsEntry)otherObject;
    if (this == otherReport) {
      return true;
    }

    return this.getReportId().equals(otherReport.getReportId());
  }

  @Override
  public int hashCode() {
    int number = reportId.equals("") ? 1 : reportId.charAt(0);
    number += reportUrl.equals("") ? 1 : reportUrl.charAt(0);
    return number * 31;
  }

}
