package com.ibatis.scorecardmodel.bo.evaluation;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.Localizable;
import com.ibatis.scorecardmodel.bo.user.CompanyBO;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 3.12.2009
 * Time: 10:07:10
 * To change this template use File | Settings | File Templates.
 */
public class InteractionTypeBO extends BaseBean implements Localizable {
  private static final long serialVersionUID = 3575890619213230551L;
  public static final String CALL_SYSTEM_TYPE_NAME = "Call";
  public static final String VIDEO_SYSTEM_TYPE_NAME = "Screen";
  public static final String AUDIO_VIDEO_SYSTEM_TYPE_NAME = "CallScreen";

  public static final int CALL_FILE_TYPE = 1;
  public static final int VIDEO_FILE_TYPE = 16;
  public static final int AUDIO_VIDEO_FILE_TYPE = 17;
  public static final int EXTENDED_AUDIO_VIDEO_FILE_TYPE = 33;
  public static final int EXTENDED_VIDEO_FILE_TYPE = 32;
  public static final int EXTENDED_VIDEO_MIXED_FILE_TYPE = 48;
  public static final int EXTENDED_AUDIO_VIDEO_MIXED_FILE_TYPE = 49;


  @Override
  public boolean isLocalized() {
    return getType() == Type.SYSTEM;
  }

  @Override
  public String getLocalizationKey() {
    String localizationName = this.getClass().getName() + "_" + getName();
    return localizationName.replaceAll("\\.", "_");
  }


  public enum Fields {
    INTERACTIONTYPE_NAME("name"), TYPE("type"), COMPANY_INTERACTIONTYPE("company"), CF_TYPES(""),
    CF_TYPES_VIDEO_AUDIO(""), CF_TYPES_AUDIO(""), CF_TYPES_VIDEO(""), INTERACTIONTYPE_ID("interactiontypeid");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  public enum Type {
    SYSTEM, USER
  }

  private Integer interactiontypeid;
  private String name;
  private Type type;
  private CompanyBO companyBO;
  private Integer company;

  public InteractionTypeBO() {
    name = "";
    type = Type.USER;
  }

  public Integer getInteractiontypeid() {
    return interactiontypeid;
  }

  //used by ibatis
  public void setInteractiontypeid(Integer interactiontypeid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.interactiontypeid = interactiontypeid;
  }

  @Override
  public Integer getId() {
    return getInteractiontypeid();
  }

  @Override
  public void setId(Integer value) {
    setInteractiontypeid(value);
  }

  public int getCompany() {
    return company;
  }

  @SuppressWarnings("unused")
  private void setCompany(int company) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.company = company;
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    this.companyBO = companyBO;
    if (companyBO != null) {
      markChanged(company, companyBO.getCompanyid());
      company = companyBO.getCompanyid();
    } else {
      markChanged(company, null);
      company = null;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    markChanged(this.name, name == null ? "" : name.trim());
    this.name = name == null ? "" : name.trim();
  }

  public Type getType() {
    return type;
  }

  /**
   * Type should always be {@link Type#USER USER},
   * with the exception of the already existing predefined {@link Type#SYSTEM SYSTEM} types.
   * This method should not be used directly, except for testing.
   */
  public void setType(Type type) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set type");
    }
    this.type = type;
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    if(companyBO != null){
      changed |= companyBO.hasAnythingChanged();
    }
    return changed;
  }


  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("InteractionTypeBO");
    sb.append("{name='").append(name).append('\'');
    sb.append(", companyBO=").append(companyBO);
    sb.append(", type=").append(type);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return ((getInteractiontypeid() == null) ? 0 : getInteractiontypeid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof InteractionTypeBO)) {
      return false;
    }
    InteractionTypeBO other = (InteractionTypeBO) obj;
    if (getInteractiontypeid() == null || other.getInteractiontypeid() == null) {
      return false;
    }
    return getInteractiontypeid().equals(other.getInteractiontypeid());
  }

  @Override
  public BaseBean newInstance() {
    return new InteractionTypeBO();
  }

  public boolean isCall(){
    return name.compareTo(CALL_SYSTEM_TYPE_NAME) == 0 || name.compareTo(AUDIO_VIDEO_SYSTEM_TYPE_NAME) == 0;
  }
  public boolean isVideo(){
    return name.compareTo(VIDEO_SYSTEM_TYPE_NAME) == 0 || name.compareTo(AUDIO_VIDEO_SYSTEM_TYPE_NAME) == 0;
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.TYPE, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.INTERACTIONTYPE_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }

}
