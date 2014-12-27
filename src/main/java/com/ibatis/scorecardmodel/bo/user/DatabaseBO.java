package com.ibatis.scorecardmodel.bo.user;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/** @author Martin Zdarsky, 26.5.2009 */
@XmlRootElement(name = "database")
@XmlAccessorType(XmlAccessType.FIELD)
public class DatabaseBO extends BaseBean {
  private static final long serialVersionUID = 6241555293631247325L;

  public enum Fields {
    DATABASE_NAME("name"), CONFIGURATION_EQUAL_GROUP("configurationEqualGroup"), DBTYPE("dbtypeEnum"), ENCRYPTION_TYPE("encryptionTypeEnum"),
    CONNECTION("connection"), CONN_LOGIN("connectionLogin"), CONN_PASSWORD("connectionPassword"), COMPANY("companyBO"), DATABASE_ID("databaseid");
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
  private Integer databaseid;
  private String name = "";
  private String configurationEqualGroup = "";
  private DATABASES dbtype;
  private ENCRYPTION_TYPES encryptionType;
  private String connection = "";
  private String connectionLogin = "";
  private String connectionPassword = "";
  private Integer company;
  private CompanyBO companyBO;

  public enum ENCRYPTION_TYPES {
    PLAIN, MD5, SHA_2, SHA_3
  }
  
  public enum DATABASES {
    LOCAL, IPCC, CALLREC, GENESYS
  }

  public void setDbtypeEnum(DATABASES type) {
    markChanged(this.dbtype, type);
    this.dbtype = type;
  }

  public DATABASES getDbtypeEnum() {
    return dbtype;
  }
  
  public void setEncryptionTypeEnum(ENCRYPTION_TYPES encryptionType) {
    markChanged(this.encryptionType, encryptionType);
    this.encryptionType = encryptionType;
  }

  public ENCRYPTION_TYPES getEncryptionTypeEnum() {
    return encryptionType;
  }

  @Override
  public Integer getId() {
    return getDatabaseid();
  }
  
  @Override
  public void setId(Integer value) {
    setDatabaseid(value);
  }

  public Integer getDatabaseid() {
    return databaseid;
  }

  //used by ibatis
  public void setDatabaseid(Integer databaseid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.databaseid = databaseid;
  }
  
  // used by ibatis
  @SuppressWarnings("unused")
  private void setEncryptionType(String encryptionType) {
    this.encryptionType = ENCRYPTION_TYPES.valueOf(encryptionType);
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private String getEncryptionType() {
    return encryptionType.toString();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    markChanged(this.name, name == null ? "" : name.trim());
    this.name = name == null ? "" : name.trim();
  }

  public String getConfigurationEqualGroup() {
    return configurationEqualGroup;
  }

  public void setConfigurationEqualGroup(String configurationEqualGroup) {
    markChanged(this.configurationEqualGroup, configurationEqualGroup == null ? "" : configurationEqualGroup.trim());
    this.configurationEqualGroup = configurationEqualGroup == null ? "" : configurationEqualGroup.trim();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getDbtype() {
    return dbtype.toString();
  }

  protected void setDbtype(String type) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot use this method. Use setDbtypeEnum(enum) instead.");
    this.dbtype = DATABASES.valueOf(type);
  }
  
  public String getConnection() {
    return connection;
  }

  public void setConnection(String connection) {
    markChanged(this.connection, connection == null ? "" : connection.trim());
    this.connection = connection == null ? "" : connection.trim();
  }

  public String getConnectionLogin() {
    return connectionLogin;
  }

  public void setConnectionLogin(String connectionLogin) {
    markChanged(this.connectionLogin, connectionLogin == null ? "" : connectionLogin.trim());
    this.connectionLogin = connectionLogin == null ? "" : connectionLogin.trim();
  }

  public String getConnectionPassword() {
    return connectionPassword;
  }

  public void setConnectionPassword(String connectionPassword) {
    markChanged(this.connectionPassword, connectionPassword == null ? "" : connectionPassword.trim());
    this.connectionPassword = connectionPassword == null ? "" : connectionPassword.trim();
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
    return getDatabaseid() == null ? 0 : getDatabaseid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof DatabaseBO)) {
      return false;
    }
    DatabaseBO other = (DatabaseBO) obj;
    return getDatabaseid() != null && other.getDatabaseid() != null && getDatabaseid().equals(other.getDatabaseid());
  }

  public String toString() {
    String tab = "\n    ";
    return "DatabaseBO ("
            + tab + "databaseid = " + databaseid
            + tab + "name = " + name
            + tab + "value = " + configurationEqualGroup
            + tab + "dbtype = " + dbtype
            + tab + "connection = " + connection
            + tab + "connectionLogin = " + connectionLogin
            + tab + "connectionPassword = " + connectionPassword
            + tab + "company = " + company
            + " )";
  }

  @Override
  public BaseBean newInstance() {
    return new DatabaseBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.DATABASE_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }
}