package com.ibatis.scorecardmodel.bo.evaluation;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

/** @author Stanislav Valenta, 26.6.2009 */
public class MediaLimitBO extends BaseBean {
  private static final long serialVersionUID = 176933128625187599L;

  public enum Fields {
    CRITERIA("criteriaid"), TYPE("interactionTypeBO"), MINIMUM("minimum");
      
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  private Integer medialimitid;
  private Integer criteriaid;
  private CriteriaBO criteriaBO;
  private Integer interactionType;
  private InteractionTypeBO interactionTypeBO;
  private Integer minimum;

  @Override
  public void lock() {
    super.lock();
    if (interactionTypeBO != null) {
      interactionTypeBO.lock();
    }
  }
  
  public CriteriaBO getCriteriaBO() {
    return criteriaBO;
  }

  public void setCriteriaBO(CriteriaBO criteriaBO) {
    this.criteriaBO = criteriaBO;
    if (criteriaBO != null) {
      markChanged(criteriaid, criteriaBO.getCriteriaid());
      criteriaid = criteriaBO.getCriteriaid();
    } else { 
      markChanged(criteriaid, null);
      criteriaid = null;
    }
  }

  // GETTERS, SETTERS ##################################################################################################


  @Override
  public void setId(Integer value) {
    setMedialimitid(value);
  }

  @Override
  public Integer getId() {
    return getMedialimitid();
  }

  public Integer getMedialimitid() {
    return medialimitid;
  }

  // used by ibatis
  public void setMedialimitid(Integer eventid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.medialimitid = eventid;
  }

  public Integer getCriteriaid() {
    return criteriaid;
  }

  public void setCriteriaid(Integer criteriaid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.criteriaid = criteriaid;
  }

  public Integer getInteractionType() {
    return interactionType;
  }

  
  public void setInteractionType(Integer type) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    this.interactionType = type;
  }

  public void setInteractionTypeBO(InteractionTypeBO interactionTypeBO) {
    this.interactionTypeBO = interactionTypeBO;
    if (interactionTypeBO != null) {
      markChanged(interactionType, interactionTypeBO.getInteractiontypeid());
      interactionType = interactionTypeBO.getInteractiontypeid();
    } else {
      markChanged(interactionType, null);
      interactionType = null;
    }
  }

  public InteractionTypeBO getInteractionTypeBO() {
    return this.interactionTypeBO;
  }

  public Integer getMinimum() {
    return minimum;
  }

  public void setMinimum(Integer minimum) {
    markChanged(this.minimum, minimum);
    this.minimum = minimum;
  }

  
  @Override
  public int hashCode() {
    return ((getMedialimitid() == null) ? 0 : getMedialimitid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof MediaLimitBO))
      return false;
    MediaLimitBO other = (MediaLimitBO) obj;
    if (getMedialimitid() == null || other.getMedialimitid() == null)
      return false;
    return getMedialimitid().equals(other.getMedialimitid());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("MediaLimitBO");
    sb.append("{id=").append(medialimitid);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new MediaLimitBO();
  }

  @Override
  public boolean hasAnythingChanged() {
    return super.hasAnythingChanged() || (interactionTypeBO != null && interactionTypeBO.hasAnythingChanged());
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.TYPE, SearchOrder.Direction.ASC));
    return ordering;
  }
}
