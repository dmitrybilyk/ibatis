package com.ibatis.scorecardmodel.bo;

import java.util.Collection;
import java.util.LinkedHashSet;


public class ReplaceableLinkedHashSet<E> extends LinkedHashSet<E> {
  private static final long serialVersionUID = 9049267079268680442L;


  /**
   * Constructs a new, empty linked hash set with the default initial
   * capacity (16) and load factor (0.75).
   */
  public ReplaceableLinkedHashSet() {
    super();
  }


  /**
   * Constructs a new linked hash set with the same elements as the
   * specified collection.  The linked hash set is created with an initial
   * capacity sufficient to hold the elements in the specified collection
   * and the default load factor (0.75).
   * @param c the collection whose elements are to be placed into
   *          this set
   * @throws NullPointerException if the specified collection is null
   */
  public ReplaceableLinkedHashSet(Collection<? extends E> c) {
    super(c);
  }


  /**
   * Adds the specified element to this set regardless of it is already present or not.
   * More formally, adds the specified element <tt>e</tt> to this set. If
   * this set contains such an element, the call replaces the current object with a new one.
   *
   * @param e element to be added to this set
   * @return always <tt>true</tt> 
   */
  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("Do NOT insert null into the ReplaceableLinkedHashSet!!!");
    if (contains(e))
      remove(e);
    return super.add(e);
  }
}
