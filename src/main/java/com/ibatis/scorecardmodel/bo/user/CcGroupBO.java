package com.ibatis.scorecardmodel.bo.user;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/** @author Martin Zdarsky, 26.5.2009 */
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ccgroups")
public class CcGroupBO extends BaseBean {
  private static final long serialVersionUID = 3905539732817163952L;
  public enum Fields {
    CCGROUP_NAME("ccgroupname"), CCGROUP_DESC("description"), PARENT("parentid"), COMPANY_CCGROUP("company"),CCGROUP_ID("ccgroupid"),
    CHILDREN_GROUPS("childrenGroups"), EXTERNAL_ID("externalId"), LEFT("leftindex"), RIGHT("rightindex");
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
  private Integer ccgroupid;
  private String ccgroupname = "";
  private String description = "";
  private Integer parentid;
  private Integer leftindex;
  private Integer rightindex;
  private Integer company;
  @Column(name="external_id")
  private String externalId = "";
  @Transient
  private Set<CcGroupBO> childrenGroups = new HashSet<CcGroupBO>();
  @Transient
  private CompanyBO companyBO;
  @Transient
  private CcGroupBO parentBO;

  public void setCompanyBO(CompanyBO companyBO) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field.");
    markChanged(this.company, companyBO == null ? null : companyBO.getCompanyid());
    if (companyBO != null)
      company = companyBO.getCompanyid();
    else
      company = null;
    this.companyBO = companyBO;
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setParentBO(CcGroupBO parentBO) {
    markChanged(this.parentid, parentBO == null ? null : parentBO.getId());
    if(this.parentBO != null){
      this.parentBO.removeChild(this);
    }
    this.parentBO = parentBO;
    if (parentBO != null) {
      parentid = parentBO.getId();
      parentBO.addChild(this);
    }
    else {
      parentid = null;
    }
  }

  public CcGroupBO getParentBO() {
    return parentBO;
  }
  
  public Set<CcGroupBO> getChildrenGroups() {
    return childrenGroups;
  }

  public void addChild(CcGroupBO childCcGroupBO) {
    if (childCcGroupBO == null)
      throw new IllegalArgumentException();
    childrenGroups.add(childCcGroupBO);
  }

  public void addChildren(Collection<CcGroupBO> childrenCcGroupsBO) {
    if (childrenCcGroupsBO == null)
      throw new IllegalArgumentException();
    childrenGroups.addAll(childrenCcGroupsBO);
  }

  public void removeChild(CcGroupBO childGroupBO) {
    if (childGroupBO == null)
      throw new IllegalArgumentException();
    childrenGroups.remove(childGroupBO);
  }

  public void removeChildren(Set<CcGroupBO> childrenCcGroupsBO) {
    if (childrenCcGroupsBO == null)
      throw new IllegalArgumentException();
    childrenGroups.removeAll(childrenCcGroupsBO);
  }

  public boolean hasChildrenGroups() {
    return (this.childrenGroups != null && !this.childrenGroups.isEmpty());
  }

  /**
   * Traverses the group children structure from the current group to the one
   * whose id is specified on the parameter, recusively.
   * Notice: the first element of the list is the target group id, and the
   * last one will be the current group.
   * @param groupId The id of the group to search
   * @return A list of groups id from the specified one to the root group
   */
  public List<Integer> findGroupTreePath(Integer groupId) {
    if (groupId == null) {
      throw new IllegalStateException("findGroupTreePath: The group id to search cannot be null.");
    }
    if (this.ccgroupid == null) {
      throw new IllegalStateException("findGroupTreePath: This group doesn't have id!");
    }

    List<Integer> nodesStack = new ArrayList<Integer>();
    findChildGroupWithParent(this, groupId, nodesStack);

    return nodesStack;
  }

  private void findChildGroupWithParent(CcGroupBO group, Integer groupId, List<Integer> nodesStack) {
    if ((group.rightindex - group.leftindex != 1) && !group.hasChildrenGroups()) {
      // children was not loaded from database
      throw new IllegalStateException("findChildGroupWithParent: The group: " + group.ccgroupid + " should have children, but they weren't loaded.");
    }
    if ((group.leftindex != 1) && group.getParentBO() == null) {
      // it's not the root group, and it's missing the parent
      throw new IllegalStateException("findChildGroupWithParent: The group: " + group.ccgroupid + " has parent group, but it wasn't loaded.");
    }

    if (group.getCcgroupid().equals(groupId)) {
      addPathToTheStack(nodesStack, group);
    } else {
      for (CcGroupBO childrenGroup : group.getChildrenGroups()) {
        findChildGroupWithParent(childrenGroup, groupId, nodesStack);
      }
    }
  }

  /* stop when it reaches the starting point */
  private void addPathToTheStack(List<Integer> nodesStack, CcGroupBO group) {
    while (group != null) {
      nodesStack.add(group.getId());
      if (group.equals(this)) {
        break;
      } else {
        group = group.getParentBO();
      }
    }
  }

  @Override
  public Integer getId() {
    return getCcgroupid();
  }
  
  @Override
  public void setId(Integer value) {
    setCcgroupid(value);
  }

  public String getName() {
    return getCcgroupname();
  }

  public Integer getCcgroupid() {
    return ccgroupid;
  }

  //used by ibatis
  public void setCcgroupid(Integer ccgroupid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.ccgroupid = ccgroupid;
  }

  public String getCcgroupname() {
    return ccgroupname;
  }

  public void setCcgroupname(String ccgroupname) {
    markChanged(this.ccgroupname, ccgroupname == null ? "" : ccgroupname.trim());
    this.ccgroupname = ccgroupname == null ? "" : ccgroupname.trim();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description == null ? "" : description.trim());
    this.description = description == null ? "" : description.trim();
  }

  public Integer getParentid() {
    return parentid;
  }

  protected void setParentid(Integer parentid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field.");
    this.parentid = parentid;
  }
  
  public Integer getLeftindex() {
    return leftindex;
  }

  public void setLeftindex(Integer leftindex) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field.");
    this.leftindex = leftindex;
  }

  public Integer getRightindex() {
    return rightindex;
  }

  public void setRightindex(Integer rightindex) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field.");
    this.rightindex = rightindex;
  }
  
  public Integer getCompany() {
    return company;
  }

  protected void setCompany(Integer company) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field.");
    this.company = company;
  }

  public void setExternalId(String externalId) {
    externalId = externalId == null ? "" : externalId;  //do not trim, just in case
    markChanged(this.externalId, externalId);
    this.externalId = externalId;
  }

  public String getExternalId() {
    return externalId;
  }

  @Override
  public int hashCode() {
    return getCcgroupid() == null ? 0 : getCcgroupid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CcGroupBO)) {
      return false;
    }
    CcGroupBO other = (CcGroupBO) obj;
    return getCcgroupid() != null && other.getCcgroupid() != null && getCcgroupid().equals(other.getCcgroupid());
  }

  public String toString() {
    String tab = "\n    ";
    return "CcGroupBO ("
            + tab + "ccgroupid = " + ccgroupid
            + tab + "ccgroupname = " + ccgroupname
            + tab + "description = " + description
            + tab + "parentid = " + parentid
            + tab + "leftindex = " + leftindex
            + tab + "rightindex = " + rightindex
            + tab + "companyid = " + company
            + tab + "companyBO = " + companyBO
            + " )";
  }

  @Override
  public BaseBean newInstance() {
    return new CcGroupBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.CCGROUP_NAME, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.CCGROUP_DESC, SearchOrder.Direction.ASC));
    return ordering;
  }
}
