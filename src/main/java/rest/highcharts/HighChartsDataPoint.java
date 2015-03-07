package rest.highcharts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HighCharts data POJO to be automatically converted to JSON options.point format as described in
 * <a href="http://api.highcharts.com/highstock#series.data">Highstock Options Reference</a>.
 */
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HighChartsDataPoint {
  private Number x;
  private Number y;
  private String name;
  private String id;

  public Number getX() {
    return x;
  }

  public void setX(Number x) {
    this.x = x;
  }

  public Number getY() {
    return y;
  }

  public void setY(Number y) {
    this.y = y;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
