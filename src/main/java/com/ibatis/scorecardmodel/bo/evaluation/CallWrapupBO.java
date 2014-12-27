//package com.ibatis.scorecardmodel.bo.evaluation;
//
//
//import com.ibatis.scorecardmodel.BaseBean;
//import com.ibatis.scorecardmodel.bo.user.CompanyBO;
//import com.ibatis.search.SearchOrder;
//
//import java.util.ArrayList;
//import java.util.List;
//
///** @author Stanislav Valenta, 26.6.2009 */
//public class CallWrapupBO extends BaseBean {
//  private static final long serialVersionUID = -8289301455690678945L;
//
//
//  public enum Fields {
//    KEY("key"), DESCRIPTION("description"), COMPANY("company");
//    private final String fieldName;
//
//    Fields(final String fieldName) {
//      this.fieldName = fieldName;
//    }
//
//    public String getFieldName() {
//      return fieldName;
//    }
//
//    public static Fields getField(String fieldName) {
//      for (Fields field: Fields.values())
//        if (field.getFieldName().equals(fieldName))
//          return field;
//      return null;
//    }
//  }
//
//
//  private Integer callwrapupid;
//  private String key;
//  private String description;
//  private Integer company;
//
//  private CompanyBO companyBO;
//
//  public CallWrapupBO() {
//    key = "";
//    description = "";
//  }
//
//  public CompanyBO getCompanyBO() {
//    return companyBO;
//  }
//
//  public void setCompanyBO(CompanyBO companyBO) {
//    this.companyBO = companyBO;
//    if (companyBO != null) {
//      markChanged(company, companyBO.getCompanyid());
//      company = companyBO.getCompanyid();
//    } else {
//      markChanged(company, null);
//      company = null;
//    }
//  }
//
//  // GETTERS, SETTERS ##################################################################################################
//
//
//  public Integer getCallwrapupid() {
//    return callwrapupid;
//  }
//
//  // used by ibatis
//  public void setCallwrapupid(Integer callwrapupid) {
//    if (isLocked()) {
//      throw new UnsupportedOperationException("You cannot set a primary key");
//    }
//    this.callwrapupid = callwrapupid;
//  }
//
//  @Override
//  public Integer getId() {
//    return getCallwrapupid();
//  }
//
//  @Override
//  public void setId(Integer id) {
//    setCallwrapupid(id);
//  }
//
//  public String getKey() {
//    return key;
//  }
//
//  public void setKey(String key) {
//    markChanged(this.key, key == null ? "" : key.trim());
//    this.key = key == null ? "" : key.trim();
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//  public void setDescription(String description) {
//    markChanged(this.description, description == null ? "" : description.trim());
//    this.description = description == null ? "" : description.trim();
//  }
//
//  public Integer getCompany() {
//    return company;
//  }
//
//  // used by ibatis
//  @SuppressWarnings({"unused"})
//  private void setCompany(Integer company) {
//    if (isLocked())
//      throw new UnsupportedOperationException("You cannot set a foreign key");
//    this.company = company;
//  }
//
//  @Override
//  public int hashCode() {
//    return ((getCallwrapupid() == null) ? 0 : getCallwrapupid().hashCode());
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (!(obj instanceof CallWrapupBO))
//      return false;
//    CallWrapupBO other = (CallWrapupBO) obj;
//    if (getCallwrapupid() == null || other.getCallwrapupid() == null)
//      return false;
//    return getCallwrapupid().equals(other.getCallwrapupid());
//  }
//
//
//  @Override
//  public String toString() {
//    final StringBuilder sb = new StringBuilder();
//    sb.append("CallWrapupBO");
//    sb.append("{id=").append(callwrapupid);
//    sb.append(", key='").append(key).append('\'');
//    sb.append(", description='").append(description).append('\'');
//    sb.append('}');
//    return sb.toString();
//  }
//
//  @Override
//  public BaseBean newInstance() {
//    return new CallWrapupBO();
//  }
//
//  @Override
//  public List<SearchOrder> defaultSortOrder() {
//    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
//    ordering.add(new SearchOrder(Fields.KEY, SearchOrder.Direction.ASC));
//    return ordering;
//  }
//}
