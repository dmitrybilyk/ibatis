package com.ibatis.scorecardmodel.bo;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.Trackable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * This class holds not only data like any other Set but also, when locked, 
 * can keep track of which object is new, restored and which has been deleted.
 * 
 * When the TrackableLinkedHashSet hasn't been locked yet then it behaves like ordinary set.
 * All data are kept in unchanged set and this class is just an envelope for it.
 * 
 * When this class has been locked then newly added items are kept in added set. 
 * Deleted items are moved to deleted set if they have been in unchanged set. If they have been in added set then the item gets removed completely.
 * When you add an item which has been deleted then the item will be moved from deleted set to unchanged set.
 * 
 * The whole purpose of this class is that at the end of data manipulation you'll know which items have been deleted and which are completely new.
 * In this way you can take further action on a database, for example. 
 * 
 * @author Martin Zdarsky
 *
 * @param <E> any item, Preferably an inherited item of a BaseBean
 */
@XmlRootElement(name = "trackableSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackableLinkedHashSet<E> extends AbstractSet<E> implements Set<E>, Trackable, Serializable {
  private static final long serialVersionUID = 8349267079263680442L;
  private boolean locked = false;
  private Set<E> added = new ReplaceableLinkedHashSet<E>();
  private Set<E> unchanged = new ReplaceableLinkedHashSet<E>();
  private Set<E> removed = new ReplaceableLinkedHashSet<E>();

  /**
   * Constructs a new, empty linked hash set with the default initial
   * capacity (16) and load factor (0.75).
   */
  public TrackableLinkedHashSet() {
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
  public TrackableLinkedHashSet(Collection<? extends E> c) {
    super();
    addAll(c);
  }


  /**
   * If not locked, Adds the specified element to unchanged set regardless of it is already present or not.
   * More formally, adds the specified element <tt>e</tt> to this set. If
   * this set contains such an element, the call replaces the current object with a new one.
   * 
   * If locked, firstly check whether this element is present in deleted set. if it is then the element will
   * be moved to unchanged set, otherwise adds the specified element to added set regardless of it is already present or not.
   *
   * @param e element to be added to this set
   * @return always <tt>true</tt> 
   */
  @Override
  public boolean add(E e) {
    if (!locked || unchanged.contains(e)) 
      return unchanged.add(e);

    if (added.contains(e)) {
      return added.add(e);
    }

    if (removed.contains(e)) {
      removed.remove(e);
      BaseBean baseBean = getBaseBean(e);
      if (baseBean != null)
        baseBean.setToBeDeleted(false);
      return unchanged.add(e);
    }

    BaseBean baseBean = getBaseBean(e);
    if (baseBean != null && baseBean.getId() != null)
      baseBean.setAssigned(true);
    return added.add(e);
  }
  
  /**
   * locks this set so that the set starts to keep track of data changes.
   */
  @Override
  public void lock() {
    locked = true;
  }
  
  /**
   * Unlocks this set so that elements from added set are moved to unchanged set and elements from removed set are disposed.
   */
  @Override
  public void unlock() {
    locked = false;    
    unchanged.addAll(added);
    clearAdded();
    clearRemoved();
  }
  
  /**
   * This method indicates that some elements are deleted or added.
   * This makes sense only if the set has been locked.
   * @return false, if the set hasn't been locked. true if anything has been added or removed. Otherwise false 
   */
  @Override
  public boolean hasChanged() {
    return added.size() > 0 || removed.size() > 0;
  }
  
  /**
   * This method is an extension of hasChanged method. It checks whether elements from unchanged set have changed.
   * 
   * @return false, if the set hasn't been locked. true if anything has changed. Otherwise false
   */
  @Override
  public boolean hasAnythingChanged() {
    boolean changed = hasChanged();
    if (!changed) {
      for (E e: unchanged) {
        BaseBean baseBean = getBaseBean(e);
        if (baseBean != null)
          changed |= baseBean.hasAnythingChanged();
      }
    }
    return changed;
  }

  /**
   * Removes all elements from all sets regardless of locking.
   */
  public void clearAll() {
    unchanged.clear();
    if (locked) {
      clearAdded();
      clearRemoved();
    }
  }
  
  private void clearAdded() {
    added.clear();
  }

  private void clearRemoved() {
    removed.clear();
  }
  
  /**
   * If not locked return contains(o) otherwise check if the element is present in any of sets including removed set.
   * 
   * @param o element
   * @return true if the element is present.
   */
  public boolean reallyContains(Object o) {
    if (!locked)
      return unchanged.contains(o);
    
    return unchanged.contains(o) || added.contains(o) || removed.contains(o);
  }

  /**
   * If not locked returns <tt>true</tt> if unchanged set contains the specified element.
   * More formally, returns <tt>true</tt> if and only if unchanged set
   * contains an element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * If locked checks if the element is present in added or unchanged sets (excluding removed set).
   * 
   * @param o element whose presence in this set is to be tested
   * @return <tt>true</tt> if the set or sets contain(s) the specified element
   */
  @Override
  public boolean contains(Object o) {
    if (!locked)
      return unchanged.contains(o);
    
    return unchanged.contains(o) || added.contains(o);
  }

  /**
   * If not locked returns <tt>true</tt> if unchanged set contains no elements otherwise it check added set as well.
   *
   * @return <tt>true</tt> if the set contains no elements
   */
  @Override
  public boolean isEmpty() {
    if (!locked)
      return unchanged.isEmpty();
    
    return unchanged.isEmpty() && added.isEmpty();
  }

  /**
   * If not locked returns an iterator over the elements in the unchanged set.
   * If locked creates and returns a new iterator made from a new set containing all elements from unchanged and added sets.
   *
   * @return an iterator over the elements in the set
   */
  @Override
  public Iterator<E> iterator() {
    if (!locked)
      return unchanged.iterator();
    
    return new TrackableLinkedHashSetIterator();
  }

  /**
   * If not locked removes the specified element from the unchanged set
   * if it is present (optional operation).  More formally, removes an element 
   * <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
   * this set contains such an element.  Returns <tt>true</tt> if this set
   * contained the element (or equivalently, if this set changed as a
   * result of the call).  (This set will not contain the element once the
   * call returns.)
   *
   * If the set is locked removes the element from the appropriate set (added or unchanged).
   * 
   * @param o object to be removed from this set, if present
   * @return <tt>true</tt> if this set contained the specified element
   * @throws ClassCastException if the type of the specified element
   *         is incompatible with this set (optional)
   * @throws NullPointerException if the specified element is null and this
   *         set does not permit null elements (optional)
   * @throws UnsupportedOperationException if the <tt>remove</tt> operation
   *         is not supported by this set
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean remove(Object o) {
    if (!locked)
      return unchanged.remove(o);

    if (!contains(o)) {
      return false;
    }

    if (added.contains(o)) {
      return added.remove(o);
    }

    unchanged.remove(o);
    BaseBean baseBean = getBaseBean(o);
    if (baseBean != null)
      baseBean.setToBeDeleted(true);
    return removed.add((E)o);
  }
  
  /**
   * removes all elements using remove method. 
   * @param c collection of elements to remove
   * @return true if any element has been removed.
   */
  public boolean removeAll(Collection<?> c) {
    boolean modified = false;
    for (Object o: c) {
      modified |= remove(o);
    }
    return modified;
  }
  
  /**
   * removes all elements using remove method.
   */
  @Override
  public void clear() {
    while (!added.isEmpty()) {
      Object o = added.iterator().next();
      remove(o);
    }
    while (!unchanged.isEmpty()) {
      Object o = unchanged.iterator().next();
      remove(o);
    }
  }

  /**
  * If not locked returns the number of elements in the unchanged set (its cardinality).  If the unchanged
  * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
  * <tt>Integer.MAX_VALUE</tt>.
  * 
  * If locked then returns sum of size of added and unchanged sets.
  *
  * @return the number of elements in this set (its cardinality)
  */
  @Override
  public int size() {
    if (!locked)
      return unchanged.size();
    
    return unchanged.size() + added.size(); 
  }
  
  /**
   * Check whether the given element is instance of the BaseBean.
   * @param object
   * @return BaseBean otherwise null
   */
  private BaseBean getBaseBean(Object object) {
    if (object == null)
      return null;
    
    if (object instanceof BaseBean)
      return (BaseBean)object;
    
    return null;
  }
  
  /**
   * @return a soft copy of the added set.
   */
  public Set<E> getAdded() {
    return new ReplaceableLinkedHashSet<E>(added);
  }

  /**
   * @return a soft copy of the removed set.
   */
  public Set<E> getRemoved() {
    return new ReplaceableLinkedHashSet<E>(removed);
  }
  
  /**
   * @return a soft copy of the unchanged set.
   */
  public Set<E> getUnchanged() {
    return new ReplaceableLinkedHashSet<E>(unchanged);
  }
  
  
  /**
   * @return a soft copy of all sets.
   */
  public Set<E> getAll() {
    Set<E> set = new ReplaceableLinkedHashSet<E>(unchanged);
    set.addAll(added);
    set.addAll(removed);
    return set;
  }

  /**
   * Moves all elements to the unchanged set and locks the set (start tracking changes). 
   */
  public void relock(){
    removed.clear();
    unchanged.addAll(added);
    added.clear();
    lock();
  }

  /**
   * Add all elements from parameter to this set, keeping their deleted/added/unchanged status
   * @param set a TrackableLinkedHashSet 
   */
  public void addAllStateful(TrackableLinkedHashSet<E> set){
    removed.addAll(set.getRemoved());
    added.addAll(set.getAdded());
    unchanged.addAll(set.getUnchanged());
  }

  public void cloneAll(TrackableLinkedHashSet<E> set) {
    for (E e: set.getRemoved()) {
      if (e instanceof BaseBean) {
        removed.add((E)((BaseBean)e).clone());
      } else {
        removed.add(e);
      }
    }
    for (E e: set.getAdded()) {
      if (e instanceof BaseBean) {
        added.add((E)((BaseBean)e).clone());
      } else {
        added.add(e);
      }
    }
    for (E e: set.getUnchanged()) {
      if (e instanceof BaseBean) {
        unchanged.add((E)((BaseBean)e).clone());
      } else {
        unchanged.add(e);
      }
    }
  }

  /**
   * Iterator which iterates over added and unchanged sets.
   *
   */
  private class TrackableLinkedHashSetIterator implements Iterator<E>, Serializable {
    private static final long serialVersionUID = -2747484722532498021L;
    
    private Iterator<E> iteratorUnchanged;
    private Iterator<E> iteratorAdded;
    private Iterator<E> iteratorInUse;
    
    public TrackableLinkedHashSetIterator() {
      iteratorUnchanged = unchanged.iterator();
      iteratorAdded = added.iterator();
      iteratorInUse = iteratorUnchanged;
    }
    @Override
    public boolean hasNext() {
      if (!iteratorUnchanged.hasNext()) {
        iteratorInUse = iteratorAdded;
      }
      return iteratorInUse.hasNext();
    }

    @Override
    public E next() {
      if (!iteratorUnchanged.hasNext()) {
        iteratorInUse = iteratorAdded;
      }
      return iteratorInUse.next();
    }

    @Override
    public void remove() {
      iteratorInUse.remove();
    }
  }

  @Override
  public boolean isLocked() {
    return locked;
  }

  public TrackableLinkedHashSet<E> clone() {
    TrackableLinkedHashSet<E> clone = new  TrackableLinkedHashSet<E>();
    clone.cloneAll(this);
    if (isLocked()) {
      clone.lock();
    }
    return clone;
  }
}
