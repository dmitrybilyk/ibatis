package com.ibatis.scorecardmodel.bo.evaluation;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchOrder;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: mensik
 * Date: 13.10.2009
 * Time: 11:04:35
 */
public class TemplateBO extends BaseBean {
  private static final long serialVersionUID = 8895530119632773340L;


  public enum Fields {
    TEMPLATE_ID("templateid"), TEMPLATE_NAME("templateName"), TEMPLATE_TYPE("templateType"),
    OWNER_BO("ownerBO"), EVALUATIONID("evaluationid"), DESCRIPTION("description"), EVALUATION_BO("evaluationBO"),
    OWNER_ID("ownerid"), GROUP_ALL_GROUPS("groupLevelAllGroupsSelected"), GROUP_ALL_EVALUATORS("groupLevelAllEvaluatorsSelected"),
    USER_ALL_GROUPS("userLevelAllGroupsSelected"), USER_ALL_EVALUATORS("userLevelAllEvaluatorsSelected");
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
    SHARED, PRIVATE
  }


  public TemplateBO() {
    this.templateType = Type.PRIVATE;
    this.templateForGroup = new TrackableLinkedHashSet<TemplateForRelationBO>();
    this.templateForUser = new TrackableLinkedHashSet<TemplateForRelationBO>();
  }

  private Integer templateid;
  private String templateName;
  private Type templateType;
  private Integer ownerid;
  private UserBO ownerBO;
  private Integer evaluationid;
  private EvaluationBO evaluationBO;
  private String description;
  private TrackableLinkedHashSet<TemplateForRelationBO> templateForGroup;
  private TrackableLinkedHashSet<TemplateForRelationBO> templateForUser;
  private Boolean groupLevelAllGroupsSelected;
  private Boolean groupLevelAllEvaluatorsSelected;
  private Boolean userLevelAllGroupsSelected;
  private Boolean userLevelAllEvaluatorsSelected;

  public void lock() {
    super.lock();
    templateForGroup.lock();
    templateForUser.lock();
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    changed |= templateForGroup.hasAnythingChanged();
    changed |= templateForUser.hasAnythingChanged();
    changed |= (evaluationBO != null && evaluationBO.hasAnythingChanged());
    changed |= (ownerBO != null && ownerBO.hasAnythingChanged());
    return changed;
  }

  @Override
  public Integer getId() {
    return templateid;
  }

  @Override
  public void setId(Integer value) {
    setTemplateid(value);
  }

  public Integer getTemplateid() {
    return templateid;
  }

  //used by ibatis
  public void setTemplateid(Integer templateid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    markChanged(this.templateid, templateid);
    this.templateid = templateid;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    markChanged(this.templateName, templateName);
    this.templateName = templateName;
  }

  public Type getTemplateType() {
    return templateType;
  }

  public void setTemplateType(Type templateType) {
    markChanged(this.templateType, templateType);
    this.templateType = templateType;
  }

  public Integer getOwnerid() {
    return ownerid;
  }

  public void setOwnerid(Integer ownerId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.ownerid = ownerId;
  }

  public Integer getEvaluationid() {
    return evaluationid;
  }

