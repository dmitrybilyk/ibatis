package com.ibatis.scorecardmodel.bo.charts;


import com.ibatis.scorecardmodel.bo.user.LanguageBO;
import com.ibatis.scorecardmodel.bo.user.RightBO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * User: juan
 * Date: 8/30/13
 * Time: 5:06 PM
 */
public class LogiReportsData implements Serializable {
  private LogiReportsConfiguration configuration = new LogiReportsConfiguration();
  // linked hashmap to maintain the order from the xml
  private List<LogiReportsEntry> reports = new LinkedList<LogiReportsEntry>();
  private LanguageBO reportsLanguage;

  public LogiReportsConfiguration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(LogiReportsConfiguration configuration) {
    this.configuration = configuration;
  }

  public List<LogiReportsEntry> getReports() {
    return reports;
  }

  public void setReports(List<LogiReportsEntry> reports) {
    this.reports = reports;
  }

  public void addReport(String reportId, String reportUrl, List<RightBO.Rights> permissions) {
//    LogiReportsEntry entry = new LogiReportsEntry(reportId, reportUrl, permissions);

//    if (this.reports.contains(entry)) {
//      int position = reports.indexOf(entry);
//      reports.set(position, entry);
//    } else {
//      this.reports.add(entry);
//    }
  }

  public boolean isValid() {
    return configuration.isConfigurationValid() && areReportsValid();
  }

  public LanguageBO getReportsLanguage() {
    return reportsLanguage;
  }

  public void setReportsLanguage(LanguageBO reportsLanguage) {
    this.reportsLanguage = reportsLanguage;
  }

  /**
   * Checks if a given language is the same that used to localize the reports
   * @param requestedLanguage the language to compare with
   * @return true if the language parameter is the same as the one currently used to localize the reports, false otherwise
   */
  public boolean isSameLanguage(LanguageBO requestedLanguage) {
    if (this.reportsLanguage == null) {
      return false;
    }
    return this.reportsLanguage.equals(requestedLanguage);
  }

  private boolean areReportsValid() {
    for (LogiReportsEntry logiReportsEntry : this.reports) {
      if ("".equals(logiReportsEntry.getReportId()) || "".equals(logiReportsEntry.getReportUrl())) {
        return false;
      }
    }
    return true;
  }
}
