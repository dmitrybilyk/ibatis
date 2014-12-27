package com.ibatis.scorecardmodel.bo.charts;

import java.io.Serializable;

/**
 * User: juan
 * Date: 8/30/13
 * Time: 4:50 PM
 */
public class LogiReportsConfiguration implements Serializable {
  private String applicationName;
  private String reportServerAddress;
  private String reportServerPort;
  private String reportUrl;
  private String reportParameter;
  private String securekeyServerAddress;
  private String securekeyServerPort;
  private String securekeyUrl;

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getReportServerAddress() {
    return reportServerAddress;
  }

  public void setReportServerAddress(String reportServerAddress) {
    this.reportServerAddress = reportServerAddress;
  }

  public String getReportServerPort() {
    return reportServerPort;
  }

  public void setReportServerPort(String reportServerPort) {
    this.reportServerPort = reportServerPort;
  }

  public String getReportUrl() {
    return reportUrl;
  }

  public void setReportUrl(String reportUrl) {
    this.reportUrl = reportUrl;
  }

  public String getSecurekeyServerAddress() {
    return securekeyServerAddress;
  }

  public void setSecurekeyServerAddress(String securekeyServerAddress) {
    this.securekeyServerAddress = securekeyServerAddress;
  }

  public String getSecurekeyServerPort() {
    return securekeyServerPort;
  }

  public void setSecurekeyServerPort(String securekeyServerPort) {
    this.securekeyServerPort = securekeyServerPort;
  }

  public String getSecureKeyUrl() {
    return securekeyUrl;
  }

  public void setSecurekeyUrl(String securekeyUrl) {
    this.securekeyUrl = securekeyUrl;
  }

  public String getReportParameter() {
    return reportParameter;
  }

  public void setReportParameter(String reportParameter) {
    this.reportParameter = reportParameter;
  }

  public boolean isConfigurationValid() {
    return !applicationName.equals("") && !reportServerAddress.equals("") && !reportServerPort.equals("0") &&
            !reportUrl.equals("") && !securekeyServerAddress.equals("") && !securekeyServerPort.equals("0") &&
            !securekeyUrl.equals("");
  }
}
