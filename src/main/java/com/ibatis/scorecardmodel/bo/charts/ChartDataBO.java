package com.ibatis.scorecardmodel.bo.charts;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean keeping data for displaying charts - represents one chart record (line or column). Keeps day, week, month quarter
 *  and year values.
 *
 * @author Stanislav Valenta, 22.10.2009
 */
public class ChartDataBO<T> extends BaseBean {
  private static final long serialVersionUID = -8289301455554678945L;
  // flag if this instance represents overall value
  private Boolean overall = Boolean.FALSE;
  // legend displayed in chart
  private ChartInfo legend;

  public ChartDataBO() {
    //empty
  }

  private List<T> dayValuesList = new ArrayList<T>();
  private List<T> weekValuesList = new ArrayList<T>();
  private List<T> monthValuesList = new ArrayList<T>();
  private List<T> yearQuoterValuesList = new ArrayList<T>();
  private List<T> yearValuesList = new ArrayList<T>();
//  private EnumMap<ChartTimeInterval, List<T>> tableOfValues = new EnumMap<ChartTimeInterval, List<T>>(ChartTimeInterval.class);
  private HashMap<ChartTimeInterval, List<T>> tableOfValues = new HashMap<ChartTimeInterval, List<T>>();


  {
    tableOfValues.put(ChartTimeInterval.DAY_VALUES, dayValuesList);
    tableOfValues.put(ChartTimeInterval.WEEK_VALUES, weekValuesList);
    tableOfValues.put(ChartTimeInterval.MONTH_VALUES, monthValuesList);
    tableOfValues.put(ChartTimeInterval.QUARTER_VALUES, yearQuoterValuesList);
    tableOfValues.put(ChartTimeInterval.YEAR_VALUES, yearValuesList);
  }


  public Map<ChartTimeInterval, List<T>> getTableOfValues() {
    return tableOfValues;
  }


  public List<T> getDayValues() {
    return tableOfValues.get(ChartTimeInterval.DAY_VALUES);
  }


  public List<T> getWeekValues() {
    return tableOfValues.get(ChartTimeInterval.WEEK_VALUES);
  }


  public List<T> getMonthValues() {
    return tableOfValues.get(ChartTimeInterval.MONTH_VALUES);
  }


  public List<T> getQuarterValues() {
    return tableOfValues.get(ChartTimeInterval.QUARTER_VALUES);
  }


  public List<T> getYearValues() {
    return tableOfValues.get(ChartTimeInterval.YEAR_VALUES);
  }

  public List<T> getValues(ChartTimeInterval timeInterval){
    return tableOfValues.get(timeInterval);
  }

  public void addDayValues(List<T> values) {
    getDayValues().addAll(values);
  }


  public void addWeekValues(List<T> values) {
    getWeekValues().addAll(values);
  }


  public void addMonthValues(List<T> values) {
    getMonthValues().addAll(values);
  }


  public void addYearQuoterValues(List<T> values) {
    getQuarterValues().addAll(values);
  }


  public void addYearValues(List<T> values) {
    getYearValues().addAll(values);
  }

  public void addValues(ChartTimeInterval timeInterval, List<T> values){
    getValues(timeInterval).addAll(values);
  }

   public void addValue(ChartTimeInterval timeInterval, T value){
    getValues(timeInterval).add(value);
  }


  public void setLegend(ChartInfo legend) {
    this.legend = legend;
  }


  public ChartInfo getLegend() {
    return legend;
  }


  public Boolean isOverall() {
    return overall;
  }


  public void setOverall(Boolean overall) {
    this.overall = overall;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    ChartDataBO that = (ChartDataBO) o;

    return !(legend != null ? !legend.equals(that.legend) : that.legend != null);

  }


  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (legend != null ? legend.hashCode() : 0);
    return result;
  }

  @Override
  public BaseBean newInstance() {
    return new ChartDataBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    return new ArrayList<SearchOrder>(0);
  }
}