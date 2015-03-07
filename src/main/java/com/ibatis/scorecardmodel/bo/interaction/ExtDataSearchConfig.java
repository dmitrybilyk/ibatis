//package com.ibatis.scorecardmodel.bo.interaction;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import cz.zoom.callrec.core.callstorage.util.SearchCondition;
//import cz.zoom.scorecard.business.utils.JsonResourceView;
//
//import java.io.Serializable;
//import java.util.Collections;
//import java.util.Map;
//
///**
// * Stanislav Valenta, 9/24/13
// */
//public class ExtDataSearchConfig implements Serializable, Comparable<ExtDataSearchConfig> {
//  private static final long serialVersionUID = 5312940358901400237L;
//
//  @Override
//  public int compareTo(ExtDataSearchConfig searchConfig) {
//    return getText().compareTo(searchConfig.getText());
//  }
//
//  public void setColumnIndex(int columnIndex) {
//    this.columnIndex = columnIndex;
//  }
//
//  public enum SearchType {
//    SELECT, AUTOSELECT, INPUT
//  }
//
//  @JsonView(JsonResourceView.ExternalDataKeyTextPair.class)
//  private String text;
//  @JsonView(JsonResourceView.ExternalDataKeyTextPair.class)
//  private String key;
//  // replace Hide with any desired view. see for default without annotations:
//  // http://jersey.576304.n2.nabble.com/How-Do-I-Provide-a-Custom-ObjectMapper-td7581765.html
//  @JsonView(JsonResourceView.Hide.class)
//  private SearchType searchType;
//  @JsonView(JsonResourceView.Hide.class)
//  private SearchCondition.Comparator comparator;
//  @JsonView(JsonResourceView.Hide.class)
//  private Map<String, String> namesAndOptions = Collections.emptyMap();
//  @JsonView(JsonResourceView.Hide.class)
//  private Integer columnIndex;
//  @JsonView(JsonResourceView.Hide.class)
//  private boolean sort;
//
//  public ExtDataSearchConfig() {
//  }
//
//  public ExtDataSearchConfig(SearchCondition.Comparator comparator, String text, String key, Integer columnIndex, boolean sort, SearchType searchType) {
//    this.comparator = comparator;
//    this.text = text;
//    this.key = key;
//    this.columnIndex = columnIndex;
//    this.sort = sort;
//    this.searchType = searchType;
//  }
//
//  public void setNamesAndOptions(Map<String, String> namesAndOptions) {
//    this.namesAndOptions = namesAndOptions;
//  }
//
//  public Integer getColumnIndex() {
//    return columnIndex;
//  }
//
//  public String getKey() {
//    return key;
//  }
//
//  public SearchCondition.Comparator getComparator() {
//    return comparator;
//  }
//
//  public Map<String, String> getNamesAndOptions() {
//    return namesAndOptions;
//  }
//
//  public String getText() {
//    return text;
//  }
//
//  public SearchType getSearchType() {
//    return searchType;
//  }
//
//  public boolean isSort() {
//    return sort;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//
//    ExtDataSearchConfig config = (ExtDataSearchConfig) o;
//
//    if (columnIndex != null ? !columnIndex.equals(config.columnIndex) : config.columnIndex != null) return false;
//    if (comparator != config.comparator) return false;
//    if (key != null ? !key.equals(config.key) : config.key != null) return false;
//    if (searchType != config.searchType) return false;
//    if (text != null ? !text.equals(config.text) : config.text != null) return false;
//
//    return true;
//  }
//
//  @Override
//  public int hashCode() {
//    int result = text != null ? text.hashCode() : 0;
//    result = 31 * result + (key != null ? key.hashCode() : 0);
//    result = 31 * result + (searchType != null ? searchType.hashCode() : 0);
//    result = 31 * result + (comparator != null ? comparator.hashCode() : 0);
//    result = 31 * result + (columnIndex != null ? columnIndex.hashCode() : 0);
//    return result;
//  }
//
//  @Override
//  public String toString() {
//    return "ExtDataSearchConfig{" +
//            "columnIndex=" + columnIndex +
//            ", text='" + text + '\'' +
//            ", key='" + key + '\'' +
//            ", searchType=" + searchType +
//            ", comparator=" + comparator +
//            ", namesAndOptions=" + namesAndOptions +
//            ", sort=" + sort +
//            '}';
//  }
//}
