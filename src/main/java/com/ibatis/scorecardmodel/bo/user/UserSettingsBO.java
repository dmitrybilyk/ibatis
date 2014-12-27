package com.ibatis.scorecardmodel.bo.user;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "userSetting")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSettingsBO extends BaseBean {
  private static final long serialVersionUID = 9079951108925052977L;

  public enum Fields {
    KEY("key"), USER("userid"), VALUE("value");
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

  private Integer usersettingid;
  private KEY key;
  private String value;
  private Integer userid;
  private String comment;
  private UserBO userBO;

  public enum KEY {
    SHOW_TOOLTIP, SEARCH_CRITERIA_WINDOW_EXPANDED, EVALUATION_FEEDBACK_EXPANDED, GRADING_FEEDBACK_EXPANDED,
    DASHBOARD_CONFIG, DASHBOARD_ENABLED, RECORDS_ON_PAGE, REPORTS_ALL_ANSWERS, REPORTS_ECO_PRINTING, RECENT_USERS,
    PLAYER_VOLUME, RECENT_INTERACTIONS_QUESTIONNAIRE
  }

  public UserSettingsBO() {
    value = null;
    comment = "";
  }

  public UserBO getUserBO() {
    return userBO;
  }

  public void setUserBO(UserBO userBO) {
    markChanged(this.userid, userBO == null ? null : userBO.getUserid());
    if (userBO != null) {
      setUserid(userBO.getUserid());
    } else {
      setUserid(null);
    }
    this.userBO = userBO;
  }

  /**
   *
   */

  @Override
  public Integer getId() {
    return getUsersettingid();
  }

  @Override
  public void setId(Integer value) {
    setUsersettingid(value);
  }

  //used by ibatis
  public Integer getUsersettingid() {
    return usersettingid;
  }

  //used by ibatis
  public void setUsersettingid(Integer usersettingid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.usersettingid = usersettingid;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getKey() {
    return key.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setKey(String key) {
    if (isLocked())
      throw new UnsupportedOperationException("Use method setKey(enum KEY) instead.");
    this.key = key == null ? null : KEY.valueOf(key);
  }

  public KEY getKEYEnum() {
    return key;
  }

  public void setKEYEnum(KEY key) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a primary key");
    this.key = key;
  }

  public Integer getUserid() {
    return userid;
  }

  protected void setUserid(Integer user) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a primary key");
    this.userid = user;
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

  @Override
  public int hashCode() {
    return getUsersettingid() == null ? 0 : getUsersettingid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof UserSettingsBO)) {
      return false;
    }
    UserSettingsBO other = (UserSettingsBO) obj;
    return getUsersettingid() != null && other.getUsersettingid() != null && getUsersettingid().equals(other.getUsersettingid());
  }

  public String toString() {
    return "UserSettingsBO(" + usersettingid + ": user " + userid + ": " + key + ": '" + value + "': '" + comment + "')";
  }

  @Override
  public BaseBean newInstance() {
    return new UserSettingsBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.KEY, SearchOrder.Direction.ASC));
    return ordering;
  }

}