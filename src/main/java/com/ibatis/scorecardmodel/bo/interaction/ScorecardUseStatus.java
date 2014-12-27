package com.ibatis.scorecardmodel.bo.interaction;

public enum ScorecardUseStatus {
  EVALUATION("E", true), SURVEY("S", false), TRAINING("T", true), BAD_FOR_EVALUATION("B", false);
  private String value;
  private boolean protect;

  ScorecardUseStatus(String value, boolean protect) {
    this.value = value;
    this.protect = protect;
  }

  public String getMark() {
    return value;
  }

  public static ScorecardUseStatus getField(String fieldName) {
    for (ScorecardUseStatus field : ScorecardUseStatus.values()) {
      if (field.getMark().equals(fieldName)) {
        return field;
      }
    }
    return null;
  }

   public String getOldFlag() {
     if (value.equals("E")) {
      return "E";
    } else if (value.equals("B")) {
      return "e";
    }
    return null;
  }

  public boolean isProtected() {
    return protect;
  }
}