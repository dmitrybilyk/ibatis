package com.ibatis.scorecardmodel.bo.user;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** @author Martin Zdarsky, 26.5.2009 */
@XmlRootElement(name = "right")
@XmlAccessorType(XmlAccessType.FIELD)
public class RightBO extends BaseBean {
  private static final long serialVersionUID = -3973653761673527020L;
  public enum Fields {
    RIGHT_NAME("name"), RIGHT_ID("rightid");
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

  /**
   * Rights that should match the database values.
   * For detailed descriptions of individual permissions see
   * <a href="http://confluence:8080/display/ENG/Module+-+Permissions">Module - Permissions</a>.
   */
  public enum Rights {
    /** Add specific call to the evaluation */
    ADD_CALL,
    /** Create evaluation */
    CREATE_EVALS,
    /** Edit admin settings */
    EDIT_ADMIN_SETTING,
    /** Edit global templates */
    EDIT_GLOBAL_TEMPLATES,
    /** Edit interaction types */
    EDIT_INTERACTION_TYPES,
    /** Edit - upload license */
    EDIT_LICENSE,
    /** Edit Settings */
    EDIT_SETTINGS,
    /** Evaluate agents from group assigned to the current user */
    EVAL_AGENTS,
    /** Delete evaluations created by ANY user */
    EVAL_REMOVE_ALL,
    /** Only his own interactions */
    INTERACTIONS_AGENT_VIEW,
    /** Export mp3 and mp4 files from interactions screen */
    INTERACTIONS_EXPORT_MEDIA,
    /** User can view and play all interactions, interactions scores, and all tags */
    INTERACTIONS_FULL_VIEW,
    /** Only the interactions from users that belong to his can evaluate groups */
    INTERACTIONS_GROUP_VIEW,
    /** Manage evaluations assigned to *or created by* logged user */
    MANAGE_ASSIGNED_EVALUATIONS,
    /** Create and modify questionnaires */
    MANAGE_QUESTIONNAIRES,
    /** Change roles, add/remove permissions */
    MANAGE_ROLES,
    /** Edit, delete, and create speech tags and speech phrases */
    MANAGE_SPEECH_TAG,
    /** Add/Edit users, groups and manage user hierarchy */
    MANAGE_USERS,
    /** Plan evaluations for all groups */
    PLAN_ALL_EVALS,
    /** Plan evaluations for own group or subgroups */
    PLAN_GROUP_EVALS,
    /** Reopen finished evaluations (4.7.2) - added to Supervisor role... */
    REOPEN_EVALS,
    /** Replace calls */
    REPLACE_CALLS,
    /** Password reset */
    RESET_ALL_PASSWORD,
    /** Password reset - team members only */
    RESET_TEAM_PASSWORD,
    /** Plan and evaluate self-evaluations */
    SELF_EVALUATE,
    /** Send evaluation feedback */
    SEND_EVAL_FEEDBACK,
    /** View all evaluations */
    VIEW_ALL_EVALS,
    /** View reports also for all other groups */
    VIEW_ALL_REPORTS,
    /** List Audit events */
    VIEW_AUDIT,
    /** View evaluations - agent view */
    VIEW_EVALS,
    /** View my evaluations where I am evaluator */
    VIEW_MY_EVALS,
    /** View reports/graphs */
    VIEW_REPORTS,
    /* View the reports/graphs from data warehouse (e.g. logi reports) */
    VIEW_DATA_WAREHOUSE_REPORTS,
    /** View team evaluations */
    VIEW_TEAM_EVALS
  }

  private Integer rightid;
  private Rights name;
  private List<RightValueBO> values= new ArrayList<RightValueBO>();

  public RightBO() {
    values = new ArrayList<RightValueBO>();
  }

  public List<RightValueBO> getValues() {
    return values;
  }


  protected void setValues(List<RightValueBO> values) {
    this.values = values;
  }


  public void addValue(RightValueBO value) {
    if (value == null)
      throw new IllegalArgumentException();
    values.add(value);
  }

  public void removeValue(RightValueBO value) {
    if (value == null)
      throw new IllegalArgumentException();
    values.remove(value);
  }

  public void addValues(Set<RightValueBO> values) {
    if (values == null)
      throw new IllegalArgumentException();
    this.values.addAll(values);
  }

  public void removeValues(Set<RightValueBO> values) {
    if (values == null)
      throw new IllegalArgumentException();
    this.values.removeAll(values);
  }

  @Override
  public Integer getId() {
    return getRightid();
  }

  @Override
  public void setId(Integer value) {
    setRightid(value);
  }

  public Integer getRightid() {
    return rightid;
  }

  //used by ibatis
  public void setRightid(Integer rightid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.rightid = rightid;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getName() {
    return name.toString();
  }

  @SuppressWarnings("unused")
  private void setName(String name) {
    this.name = name == null ? null : Rights.valueOf(name.trim());
  }

  public Rights getNameEnum() {
    return name;
  }

  public void setNameEnum(Rights right) {
    markChanged(this.name, right);
    this.name = right;
  }

  @Override
  public int hashCode() {
    return getRightid() == null ? 0 : getRightid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RightBO)) {
      return false;
    }
    RightBO other = (RightBO) obj;
    return getRightid() != null && other.getRightid() != null && getRightid().equals(other.getRightid());
  }

  @Override
  public String toString() {
    return "RightBO(" + rightid + ": " + name + ": " + values + ")";
  }

  @Override
  public BaseBean newInstance() {
    return new RightBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.RIGHT_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }

}