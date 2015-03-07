package com.ibatis.scorecardmodel.bo;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchBO;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Utils {
  public static void assertNotNullOrEmpty(Object checkedObject) {
    if (checkedObject == null || (checkedObject instanceof String && ((String) checkedObject).length() < 1)) {
      String message = new StringBuilder("Checked object: ").append(checkedObject).append(" is null or empty").toString();
//      throw new ConfigurationException(message);
    }
  }


  public static void initDbPool(Configuration configuration) {
//    Database database = configuration.getDatabase();
//    assertNotNullOrEmpty(database);
//    PoolManager poolManager = PoolManager.getInstance();
//    poolManager.initFromDatabaseConfig(database);
  }

  public static <T extends BaseBean> List<T> sortBeans(Collection<T> data, SearchBO definition) {
    return null;
  }

  public static <T extends BaseBean> void sortBeanSet(Set<T> data, SearchBO definition) {

  }
}