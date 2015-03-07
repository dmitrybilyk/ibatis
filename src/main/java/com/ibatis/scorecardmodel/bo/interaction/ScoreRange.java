package com.ibatis.scorecardmodel.bo.interaction;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jamel.gasmi
 * Date: 25/01/12
 * Time: 12:19
 */
public class ScoreRange implements Serializable {
    private Integer startScore;
    private Integer endScore;

    public Integer getStartScore() {
        return startScore;
    }

    public void setStartScore(Integer startScore) {
        this.startScore = startScore;
    }

    public Integer getEndScore() {
        return endScore;
    }

    public void setEndScore(Integer endScore) {
        this.endScore = endScore;
    }
}