package com.ibatis.scorecardmodel.bo.user;

import com.google.common.base.Joiner;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ChartInfo;
import com.ibatis.scorecardmodel.bo.ReplaceableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.*;

/** @author Martin Zdarsky, 26.5.2009 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="sc_users")
public class UserBO extends BaseBean implements ChartInfo, Serializable {
  private static final long serialVersionUID = 4211115374860863956L;

  // for serialization
  public UserBO(){}

  public UserBO(UserBO userBO) {
    this.identificatorUsed = userBO.getIdentificatorUsedEnum();
    this.login = userBO.getLogin();
    this.name = userBO.getName();
    this.surname = userBO.getSurname();
    this.email = userBO.getEmail();
    this.phone = userBO.getPhone();
    this.agentid = userBO.getAgentid();
    this.languageBO = userBO.getLanguageBO();
    this.delegatorBO = userBO.getDelegatorBO();
    this.delegationFrom = userBO.getDelegationFrom();
    this.delegationTo = userBO.getDelegationTo();
    this.addRoles(userBO.getRoles());
    this.addBelongsToCcGroups(userBO.getBelongsToCcGroups());
  }

  public enum Fields {
    USER_NAME("name"), SURNAME("surname"), LOGIN("login"), DATABASE("database"), SYNC("sync"), STATUS("status"),
        PHONE("phone"), AGENTID("agentid"), ID_USED("identificatorUsed"), DELEGATOR("delegator"),
        DELEG_FROM("delegationFrom"), DELEG_TO("delegationTo"), SEND_FEEDBACK("sendFeedback"), EMAIL("email"),
        LANGUAGE("language"), COMPANY_USER("company"), EXTERNAL_ID("externalId"), USER_ID("userid");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values()) {
        if (field.getFieldName().equals(fieldName)) {
            return field;
        }
    }
      return null;
    }
  }

  public enum IdentificatorUsed {
    EXTERNAL_AGENT_ID, PHONE, NONE
  }

  public enum Status {
    ACTIVE, INACTIVE, DELETED, BLOCKED
  }
  @Id
  @GeneratedValue
  private Integer userid;
  private String name = "";
  private String surname = "";
  private String login = "";
  //not in user table
  @Transient
  private String password;
  private Integer database;
  @Transient
  private DatabaseBO databaseBO;
  private Boolean sync;
  private Status status = Status.ACTIVE;
  private String phone = "";
  private String agentid = "";
  @Column(name="identificator_used")
  private IdentificatorUsed identificatorUsed;
  private Integer delegator;
  @Transient
  private UserBO delegatorBO;
  @Column(name="delegation_from")
  private Date delegationFrom;
  @Column(name="delegation_to")
  private Date delegationTo;
  @Column(name="send_feedback")
  private Integer sendFeedback;
  @Transient
  private UserBO sendFeedbackBO;

  private String email = "";
  private Integer language;
  @Transient
  private LanguageBO languageBO;
  private Integer company;
  @Transient
  private CompanyBO companyBO;
  @Column(name="external_id")
  private String externalId = "";
  @Column(name="deactivation_date")
  private Date deactivationDate;
  @Column(name="deleted_ts")
  private Date deletedDate;
  @Column(name="locked_ts")
  private Date lockedDate;
  @ManyToMany (cascade = CascadeType.ALL)
  @JoinTable(name ="user_belongsto_ccgroup", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "ccgroupid") })
  private Set<CcGroupBO> ccGroupsBelongsTo = new TrackableLinkedHashSet<CcGroupBO>();
  @ManyToMany (cascade = CascadeType.ALL)
  @JoinTable(name ="user_canevaluate_ccgroup", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "ccgroupid") })
  private Set<CcGroupBO> ccGroupsCanEvaluate = new TrackableLinkedHashSet<CcGroupBO>();
  @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name ="user_role", joinColumns = { @JoinColumn(name = "userid") }, inverseJoinColumns = { @JoinColumn(name = "roleid") })
  private Set<RoleBO> roles = new TrackableLinkedHashSet<RoleBO>();
  @XmlJavaTypeAdapter(TrackableLinkedHashSetXmlAdapter.class)
  @Transient
  private TrackableLinkedHashSet<RightBO> additionalRights = new TrackableLinkedHashSet<RightBO>();
  @Transient
  private Set<RightBO> allRights = new ReplaceableLinkedHashSet<RightBO>();
  @XmlJavaTypeAdapter(TrackableLinkedHashSetXmlAdapter.class)
  @Transient
  private TrackableLinkedHashSet<UserSettingsBO> settings = new TrackableLinkedHashSet<UserSettingsBO>();
  @Transient
  private UserBO originalDelegatorBO;
  @Transient
  private Date originalDelegationFrom;
  @Transient
  private Date originalDelegationTo;
  @Transient
  private boolean statusChanged = false;

  @Override
  public void lock() {
    super.lock();
    settings.lock();
    if(ccGroupsBelongsTo instanceof TrackableLinkedHashSet){
      ((TrackableLinkedHashSet<CcGroupBO>)ccGroupsBelongsTo).lock();
    }
    if(ccGroupsCanEvaluate instanceof TrackableLinkedHashSet){
    ((TrackableLinkedHashSet<CcGroupBO>)ccGroupsCanEvaluate).lock();
    }
    if(roles instanceof TrackableLinkedHashSet){
    ((TrackableLinkedHashSet)roles).lock();
    }
    additionalRights.lock();

    originalDelegationFrom = delegationFrom;
    originalDelegationTo = delegationTo;
    originalDelegatorBO = delegatorBO;
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean result = super.hasAnythingChanged();
    result |= ((TrackableLinkedHashSet<CcGroupBO>)ccGroupsBelongsTo).hasAnythingChanged();
    result |= ((TrackableLinkedHashSet<CcGroupBO>)ccGroupsCanEvaluate).hasAnythingChanged();
    result |= ((TrackableLinkedHashSet)roles).hasAnythingChanged();
    result |= additionalRights.hasAnythingChanged();
    result |= settings.hasAnythingChanged();
    return result;
  }

  public boolean hasAttributesChanged() {
    boolean result = super.hasAnythingChanged();
    result |= settings.hasAnythingChanged();
    return result;
  }

  public void setDatabaseBO(DatabaseBO databaseBO) {
    this.databaseBO = databaseBO;
    if (databaseBO != null) {
      markChanged(database, databaseBO.getDatabaseid());
      database = databaseBO.getDatabaseid();
    } else {
      markChanged(database, null);
      database = null;
    }
  }

  public DatabaseBO getDatabaseBO() {
    return databaseBO;
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private DatabaseBO getDatabaseIbatis() {
    if(this.databaseBO==null){
      DatabaseBO databaseBO = new DatabaseBO();
      databaseBO.setId(database);
      setDatabaseBO(databaseBO);
    }
    return databaseBO;
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private void setDatabaseIbatis(DatabaseBO databaseBO) {
    setDatabaseBO(databaseBO);
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private LanguageBO getLanguageIbatis() {
    if(this.languageBO==null){
      LanguageBO languageBO= new LanguageBO();
      languageBO.setId(language);
      setLanguageBO(languageBO);
    }
    return languageBO;
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private void setLanguageIbatis(LanguageBO languageBO) {
    setLanguageBO(languageBO);
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private CompanyBO getCompanyIbatis() {
    if(this.companyBO==null){
      CompanyBO companyBO = new CompanyBO();
      companyBO.setId(company);
      setCompanyBO(companyBO);
    }
    return companyBO;
  }

  // used by iBatis
  @SuppressWarnings("unused")
  private void setCompanyIbatis(CompanyBO companyBO) {
    setCompanyBO(companyBO);
  }
  // used by iBatis

  @SuppressWarnings("unused")
  private void setBelongstoGroupNameIbatis(String groupName) {
    if (getBelongsToCcGroups().isEmpty()) {
      CcGroupBO newGroup = new CcGroupBO();
      newGroup.setCcgroupname(groupName);
      getBelongsToCcGroups().add(newGroup);
    } else {
      CcGroupBO group = getBelongsToCcGroups().iterator().next();
      group.setCcgroupname(groupName);
    }
  }

  public LanguageBO getLanguageBO() {
    return languageBO;
  }

  public void setLanguageBO(LanguageBO languageBO) {
    this.languageBO = languageBO;
    if (languageBO != null) {
      markChanged(language, languageBO.getLanguageid());
      language = languageBO.getLanguageid();
    } else {
      markChanged(language, null);
      language = null;
    }
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field");
    }
    markChanged(this.company, companyBO != null ? companyBO.getCompanyid() : null);
    company = companyBO != null ? companyBO.getCompanyid() : null;
    this.companyBO = companyBO;
  }

  public Status getStatusEnum() {
    return status;
  }

  /**
   * Locks user when status is changed from ACTIVE-&gt; BLOCKED
   * Unlocks user when status is changed from BLOCKED -&gt *
   * Changes status when BLOCKED -&gt; ACTIVE
   * Doesn't change status when BLOCKED -&gt; ANY
   * otherwise changes status without side effect
   *
   * @param status
   */
  public void setStatusEnum(Status status) {
    if (Status.BLOCKED == status) {
      if (this.status != Status.ACTIVE && this.status != Status.BLOCKED) {
        return; // will keep current status
      }
      Date lockedDate = new Date();
      markChanged(this.lockedDate, lockedDate);
      setLockedDate(lockedDate);
    }

    boolean changed = markChanged(this.status, status);
    markStatusChanged(changed);
    if (this.statusChanged && this.status == Status.BLOCKED) {
      setLockedDate(null); // unlocks
    }
    this.status = status;

  }

  public Set<RoleBO> getRoles() {
    return roles;
  }

  public void addRole(RoleBO role) {
    if (role == null) {
        throw new IllegalArgumentException();
    }
    roles.add(role);
    allRights.addAll(role.getRights());
  }

  public void addRoles(Set<RoleBO> roles) {
    if (roles == null) {
        throw new IllegalArgumentException();
    }
    this.roles.addAll(roles);
    if (!roles.isEmpty()) {
        for (RoleBO role: roles) {
            allRights.addAll(role.getRights());
        }
    }
  }

  public void removeRole(RoleBO role) {
    if (role == null) {
        throw new IllegalArgumentException();
    }
    roles.remove(role);
    recalculateRights();
  }

  private void recalculateRights() {
    allRights.clear();
    allRights.addAll(additionalRights);
    for (RoleBO role: roles) {
        allRights.addAll(role.getRights());
    }
  }

  public void removeRoles(Set<RoleBO> roles) {
    if (roles == null) {
        throw new IllegalArgumentException();
    }
    this.roles.removeAll(roles);
    recalculateRights();
  }

  public Set<UserSettingsBO> getSettings() {
    return settings;
  }

  public void addSettings(UserSettingsBO userSettingsBO) {
    userSettingsBO.setUserBO(this);
    this.settings.add(userSettingsBO);
  }

  public void removeSettings(UserSettingsBO userSettingsBO) {
    userSettingsBO.setUserBO(null);
    this.settings.remove(userSettingsBO);
  }

  public void addSettings(Set<UserSettingsBO> userSettingsBO) {
    for (UserSettingsBO settingsBO: userSettingsBO) {
      addSettings(settingsBO);
    }
  }

  public void removeSettings(Set<UserSettingsBO> userSettingsBO) {
    for (UserSettingsBO settingsBO: userSettingsBO) {
      removeSettings(settingsBO);
    }
  }

  public void clearSettings() {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot erase settings. Use removeSettings method instead.");
    }

    settings.clearAll();
  }

  public Set<RightBO> getAdditionalRights() {
    return additionalRights;
  }

  public void addAdditionalRight(RightBO additionalRight) {
    this.additionalRights.add(additionalRight);
    allRights.add(additionalRight);
  }

  public void removeAdditionalRight(RightBO additionalRight) {
    this.additionalRights.remove(additionalRight);
    recalculateRights();
  }

  public void addAdditionalRights(Set<RightBO> additionalRights) {
    this.additionalRights.addAll(additionalRights);
    allRights.addAll(additionalRights);
  }

  public void removeAdditionalRights(Set<RightBO> additionalRights) {
    this.additionalRights.removeAll(additionalRights);
    recalculateRights();
  }

  public Set<RightBO> getAllRights() {
    return allRights;
  }

  public boolean hasRight(RightBO.Rights right) {

    for(RightBO item: allRights){
      if(item.getNameEnum() == right){
        return true;
      }
    }
    return false;
  }

  public Set<CcGroupBO> getBelongsToCcGroups() {
    return ccGroupsBelongsTo;
  }

  /**There is used contains, so object must be identical. Group id is not enough!
   *
   * @param ccGroupBO filled groupBO.
   * @return true if this user is in ccGroupBO.
   */
  public boolean getBelongsToCcGroups(CcGroupBO ccGroupBO) {
    return ccGroupsBelongsTo.contains(ccGroupBO);
  }

  public boolean getBelongsToCcGroupsByExternalId(String externalGroupId) {//why get when this is boolen is...
    for (CcGroupBO group : ccGroupsBelongsTo) {
      if (group.getExternalId() != null && group.getExternalId().equals(externalGroupId)) {
        return true;
      }
    }
    return false;
  }


  /**
   * ChartInfo implementation
   * @return legend to be displayed in chart
   */
  @Override
  public String getInfo() {
    return getSurname() + " " + getName();
  }


  public void addBelongsToCcGroup(CcGroupBO ccGroupBelongsTo) {
    this.ccGroupsBelongsTo.add(ccGroupBelongsTo);
  }

  public void removeBelongsToCcGroup(CcGroupBO ccGroupBelongsTo) {
    this.ccGroupsBelongsTo.remove(ccGroupBelongsTo);
  }

  public void addBelongsToCcGroups(Set<CcGroupBO> ccGroupsBelongsTo) {
    this.ccGroupsBelongsTo.addAll(ccGroupsBelongsTo);
  }

  public void removeBelongsToCcGroups(Set<CcGroupBO> ccGroupsBelongsTo) {
    this.ccGroupsBelongsTo.removeAll(ccGroupsBelongsTo);
  }

  public Set<CcGroupBO> getCanEvaluateCcGroups() {
    return ccGroupsCanEvaluate;
  }

  public boolean canEvaluateCcGroups(CcGroupBO ccGroupBO) {
    return ccGroupsCanEvaluate.contains(ccGroupBO);
  }

  public boolean canEvaluateAnyCcGroup(@NotNull Set<CcGroupBO> ccGroups) {
    for (CcGroupBO groupBO: ccGroups) {
      if (ccGroupsCanEvaluate.contains(groupBO)) {
        return true;
      }
    }
    return false;
  }

  public void addCanEvaluateCcGroup(CcGroupBO ccGroupCanEvaluate) {
    this.ccGroupsCanEvaluate.add(ccGroupCanEvaluate);
  }

  public void removeCanEvaluateCcGroup(CcGroupBO ccGroupCanEvaluate) {
    this.ccGroupsCanEvaluate.remove(ccGroupCanEvaluate);
  }

  public void addCanEvaluateCcGroups(Set<CcGroupBO> ccGroupsCanEvaluate) {
    this.ccGroupsCanEvaluate.addAll(ccGroupsCanEvaluate);
  }

  public void removeCanEvaluateCcGroups(Set<CcGroupBO> ccGroupsCanEvaluate) {
    this.ccGroupsCanEvaluate.removeAll(ccGroupsCanEvaluate);
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private void setCanEvaluateCcGroups(List<CcGroupBO> ccGroupsCanEvaluate) {
    if (ccGroupsCanEvaluate == null) {
        return;
    }
    for (CcGroupBO ccGroupBO: ccGroupsCanEvaluate) {
      addCanEvaluateCcGroup(ccGroupBO);
    }
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private void setBelongsToCcGroups(List<CcGroupBO> belongsToCcGroups) {
     if (ccGroupsBelongsTo == null) {
        return;
    }
    for (CcGroupBO ccGroupBO: ccGroupsBelongsTo) {
      addBelongsToCcGroup(ccGroupBO);
    }
  }

  // used by ibatis
  private void setRoles(Collection<RoleBO> roleList) {
    if (roleList != null) {
      ((TrackableLinkedHashSet)this.roles).clearAll();
      this.roles.addAll(roleList);
    }
  }

  @Override
  public Integer getId() {
    return getUserid();
  }

  @Override
  public void setId(Integer value) {
    setUserid(value);
  }

  public Integer getUserid() {
    return userid;
  }

  //used by ibatis
  public void setUserid(Integer userid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.userid = userid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    markChanged(this.name, name == null ? "" : name.trim());
    this.name = name == null ? "" : name.trim();
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    markChanged(this.surname, surname == null ? "" : surname.trim());
    this.surname = surname == null ? "" : surname.trim();
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    markChanged(this.login, login == null ? "" : login.trim());
    this.login = login == null ? "" : login.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    markChanged(this.password, password == null ? null : password.trim());
    this.password = password == null ? null : password.trim();
  }

  public Integer getDatabase() {
    return database;
  }

  protected void setDatabase(Integer database) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.database = database;
  }

  public Boolean getSync() {
    return sync;
  }

  public void setSync(Boolean sync) {
    markChanged(this.sync, sync);
    this.sync = sync;
  }

  // used by ibatis
  protected String getStatus() {
    return status.toString();
  }

  /**
   * Used by iBatis, doesn't modify LockedDate
   * @param status - String representation of Status Enum
   */
  protected void setStatus(String status) {
    this.status = Status.valueOf(status);
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    markChanged(this.phone, phone == null ? "" : phone.trim());
    this.phone = phone == null ? "" : phone.trim();
  }

  public String getAgentid() {
    return agentid;
  }

  public void setAgentid(String agentid) {
    markChanged(this.agentid, agentid == null ? "" : agentid.trim());
    this.agentid = agentid == null ? "" : agentid.trim();
  }

  //used by ibatis
  protected String getIdentificatorUsed() {
    return identificatorUsed == null ? "" : identificatorUsed.toString();
  }

  public void setIdentificatorUsedEnum(IdentificatorUsed identificatorUsed) {
    markChanged(this.identificatorUsed, identificatorUsed);
    this.identificatorUsed = identificatorUsed;
  }

  public IdentificatorUsed getIdentificatorUsedEnum() {
    return identificatorUsed;
  }

  protected void setIdentificatorUsed(String identificatorUsed) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field. Use setIdentificatorUsed(IdentificatorUsed identificatorUsed) method instead.");
    }
    setIdentificatorUsedEnum(IdentificatorUsed.valueOf(identificatorUsed));
  }

  public Integer getDelegator() {
    return delegator;
  }

  protected void setDelegator(Integer delegator) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field. Use set BO method instead.");
    }
    markChanged(this.delegator, delegator);
    this.delegator = delegator;
  }

  public void setDelegatorBO(UserBO delegatorBO) {
    this.delegatorBO = delegatorBO;
    if (delegatorBO != null) {
      markChanged(delegator, delegatorBO.getUserid());
      delegator = delegatorBO.getUserid();
    } else {
      markChanged(delegator, null);
      delegator = null;
    }
  }

  public UserBO getDelegatorBO() {
    return delegatorBO;
  }

  public Date getDelegationFrom() {
    return delegationFrom;
  }

  public void setDelegationFrom(Date delegationFrom) {
    markChanged(this.delegationFrom, delegationFrom);
    this.delegationFrom = delegationFrom;
  }

  public Date getDelegationTo() {
    return delegationTo;
  }

  public void setDelegationTo(Date delegationTo) {
    markChanged(this.delegationTo, delegationTo);
    this.delegationTo = delegationTo;
  }

  public Integer getSendFeedback() {
    return sendFeedback;
  }

  protected void setSendFeedback(Integer sendFeedback) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field. Use set BO method instead.");
    }
    markChanged(this.sendFeedback, sendFeedback);
    this.sendFeedback = sendFeedback;
  }

  public void setSendFeedbackBO(UserBO sendFeedbackBO) {
    this.sendFeedbackBO = sendFeedbackBO;
    if (sendFeedbackBO != null) {
      markChanged(sendFeedback, sendFeedbackBO.getUserid());
      sendFeedback = sendFeedbackBO.getUserid();
    } else {
      markChanged(sendFeedback, null);
      sendFeedback = null;
    }
  }

  public UserBO getSendFeedbackBO() {
    return sendFeedbackBO;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    markChanged(this.email, email == null ? "" : email.trim());
    this.email = email == null ? "" : email.trim();
  }

  public Integer getLanguage() {
    return language;
  }

  protected void setLanguage(Integer language) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field. Use set BO method instead.");
    }
    this.language = language;
  }

  public Integer getCompany() {
    return company;
  }

  protected void setCompany(Integer company) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field. Use set BO method instead.");
    }
    this.company = company;
  }

  public void setExternalId(String externalId) {
    markChanged(this.externalId, externalId);
    this.externalId = externalId;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setDeactivationDate(Date deactivationDate) {
    markChanged(this.deactivationDate, deactivationDate);
    this.deactivationDate = deactivationDate;
  }

  public Date getDeactivationDate() {
    return deactivationDate;
  }

  /**
   * @return copy of locked Date
   */
  public Date getLockedDate() {
      if( lockedDate == null ) {
        return null;
    }
      return new Date( lockedDate.getTime() );
  }

  /**
   * Sets locked date - used in conjunction with StatusEnum
   * @param date
   */
  protected void setLockedDate( Date date ) {
      if( lockedDate == null || date == null || lockedDate.before( date ) ) {
          markChanged( this.lockedDate, date );
          lockedDate = date;
      }
  }

  @Override
  public int hashCode() {
    return getUserid() == null ? 0 : getUserid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (!(obj instanceof UserBO)) {
        return false;
    }
    UserBO other = (UserBO) obj;
    return getUserid() != null && getUserid().equals(other.getUserid());
  }

  @Override
  public String toString() {
    final String tab = "\n    ";
    Joiner collectionJoiner = Joiner.on(tab);
    return "\nUserBO ("
            + tab + "userid = " + userid
            + tab + "name = " + name
            + tab + "surname = " + surname
            + tab + "login = " + login
            + tab + "database = " + database
            + tab + "sync = " + sync
            + tab + "status = " + status
            + tab + "phone = " + phone
            + tab + "agentid = " + agentid
            + tab + "identificatorUsed = " + identificatorUsed
            + tab + "delegator = " + delegator
            + tab + "delegationFrom = " + delegationFrom
            + tab + "delegationTo = " + delegationTo
            + tab + "sendFeedback = " + sendFeedback
            + tab + "email = " + email
            + tab + "language = " + language
            + tab + "company = " + company
            + tab + "languageBO = " + String.valueOf(languageBO).replace("\n", tab)
            + tab + "companyBO = " + String.valueOf(companyBO).replace("\n", tab)
            + tab + "databaseBO = " + String.valueOf(databaseBO).replace("\n", tab)
            + tab + "ccGroupsBelongsTo = " + collectionJoiner.join(ccGroupsBelongsTo).replace("\n", tab)
            + tab + "ccGroupsCanEvaluate = " + collectionJoiner.join(ccGroupsCanEvaluate).replace("\n", tab)
            + tab + "roles = " + collectionJoiner.join(roles).replace("\n", tab)
            + tab + "additionalRights = " + collectionJoiner.join(additionalRights).replace("\n", tab)
            + tab + "settings = " + collectionJoiner.join(settings).replace("\n", tab)
            + tab + "allRights = " + collectionJoiner.join(allRights).replace("\n", tab)
            + " )";
  }

  public boolean isActive(){
    return status.compareTo(Status.ACTIVE)==0;
  }

  public boolean isDeleted(){
    return status.compareTo(Status.DELETED)==0;
  }

  public void setDeleted(){
    setStatusEnum(Status.DELETED);
  }

  public void setActive(){
    setStatusEnum(Status.ACTIVE);
  }

  public void setInactive(){
    setStatusEnum(Status.INACTIVE);
  }

  public void setBlocked(){
    setStatusEnum(Status.BLOCKED);
  }

  @Override
  public BaseBean newInstance() {
    return new UserBO();
  }

  public boolean hasDelegationChanged() {
    if (originalDelegationFrom != null) {
      if (!originalDelegationFrom.equals(delegationFrom)) {
        return true;
      }
    } else {
      if (delegationFrom != null) {
        return true;
      }
    }

    if (originalDelegationTo != null) {
      if (!originalDelegationTo.equals(delegationTo)) {
        return true;
      }
    } else {
      if (delegationTo != null) {
        return true;
      }
    }

    if (originalDelegatorBO != null) {
      if (!originalDelegatorBO.equals(delegatorBO)) {
        return true;
      }
    } else {
      if (delegatorBO != null) {
        return true;
      }
    }

    return false;
  }

  public UserBO getOriginalDelegatorBO() {
    return originalDelegatorBO;
  }

  public Date getOriginalDelegationFrom() {
    return originalDelegationFrom;
  }

  public Date getOriginalDelegationTo() {
    return originalDelegationTo;
  }


  public Date getDeletedDate() {
    return deletedDate;
  }

  public void setDeletedDate(Date deletedDate) {
    markChanged(this.deletedDate, deletedDate);
    this.deletedDate = deletedDate;
  }

  protected boolean markStatusChanged(boolean value) {
    boolean setNow = !statusChanged && value;
    statusChanged |= value;
    return setNow;
  }

  public boolean isStatusChanged(){
    return statusChanged;
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.SURNAME, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.USER_NAME, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.LOGIN, SearchOrder.Direction.ASC));
    return ordering;
  }

}