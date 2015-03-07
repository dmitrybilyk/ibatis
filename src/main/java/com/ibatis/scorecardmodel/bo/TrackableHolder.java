package com.ibatis.scorecardmodel.bo;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.Trackable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Zdary
 * Date: 11/11/13
 * Time: 16:43
 * <p/>
 * Holds a single object and, after being {@link #lock() locked}, keeps track of changes to this object reference,
 * allowing to find out if the object reference has changed (added, removed or both).
 * This enables keeping track of changes on 1..1 relation from a database perspective.
 */
public class TrackableHolder<E extends BaseBean> implements Trackable, Iterable, Serializable {
  private static final long serialVersionUID = 2349267042636455442L;
  private boolean locked = false;
  private E current;
  private E original;

  @Override
  public void lock() {
    locked = true;
  }

  @Override
  public void unlock() {
    original = current;
    locked = false;
  }

  @Override
  public boolean isLocked() {
    return locked;
  }

  /**
   * Checks whether the held object reference has changed since being locked.
   * Always returns false, if this reference has not been locked.
   *
   * @return true if the reference has changed since being locked;
   * false if never locked, or if still holding the same object as before being locked
   */
  @Override
  public boolean hasChanged() {
    return original != current;
  }

  /**
   * This method is an extension of hasChanged method. It checks whether the element itself has changed.
   *
   * @return true if the reference or its contents has changed since being locked;
   * false if never locked, or if still holding the same object as before being locked and this object hasn't changed
   */
  @Override
  public boolean hasAnythingChanged() {
    return hasChanged() || (current != null && current.hasChanged());
  }

  /**
   * @return the current value
   */
  public E get() {
    return current;
  }

  /**
   * Sets the new current value. If unlocked then it also set the original.
   *
   * @param newValue the new object to hold
   */
  public void set(E newValue) {
    current = newValue;
    if (!locked) {
      original = newValue;
    }
  }

  /**
   * Convenience method to establish what has been added.
   * Returns the newly held reference, if the reference has changed after the holder was locked.
   * Returns null if not locked, if the reference hasn't changed, or if the new reference is null.
   *
   * @return added element or null
   */
  public E getAdded() {
    if (hasChanged()) {
      return current;
    }
    return null;
  }

  /**
   * Convenience method to establish what has been deleted.
   * Returns the previously held reference, if the reference has changed after the holder was locked.
   * Returns null if not locked, if the reference hasn't changed, or if the previous reference was null.
   *
   * @return deleted element or null
   */
  public E getRemoved() {
    if (hasChanged()) {
      return original;
    }
    return null;
  }

  @Override
  public Iterator iterator() {
    return Collections.singletonList(current).iterator();
  }
}
