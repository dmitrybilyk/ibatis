package com.ibatis.scorecardmodel.bo.user;

import com.google.common.base.Joiner;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** @author Martin Zdarsky, 26.5.2009 */
@XmlRootElement(name = "role")
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
@Table(name="roles")
public class RoleBO extends BaseBean {
  private static final long serialVersionUID = 2103480789218636461L;


  public RoleBO() {
  }

  public RoleBO(RoleBO roleBO) {
    this.setRoleid(roleBO.getRoleid());
    this.setToBeDeleted(roleBO.isToBeDeleted());
  }

  public enum Fields {
    ROLE_NAME("name"), ROLE_DESC("description"), COMPANY_ROLE("company"), ROLE_ID("roleId");
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

  @Id
  @GeneratedValue
  private Integer roleid;
  private String name = "";
  private String description = "";
  @XmlJavaTypeAdapter(TrackableLinkedHashSetXmlAdapter.class)
  @Transient
  private TrackableLinkedHashSet<RightBO> rights = new TrackableLinkedHashSet<RightBO>();
  private Integer company;
  @Transient
  private CompanyBO companyBO;

  @Override
  public void lock() {
    super.lock();
    rights.lock();
  }

  @Override
  public boolean hasAnythingChanged() {
    return super.hasAnythingChanged() || rights.hasAnythingChanged();
  }

  public Set<RightBO> getRights() {
    return rights;
  }

  public boolean hasRight(RightBO right) {
    return rights.contains(right);
  }

  public boolean addRight(RightBO right) {
    return this.rights.add(right);
  }

  public boolean removeRight(RightBO right) {
    return this.rights.remove(right);
  }

  public void addRights(Set<RightBO> rights) {
    this.rights.addAll(rights);
  }

  public void removeRights(Set<RightBO> rights) {
    this.rights.removeAll(rights);
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setRightList(List<RightBO> rights) {
    if (rights == null)
      return;

    for (RightBO rightBO: rights) {
      addRight(rightBO);
    }
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private Set<RightBO> getRightList() {
    return new HashSet<RightBO>() {
      private static final long serialVersionUID = -6398741039891324376L;

      public boolean add(RightBO e) {
        return addRight(e);
      }
    };
  }

  @Override
  public Integer getId() {
    return getRoleid();
  }

  @Override
  public void setId(Integer value) {
    setRoleid(value);
  }


  public Integer getRoleid() {
    return roleid;
  }

  //used by ibatis
  public void setRoleid(Integer roleid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.roleid = roleid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    markChanged(this.name, name);
    this.name = name == null ? "" : name.trim();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description);
    this.description = description == null ? "" : description.trim();
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    markChanged(this.company, companyBO == null ? null : companyBO.getCompanyid());
    if (companyBO != null)
      company = companyBO.getCompanyid();
    else
      company = null;
    this.companyBO = companyBO;
  }


  public Integer getCompany() {
    return company;
  }

  protected void setCompany(Integer company) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    this.company = company;
  }

  @Override
  public int hashCode() {
    return getRoleid() == null ? 0 : getRoleid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RoleBO)) {
      return false;
    }
    RoleBO other = (RoleBO) obj;
    return getRoleid() != null && other.getRoleid() != null && getRoleid().equals(other.getRoleid());
  }

  public String toString() {
    String tab = "\n    ";
    return "RoleBO ("
            + tab + "roleid = " + roleid
            + tab + "name = " + name
            + tab + "description = " + description
            + tab + "company = " + company
            + tab + "rights = " + Joiner.on(tab).join(rights).replace("\n", tab)
            + " )";
  }

  @Override
  public BaseBean newInstance() {
    return new RoleBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.ROLE_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }

}
