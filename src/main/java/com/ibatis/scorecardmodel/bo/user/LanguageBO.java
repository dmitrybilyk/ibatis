package com.ibatis.scorecardmodel.bo.user;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "language")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageBO extends BaseBean {
  private static final long serialVersionUID = -5608800563456331650L;

  public static final String DEFAULT_LOCALE = "en_US";

  public enum Fields {
    DISPLAY_NAME("displayName"), LOCALE("locale"), COUNTRY("country"), DEFAULT("mainLanguage"), DESC("description");
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
  
  private Integer languageid;
  private String displayName = "";
  private String language = "";
  private String country = "";
  private boolean mainLanguage;
  private String description = "";
  private int firstDayOfWeek;

  @Override
  public Integer getId() {
    return getLanguageid();
  }
  
  @Override
  public void setId(Integer value) {
    setLanguageid(value);
  }

  public Integer getLanguageid() {
    return languageid;
  }

  //used by ibatis
  public void setLanguageid(Integer languageid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.languageid = languageid;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    markChanged(this.displayName, displayName == null ? "" : displayName.trim());
    this.displayName = displayName == null ? "" : displayName.trim();
  }

  public void setLanguage(String language) {
    markChanged(this.language, language);
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }

  public void setCountry(String country) {
    markChanged(this.country, country);
    this.country = country;
  }

  public String getCountry() {
    return country;
  }

  public void setMainLanguage(boolean mainLanguage) {
    markChanged(this.mainLanguage, mainLanguage);
    this.mainLanguage = mainLanguage;
  }

  public boolean isMainLanguage() {
    return mainLanguage;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description);
    this.description = description == null ? "" : description.trim();
  }

  public void setFirstDayOfWeek(int firstDayOfWeek) {
    markChanged(this.firstDayOfWeek, firstDayOfWeek);
    this.firstDayOfWeek = firstDayOfWeek;
  }

  public int getFirstDayOfWeek() {
    return firstDayOfWeek;
  }

  @Override
  public int hashCode() {
    return getLanguageid() == null ? 0 : getLanguageid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof LanguageBO)) {
      return false;
    }
    LanguageBO other = (LanguageBO) obj;
    return getLanguageid() != null && other.getLanguageid() != null && getLanguageid().equals(other.getLanguageid());
  }

  public String toString() {
    return "LanguageBO (" + languageid + ": "
            + language + "_" + country + ": "
            + displayName + ": '" + description + "'" + ")";
  }

  @Override
  public BaseBean newInstance() {
    return new LanguageBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.DISPLAY_NAME, SearchOrder.Direction.ASC));
    return ordering;
  }

  public String getLanguageCode(){
    return getLanguage() + "_" + getCountry();
  }

}
