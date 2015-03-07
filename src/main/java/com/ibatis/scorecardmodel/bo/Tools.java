package com.ibatis.scorecardmodel.bo;

//import com.extjs.gxt.ui.client.data.PagingLoadConfig;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchOrder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Tools {

  public static SearchBO getSearchBO(PagingLoadConfig loadConfig, BaseBean sortingBean) {
    return getSearchBO(loadConfig, new SearchBO(), sortingBean);
  }

  public static SearchBO getSearchBO(PagingLoadConfig loadConfig, SearchBO searchBO, BaseBean... sortingBean) {
    if (searchBO == null) {
      searchBO = new SearchBO();
    }
    if (loadConfig != null && loadConfig.getLimit() > 0) {
      searchBO.setLimit(loadConfig.getLimit());
      searchBO.setOffset(loadConfig.getOffset());
    } else {
      searchBO.setOffset(0);
    }
    // look enums for sorting in the base beans
    for (BaseBean baseBean : sortingBean) {
      boolean loaded = loadSortingField(searchBO, loadConfig, baseBean);
      if (loaded) break;
    }
    // if the parameter was not found in any enum, then use the default one from the first base bean
    if (searchBO.getOrder().size() == 0) {
      searchBO.addOrderList(sortingBean[0].defaultSortOrder());
    }
    return searchBO;
  }

  public static boolean loadSortingField(SearchBO searchBO, PagingLoadConfig loadConfig, BaseBean BO) {
    if (searchBO == null) {
      return false;
    }
    Enum<?> sortingField = null;
    if (loadConfig != null) {
      sortingField = getSortingField(loadConfig, BO);
    }
    if (sortingField != null) {
      searchBO.addOrder(sortingField, SearchOrder.Direction.valueOf(loadConfig.getSortDir().name()));
      return true;
    }
    return false;
  }

  /**
   * This method has empty catch block as they may occur.
   */
  private static Enum<?> getSortingField(PagingLoadConfig loadConfig, BaseBean BO) {
    try {
      Class<?> clazz = Class.forName(BO.getClass().getCanonicalName() + "$Fields");
      Method method = clazz.getMethod("getField", String.class);
      return (Enum<?>) method.invoke(null, loadConfig.getSortField());
    } catch (SecurityException e) {
    } catch (NoSuchMethodException e) {
    } catch (IllegalArgumentException e) {
    } catch (IllegalAccessException e) {
    } catch (InvocationTargetException e) {
    } catch (ClassNotFoundException e) {
    }
    return null;
  }

  /**
   * Sort a collection of {@link cz.zoom.callrec.core.callstorage.util.BaseBean} objects according to the order defined in the SearchBO.
   * The sort is locale-sensitive.
   *
   * @param data
   * @param definition
   * @param <T>
   * @return
   */
  public static <T extends BaseBean> List<T> sortBeans(final Collection<T> data, SearchBO definition) {
    return Utils.sortBeans(data, definition);
  }

  public static <T extends BaseBean> void sortBeanSet(Set<T> data, SearchBO definition) {
    Utils.sortBeanSet(data, definition);
  }

  public static boolean compareDouble(Double subEvalTotal, double v) {
    return false;
  }
}