  @SuppressWarnings("unused")
  private void setEvaluationid(Integer evaluationId) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.evaluationid = evaluationId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description);
    this.description = description;
   }

  public Boolean getGroupLevelAllGroupsSelected() {
    return groupLevelAllGroupsSelected;
  }

  public void setGroupLevelAllGroupsSelected(Boolean groupLevelAllGroupsSelected) {
    this.groupLevelAllGroupsSelected = groupLevelAllGroupsSelected;
  }

  public Boolean getGroupLevelAllEvaluatorsSelected() {
    return groupLevelAllEvaluatorsSelected;
  }

  public void setGroupLevelAllEvaluatorsSelected(Boolean groupLevelAllEvaluatorsSelected) {
    this.groupLevelAllEvaluatorsSelected = groupLevelAllEvaluatorsSelected;
  }

  public Boolean getUserLevelAllGroupsSelected() {
    return userLevelAllGroupsSelected;
  }

  public void setUserLevelAllGroupsSelected(Boolean userLevelAllGroupsSelected) {
    this.userLevelAllGroupsSelected = userLevelAllGroupsSelected;
  }

  public Boolean getUserLevelAllEvaluatorsSelected() {
    return userLevelAllEvaluatorsSelected;
  }

  public void setUserLevelAllEvaluatorsSelected(Boolean userLevelAllEvaluatorsSelected) {
    this.userLevelAllEvaluatorsSelected = userLevelAllEvaluatorsSelected;
  }

  public EvaluationBO getEvaluationBO() {
    return evaluationBO;
  }

  public void setEvaluationBO(EvaluationBO evaluationBO) {
    this.evaluationBO = evaluationBO;
    if (evaluationBO != null) {
      markChanged(evaluationid, evaluationBO.getEvaluationid());
      evaluationid = evaluationBO.getEvaluationid();
    } else {
      markChanged(evaluationid, null);
      evaluationid = null;
    }
  }

  public UserBO getOwnerBO() {
    return ownerBO;
  }

  public void setOwnerBO(UserBO ownerBO) {
    this.ownerBO = ownerBO;
    if (ownerBO != null) {
      markChanged(ownerid, ownerBO.getUserid());
      ownerid = ownerBO.getUserid();
    } else {
      markChanged(ownerid, null);
      ownerid = null;
    }
  }

  public Set<Integer> getGroupEvaluators(){
    HashSet<Integer> evaluators = new HashSet<Integer>();
    for (TemplateForRelationBO relation : templateForGroup) {
      evaluators.add(relation.getEvaluatorid());
    }
    return evaluators;
  } 

  public Set<Integer> getUserEvaluators(){
    HashSet<Integer> evaluators = new HashSet<Integer>();
    for (TemplateForRelationBO relation : templateForUser) {
      evaluators.add(relation.getEvaluatorid());
    }
    return evaluators;
  }

  public Set<Integer> getEvaluatedGroups(Integer evaluatorId){
   HashSet<Integer> groups = new HashSet<Integer>();
    for (TemplateForRelationBO relation : templateForGroup) {
      if(relation.getEvaluatorid() == evaluatorId){
        groups.add(relation.getRelationid());
      }
    }
    return groups;
  }

  public Set<Integer> getEvaluatedUsers(Integer evaluatorId){
   HashSet<Integer> groups = new HashSet<Integer>();
    for (TemplateForRelationBO relation : templateForUser) {
      if(relation.getEvaluatorid() == evaluatorId){
        groups.add(relation.getRelationid());
      }
    }
    return groups;
  }

  public Set<TemplateForRelationBO> getGroupRelations(){
    return templateForGroup;
  }

  public Set<TemplateForRelationBO> getUserRelations(){
    return templateForUser;
  }

  public void addGroupRelation(Integer evaluatorId, Integer groupId){
    templateForGroup.add(new TemplateForRelationBO(evaluatorId, groupId));
  }

  public void addGroupRelations(Collection<TemplateForRelationBO> relations){
    templateForGroup.addAll(relations);
  }  
  public void addUserRelations(Collection<TemplateForRelationBO> relations){
    templateForUser.addAll(relations);
  }

  public void addUserRelation(Integer evaluatorId, Integer userId){
    templateForUser.add(new TemplateForRelationBO(evaluatorId, userId));
  }

  public void removeGroupRelation(TemplateForRelationBO relation){
    templateForGroup.remove(relation);
  }

  public void removeUserRelation(TemplateForRelationBO relation){
    templateForUser.remove(relation);
  }

  @Override
  public int hashCode() {
    return ((getTemplateid() == null) ? 0 : getTemplateid().hashCode());
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof TemplateBO))
      return false;
    TemplateBO other = (TemplateBO) obj;
    if (getTemplateid() == null || other.getTemplateid() == null){
      return false;
    }
    return getTemplateid().equals(other.getTemplateid());
  }

  @Override
  public BaseBean newInstance() {
    return new TemplateBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.TEMPLATE_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }

}