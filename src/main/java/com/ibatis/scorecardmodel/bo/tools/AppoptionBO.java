package com.ibatis.scorecardmodel.bo.tools;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.user.CompanyBO;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


public class AppoptionBO extends BaseBean implements Options<AppoptionBO.KEY> {
  private static final long serialVersionUID = 1097491698523732722L;

  public enum Fields {
    KEY("keyEnum"), VALUE("value"), COMPANY("company"), COMMENT("comment"), COMPANY_BO("companyBO");
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

  public enum KEY {
    NOT_APPLICABLE_ANSWER_CALCULATING_METHOD,
    IPCC_SUPERVISOR_EQ,
    IPCC_AGENT_EQ,
    RECORDS_ON_PAGE,
    CUSTOM_WEEK_INTERVAL_START,
    DASHBOARD,
    CUSTOM_MONTH_INTERVAL_START,
    DEFAULT_LOCALE,
    AGENT_EVALUATION_NOTIFICATION,
    DASHBOARD_WIDGET_LIMIT,
    SELFEVALUATION_APPLY_TO_STATS,
    PREFER_USER_LANG_FOR_SORTING,
    INTERACTIONS_COUNT_MAX_LIMIT,
    APPLET_COMMUNICATION_CHANNEL,
    UPL_SKIP_SILENT_PARTS,
    CUSTOM_TRACKING_LABEL_1,
    CUSTOM_TRACKING_LABEL_2,
    HIDDEN_NAVIGATION_ITEMS
  }

  /* memory cache with all possible values.
     if defined directly in a loop, a new copy would be created every time
   */
  private static final EnumSet<KEY> keyValues =  EnumSet.allOf(KEY.class);

  private Integer appoptionid;
  private KEY key;
  private String value;
  private String comment;
  private Integer company;
  private CompanyBO companyBO;

  public AppoptionBO() {
    value = null;
    comment = "";
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    if (companyBO != null) {
      markChanged(company, companyBO.getCompanyid());
      this.company = companyBO.getCompanyid();
    } else {
      markChanged(company, null);
      this.company = null;
    }
    this.companyBO = companyBO;
  }

  @Override
  public Integer getId() {
    return getAppoptionid();
  }

  @Override
  public void setId(Integer value) {
    setAppoptionid(value);
  }

  //used by ibatis
  private Integer getAppoptionid() {
    return appoptionid;
  }

  //used by ibatis
  public void setAppoptionid(Integer appoptionid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.appoptionid = appoptionid;
  }

  //used by ibatis
  protected String getKey() {
    return key.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setKey(String key) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Use method setKey(enum KEY) instead.");
    }

    if (doesKeyExist(key)) {
      this.key = KEY.valueOf(key);
    } else {
      this.key = null;
    }
  }

  private static boolean doesKeyExist(String keyName) {
    if (keyName == null) {
      return false;
    }

    for (KEY keyValue : keyValues) {
      if (keyValue.name().equals(keyName)) {
        return true;
      }
    }
    return false;
  }

  public KEY getKEYEnum() {
    return key;
  }

  public void setKEYEnum(KEY key) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    markChanged(this.value, value == null ? null : value.trim());
    this.value = value == null ? null : value.trim();
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    markChanged(this.comment, comment == null ? "" : comment.trim());
    this.comment = comment == null ? "" : comment.trim();
  }

  public Integer getCompany() {
    return company;
  }

  //used by ibatis
  private void setCompany(Integer company) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    markChanged(this.company, company);
    this.company = company;
  }

  @Override
  public int hashCode() {
    return getAppoptionid() == null ? 0 : getAppoptionid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof AppoptionBO))
      return false;
    AppoptionBO other = (AppoptionBO) obj;
    if (getAppoptionid() == null || other.getAppoptionid() == null)
      return false;
    return getAppoptionid().equals(other.getAppoptionid());
  }

  /**
   * Constructs a <code>String</code> with all attributes
   * in name = value format.
   *
   * @return a <code>String</code> representation
   *         of this object.
   */
  public String toString() {
    final String TAB = "\n    ";

    StringBuilder retValue = new StringBuilder();

    retValue.append("\nAppoptionBO (").append(TAB)
            .append("appoptionid = ").append(this.appoptionid).append(TAB)
            .append("key = ").append(this.key).append(TAB)
            .append("value = ").append(this.value).append(TAB)
            .append("comment = ").append(this.comment).append(TAB)
            .append("comapny = ").append(this.company).append(TAB)
            .append(" )\n");

    return retValue.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new AppoptionBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.KEY, SearchOrder.Direction.ASC));
    return ordering;
  }
}