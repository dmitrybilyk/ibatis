package com.ibatis.scorecardmodel.bo.user;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ReplaceableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;

import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * Adapter for JAXB serialization of TrackableLinkedHashSet.
 * DO NOT USE DIRECTLY.
 *
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 2.5.11
 * Time: 12:42
 */
@XmlRootElement(name = "trackableSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackableLinkedHashSetMarshallable<T extends BaseBean> {

  private boolean locked = false;
  @XmlElementWrapper
  @XmlElementRef
  private Set<T> added = new ReplaceableLinkedHashSet<T>();
  @XmlElementWrapper
  @XmlElementRef
  private Set<T> unchanged = new ReplaceableLinkedHashSet<T>();
  @XmlElementWrapper
  @XmlElementRef
  private Set<T> removed = new ReplaceableLinkedHashSet<T>();

  protected TrackableLinkedHashSetMarshallable() {
    //empty constructor for JAXB
  }

  protected TrackableLinkedHashSetMarshallable(TrackableLinkedHashSet<T> set) {
    if(set != null){
      this.locked = set.isLocked();
      this.added.addAll(set.getAdded());
      this.unchanged.addAll(set.getUnchanged());
      this.removed.addAll(set.getRemoved());
    }
  }

  protected TrackableLinkedHashSet<T> toTrackableHashSet() {
    TrackableLinkedHashSet<T> set = new TrackableLinkedHashSet<T>();
    set.addAll(unchanged);
    if (locked) {
      set.addAll(removed);
      set.lock();
      set.addAll(added);
      set.removeAll(removed);
    }
    return set;
  }

}
