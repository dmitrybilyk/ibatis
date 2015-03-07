//package com.ibatis.scorecardmodel.bo.interaction;
//
//import static org.apache.commons.lang.StringUtils.isNotBlank;
//
///**
// * Stanislav Valenta, 9/30/13
// */
//public class ExtDataSearchConfigValidator implements BOValidator<ExtDataSearchConfig> {
//  @Override
//  public boolean isValid(ExtDataSearchConfig config) {
//    return config.getColumnIndex() != null && config.getColumnIndex() > 0 && config.getColumnIndex() < 16 &&
//            isNotBlank(config.getKey()) && isNotBlank(config.getText()) && config.getComparator() != null;
//  }
//}
