package rest.highcharts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * HighCharts series POJO to be automatically converted to JSON format as described in
 * <a href="http://api.highcharts.com/highstock#series">Highstock Options Reference</a>.
 */
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HighChartsDataSeries {
  private String id;
  private String name;
  private List<HighChartsDataPoint> data;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<HighChartsDataPoint> getData() {
    return data;
  }

  public void setData(List<HighChartsDataPoint> data) {
    this.data = data;
  }
}
