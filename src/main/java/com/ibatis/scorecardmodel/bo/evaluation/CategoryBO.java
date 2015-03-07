package com.ibatis.scorecardmodel.bo.evaluation;





import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.user.CompanyBO;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

/** @author Stanislav Valenta, 9.7.2009 */
public class CategoryBO extends BaseBean {
  private static final long serialVersionUID = 7382243088020732384L;

  public enum Fields {
    CATEGORY_NAME("categoryName"), COMPANY_CATEGORY("company"), CATEGORY_ID("categoryid");
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


  private Integer categoryid;
  private String categoryName;
  private Integer company;

  private CompanyBO companyBO;
  
  public CategoryBO() { 
    categoryName = "";
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
  
  //GETTERS, SETTERS ####################################################################################################

  @Override
  public Integer getId() {
    return getCategoryid();
  }

  @Override
  public void setId(Integer value) {
    setCategoryid(value);
  }

  public Integer getCategoryid() {
    return categoryid;
  }

  // used by ibatis
  public void setCategoryid(Integer categoryid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.categoryid = categoryid;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String category) {
    markChanged(this.categoryName,  category == null ? "" : category.trim());
    this.categoryName = category == null ? "" : category.trim();
  }

  public Integer getCompany() {
    return company;
  }

  public void setCompany(Integer company) {
    this.company = company;
  }

  
  @Override
  public int hashCode() {
    return ((getCategoryid() == null) ? 0 : getCategoryid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof CategoryBO))
      return false;
    CategoryBO other = (CategoryBO) obj;
    if (getCategoryid() == null || other.getCategoryid() == null)
      return false;
    return getCategoryid().equals(other.getCategoryid());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("CategoryBO");
    sb.append("{id=").append(categoryid);
    sb.append('}');
    return sb.toString();
  }

    @Override
  public BaseBean newInstance() {
    return new CategoryBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.CATEGORY_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }
}
