package com.ibatis.scorecardmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "baseBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseBean implements Serializable, Trackable {
  private static final long serialVersionUID = -6875912678629565434L;
  private final static double EPSILON = 0.00001;

  private boolean toBeDeleted = false;
  private boolean assigned = false;
  private boolean changed = false;
  private boolean locked = false;

  /**
   * @deprecated This class should be abstract - do not create instances of this class.
   */
  @Deprecated
  public BaseBean() {}

  public Integer getId(){
    return 0;
  }

  public void setId(Integer value) {}

  @Override
  public boolean hasChanged() {
    return changed;
  }

  @Override
  public boolean hasAnythingChanged() {
    return hasChanged() || isToBeDeleted() || isAssigned();
  }

  public boolean isToBeDeleted() {
    return toBeDeleted;
  }

  public void setAssigned(boolean assigned) {
    this.assigned = assigned;
  }

  public boolean isAssigned() {
    return assigned;
  }

  @Override
  public void lock() {
    locked = true;
    changed = false;
  }

  public void setToBeDeleted(boolean toBeDeleted) {
    this.toBeDeleted = toBeDeleted;
  }

  @Override
  public void unlock() {
    locked = false;
  }

  @Override
  public boolean isLocked() {
    return locked;
  }

  /**
   * @return true if the intention was to set markChanged. Other methods markChanged resolved value to true
   */
  protected boolean markChanged(boolean value) {
    changed |= value;
    return value;
  }

  protected boolean markChanged(Boolean oldValue, Boolean newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(Date oldValue, Date newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(Double oldValue, Double newValue) {
    if (oldValue == null) {
      return markChanged(newValue != null);
    }
    if (newValue == null) {
      return markChanged(true);
    }
    return markChanged(!equals(oldValue, newValue));
  }

  protected boolean markChanged(BigDecimal oldValue, BigDecimal newValue) {
    if (oldValue == null) {
      return markChanged(newValue != null);
    }
    if (newValue == null) {
      return markChanged(true);
    }
    return markChanged(oldValue.compareTo(newValue) != 0);
  }

  protected boolean markChanged(Integer oldValue, Integer newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(Long oldValue, Long newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(String oldValue, String newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(Enum<?> oldValue, Enum<?> newValue) {
    if (oldValue == null)
      return markChanged(newValue != null);
    else
      return markChanged(!oldValue.equals(newValue));
  }

  protected boolean markChanged(Set oldValue, Set newValue) {
    if (oldValue == null) {
      return markChanged(newValue != null);
    }
    if (newValue == null) {
      return markChanged(true);
    }
    if (oldValue.size() != newValue.size()) {
      return markChanged(true);
    }
    return markChanged(!oldValue.containsAll(newValue));
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof BaseBean))
      return false;
    return true;
  }

  public BaseBean newInstance(){
    return new BaseBean();
  }

  public List defaultSortOrder(){
    return Collections.emptyList();
  }

  public BaseBean clone() throws RuntimeException {
    // CloneNotSupportedException is not supported by GWT. RuntimeException will do.
    throw new RuntimeException("CloneNotSupportedException");
  }

  /**
   * Returns true if two doubles are considered equal. Tests if the absolute
   * difference between two doubles has a difference less then EPSILON.
   *
   * @param a double to compare.
   * @param b double to compare.
   * @return true true if two doubles are considered equal.
   */
  private boolean equals(double a, double b){
    return a == b || Math.abs(a - b) < EPSILON;
  }
}
