package com.ibatis.scorecardmodel.bo.charts;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Karel Tejnora
 * @version 1.0
 * @created 14-Apr-2011 6:54:22 PM
 */
public class HiChartSerie implements Serializable{

  private static final long serialVersionUID = -4206717141816504373L;
  /**
     * Color of the bar
     */
    private String color;

	/**
	 * Label for this record displayed inside of the chart
	 */
	private String label;
	/**
	 * An array of evaluation ids for represented by this instance on the graph
	 */

	/**
	 * Y-axe values
	 */
	private List<PointDateDouble> values = new ArrayList<PointDateDouble>();


  private List<String> categories = new ArrayList<String>();

	/**
	 * displayed value on X-axe
	 * having it here has no extra meaning only for easy debugging
	 */
	private String xName;

	/**
	 * Constructs default instance with default values for Java types
	 */
	public HiChartSerie(){

	}

	/**
	 *
	 * @param xName
	 * @param evaluationIds
	 * @param values
	 * @param label
	 */
  //todo evaluationids is not nescessary
	public HiChartSerie(String xName, List<Integer> evaluationIds, List<PointDateDouble> values, String label){
	    this.xName = xName;
	    this.values = values;
	    this.label = label;
	}

	/**
	 * Label for this record displayed inside of the chart
	 * Label has value equals one of {@link QuestionThreshold#getInfo()}
	 */
	public String getLabel(){
		return label;
	}

	/**
	 * Label for this record displayed inside of the chart
	 *
	 * @param newVal
	 */
	public void setLabel(String newVal){
		label = newVal;
	}



	/**
	 * Y-axe values
	 */
	public List<PointDateDouble> getValues(){
		return values;
	}

	/**
	 * displayed value on X-axe
	 * having it here has no extra meaning only for easy debugging
	 */
	public String getXName(){
		return xName;
	}

	/**
	 * Y-axe values
	 *
	 * @param newVal
	 */
	public void setValues(List<PointDateDouble> newVal){
		values = newVal;
	}

	/**
	 * displayed value on X-axe
	 * having it here has no extra meaning only for easy debugging
	 *
	 * @param newVal
	 */
	public void setXName(String newVal){
		xName = newVal;
	}

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  /**
     * Returns String representation of this object
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append( label ).append( " (" ).append( xName ).append( ") : [ " );
        if(values != null) {
            for( PointDateDouble d : values) {
                if(d.value == null ) {
                    str.append("0 ");
                } else {
                    str.append( d.value.intValue() );
                }
            }
        }
        str.append( "] " );

        return str.toString();
    }

}