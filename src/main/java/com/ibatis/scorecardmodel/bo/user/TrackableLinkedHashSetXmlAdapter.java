package com.ibatis.scorecardmodel.bo.user;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB serialization of TrackableLinkedHashSet.
 * DO NOT USE DIRECTLY.
 *
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 2.5.11
 * Time: 12:37
 */
public class TrackableLinkedHashSetXmlAdapter<T extends BaseBean> extends XmlAdapter<TrackableLinkedHashSetMarshallable<T>, TrackableLinkedHashSet<T>> {

  @Override
  public TrackableLinkedHashSet<T> unmarshal(TrackableLinkedHashSetMarshallable<T> v) throws Exception {
    return v.toTrackableHashSet();
  }

  @Override
  public TrackableLinkedHashSetMarshallable<T> marshal(TrackableLinkedHashSet<T> v) throws Exception {
    return new TrackableLinkedHashSetMarshallable<T>(v);
  }
}
