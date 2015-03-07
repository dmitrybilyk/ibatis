package com.ibatis.scorecardmodel.bo.interaction;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jamel.gasmi
 * Date: 25/01/12
 * Time: 12:19
 */
public class ConfidenceRange implements Serializable {
    private double min = 0d;
    private double max = 100d;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}