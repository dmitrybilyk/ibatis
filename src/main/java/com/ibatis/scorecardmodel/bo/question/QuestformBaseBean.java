package com.ibatis.scorecardmodel.bo.question;

import com.ibatis.scorecardmodel.bo.evaluation.WeekdayBean;

import javax.persistence.Transient;

/**
 * User: Martin Zdarsky
 * Date: 10/04/14
 * Time: 10:16
 *
 * This class holds flags for QuestformBO which can be modified by other setters.
 * The RPC serialisation prefers setters which change the value of these flags as the serialization order cannot be relied on.
 *
 * The RPC serializes main bean first and then its super class.
 * For further reference look at <code>ServerSerializationStreamReader.deserializeClass(Class<?>, Object)</code>
 */
public abstract class QuestformBaseBean extends WeekdayBean {

  @Transient
  private boolean permissionsChanged;
  @Transient
  private boolean csRulesChanged;
  @Transient
  private boolean propertiesChanged;
  @Transient
  private boolean preventChangesWhenQformUsedInEvaluation;

  @Override
  public void lock() {
    super.lock();
    setCsRulesChanged(false);
    setPropertiesChanged(false);
    setPermissionsChanged(false);
    setPreventChangesWhenQformUsedInEvaluation(false);
  }

  protected void markPermissionsChanged(boolean value) {
    permissionsChanged |= value;
  }

  protected void markCSRulesChanged(boolean value) {
    csRulesChanged |= value;
  }

  protected void markPropertiesChanged(boolean value) {
    propertiesChanged |= value;
  }

  protected void markPreventChangesChanged(boolean value) {
    preventChangesWhenQformUsedInEvaluation |= value;
  }

  protected boolean isPermissionsChanged() {
    return permissionsChanged;
  }

  protected void setPermissionsChanged(boolean permissionsChanged) {
    this.permissionsChanged = permissionsChanged;
  }

  protected boolean isCsRulesChanged() {
    return csRulesChanged;
  }

  protected void setCsRulesChanged(boolean csRulesChanged) {
    this.csRulesChanged = csRulesChanged;
  }

  protected boolean isPropertiesChanged() {
    return propertiesChanged;
  }

  protected void setPropertiesChanged(boolean propertiesChanged) {
    this.propertiesChanged = propertiesChanged;
  }

  protected boolean isPreventChangesWhenQformUsedInEvaluation() {
    return preventChangesWhenQformUsedInEvaluation;
  }

  protected void setPreventChangesWhenQformUsedInEvaluation(boolean preventChangesWhenQformUsedInEvaluation) {
    this.preventChangesWhenQformUsedInEvaluation = preventChangesWhenQformUsedInEvaluation;
  }
}
