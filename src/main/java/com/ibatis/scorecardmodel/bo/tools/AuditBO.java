//package com.ibatis.scorecardmodel.bo.tools;
//
//
//
//import com.ibatis.scorecardmodel.BaseBean;
//import com.ibatis.scorecardmodel.bo.evaluation.EvaluationBO;
//import com.ibatis.scorecardmodel.bo.user.UserBO;
//import com.ibatis.search.SearchOrder;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class AuditBO extends BaseBean {
//  private static final long serialVersionUID = -1687616649015714002L;
//
//  public static final char AUDIT_PARAMETERS_SEPARATOR = '\uFFFC';
//  public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
//
//  public enum Fields {
//    DATE_TIME("datetime"), TYPE("type"), LOGGED_USER("loggedUserBO"), EVALUATION_ID("evaluationid"), RESULT("result"), PARAMETERS("parameters");
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
//      for (Fields field : Fields.values())
//        if (field.getFieldName().equals(fieldName))
//          return field;
//      return null;
//    }
//  }
//
//  public enum Type {
//    CREATE_EVALUATION, DELETE_EVALUATION, DELETE_FINISHED_EVALUATION, START_EVALUATION, COMPLETE_EVALUATION, SAVE_EVALUATION, REOPEN_EVALUATION, ADD_QUESTIONAIRE,
//    REMOVE_QUESTIONAIRE, EDIT_QUESTIONAIRE, REPLACE_CALL, ADD_PARTICULAR_CALL, DELETE_CALL,
//    SEND_FEEDBACK, EVALUATION_REPORT, ADD_USER, EDIT_USER, REMOVE_USER, RESET_PASSWORD, CHANGE_PASSWORD,
//    REGENERATE_PASSWORD, USER_LOGIN, USER_LOGOUT, SYNCHRONIZE_NOW, EDIT_EVALUATORS_GROUP, ADD_ROLE, EDIT_ROLE, REMOVE_ROLE, COPY_ROLE,
//    ASSIGN_ROLE_TO_USER, ADD_GROUP, EDIT_GROUP, REMOVE_GROUP, EDIT_OPTIONS, USER_PROFILE_CHANGE_DELEGATE,
//    USER_PROFILE_CHANGE_SEND_FEEDBACK_CONTACT, EDIT_QUESTIONAIRE_PROPERTIES, EDIT_QUESTIONAIRE_PERMISSIONS,
//    EDIT_QUESTIONAIRE_CALL_SELECTION_RULES, ADD_INTERACTION_TYPE, EDIT_INTERACTION_TYPE, REMOVE_INTERACTION_TYPE,
//    USER_REMOVING_ACCOUNT_LOCK, USER_REMOVING_LOGIN_ATTEMPTS, USER_BLOCKED_LOGIN_ATTEMPT, USER_INCREASE_WRONG_ATTEMPTS,
//    ADD_SPEECH_TAG, REMOVE_SPEECH_TAG, EDIT_SPEECH_TAG, EXPORT_INTERACTION_CALL
//  }
//
//  public enum Result {
//    OK, UNAUTHORIZED
//  }
//
//  private Integer auditid;
//  private Date datetime;
//  private Type type;
//  private Integer loggedUser;
//  private UserBO loggedUserBO;
//  private Result result;
//  private Integer evaluation;
//  private EvaluationBO evaluationBO;
//  private String parameters;
//  private String localizedDescription;
//
//  public AuditBO() {
//    result = Result.OK;
//    parameters = "";
//  }
//
//  @Override
//  public Integer getId() {
//    return getAuditid();
//  }
//
//  @Override
//  public void setId(Integer value) {
//    setAuditid(value);
//  }
//
//  public Integer getAuditid() {
//    return auditid;
//  }
//
//  //used by ibatis
//  public void setAuditid(Integer auditid) {
//    if (isLocked()) {
//      throw new UnsupportedOperationException("You cannot set a primary key");
//    }
//    this.auditid = auditid;
//  }
//
//  public Date getDatetime() {
//    return datetime;
//  }
//
//  public void setDatetime(Date datetime) {
//    markChanged(this.datetime, datetime);
//    this.datetime = datetime;
//  }
//
//  // used by ibatis
//  @SuppressWarnings("unused")
//  private String getType() {
//    return type.toString();
//  }
//
//  // used by ibatis
//  @SuppressWarnings("unused")
//  private void setType(String type) {
//    if (isLocked())
//      throw new UnsupportedOperationException("You cannot set this field. Use setTypeEnum instead.");
//    this.type = Type.valueOf(type);
//  }
//
//  public Type getTypeEnum() {
//    return type;
//  }
//
//  public void setTypeEnum(Type type) {
//    markChanged(this.type, type);
//    this.type = type;
//  }
//
//  //used by ibatis
//  public Integer getLoggedUser() {
//    return loggedUser;
//  }
//
//  //used by ibatis
//  @SuppressWarnings("unused")
//  private void setLoggedUser(Integer loggedUser) {
//    if (isLocked())
//      throw new UnsupportedOperationException("You cannot set this field. Use setToUserBO instead.");
//    this.loggedUser = loggedUser;
//  }
//
//  public void setLoggedUserBO(UserBO loggedUserBO) {
//    this.loggedUserBO = loggedUserBO;
//    if (loggedUserBO != null) {
//      markChanged(loggedUser, loggedUserBO.getUserid());
//      loggedUser = loggedUserBO.getUserid();
//    } else {
//      markChanged(loggedUser, null);
//      loggedUser = null;
//    }
//  }
//
//  public UserBO getLoggedUserBO() {
//    return loggedUserBO;
//  }
//
//  //used by ibatis
//  public Integer getEvaluationid() {
//    return evaluation;
//  }
//
//  //used by ibatis
//  @SuppressWarnings("unused")
//  private void setEvaluationid(Integer evaluationid) {
//    if (isLocked())
//      throw new UnsupportedOperationException("You cannot set this field. Use setEvaluationBO instead.");
//    this.evaluation = evaluationid;
//  }
//
//  public void setEvaluationBO(EvaluationBO evaluationBO) {
//    this.evaluationBO = evaluationBO;
//    if (evaluationBO != null) {
//      markChanged(evaluation, evaluationBO.getEvaluationid());
//      evaluation = evaluationBO.getEvaluationid();
//    } else {
//      markChanged(evaluation, null);
//      evaluation = null;
//    }
//  }
//
//  public EvaluationBO getEvaluationBO() {
//    return evaluationBO;
//  }
//
//
//
//  public String getResult() {
//    return result.toString();
//  }
//
//  // used by ibatis
//  @SuppressWarnings("unused")
//  private void setResult(String value) {
//    if (isLocked())
//      throw new UnsupportedOperationException("You cannot set this field. Use setResultEnum instead.");
//
//    this.result = Result.valueOf(value);
//  }
//
//  public Result getResultEnum() {
//    return result;
//  }
//
//  public void setResultEnum(Result value) {
//    markChanged(this.result, value);
//    this.result = value;
//  }
//
//  public String getParameters() {
//    return parameters;
//  }
//
//  public void setParameters(String parameters) {
//    markChanged(this.parameters, parameters == null ? "" : parameters.trim());
//    this.parameters = parameters == null ? "" : parameters.trim();
//  }
//
//  @Override
//  public int hashCode() {
//    return ((getAuditid() == null) ? 0 : getAuditid().hashCode());
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (!(obj instanceof AuditBO))
//      return false;
//    AuditBO other = (AuditBO) obj;
//    if (getAuditid() == null || other.getAuditid() == null)
//        return false;
//    return getAuditid().equals(other.getAuditid());
//  }
//
//  @Override
//  public String toString() {
//    return "AuditBO{" + auditid + ":" + type + "=" + result + "@" + datetime +
//            ", param=\"" + parameters + "\"" + ", desc=\"" + localizedDescription + "\"" +
//            ", user=" + loggedUserBO + ", eval=" + evaluationBO + "}";
//  }
//
//  @Override
//  public BaseBean newInstance() {
//    return new AuditBO();
//  }
//
//  @Override
//  public List<SearchOrder> defaultSortOrder() {
//    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
//    ordering.add(new SearchOrder(Fields.DATE_TIME, SearchOrder.Direction.DESC));
//    return ordering;
//  }
//
//  public String getLocalizedDescription() {
//    return localizedDescription;
//  }
//
//  public void setLocalizedDescription(String localizedDescription) {
//    this.localizedDescription = localizedDescription;
//  }
//
//}