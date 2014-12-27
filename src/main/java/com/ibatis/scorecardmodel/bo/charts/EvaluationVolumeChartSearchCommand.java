/**
 *
 */
package com.ibatis.scorecardmodel.bo.charts;

import com.ibatis.scorecardmodel.bo.question.QuestformBO;
import com.ibatis.scorecardmodel.bo.tools.ScorecardEnums;
import com.ibatis.scorecardmodel.bo.user.CcGroupBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 *
 * Command for search Evaluation Volume
 *
 * Boolean are used instead of EnumSet because f??? GWT
 *
 * Karel Tejnora <karel.tejnora@zoomint.com>; Zoom International, s. r. o. Created At: 6:13:27 PM
 */
public class EvaluationVolumeChartSearchCommand implements Serializable {

    private static final long serialVersionUID = 1001L;

    /**
     * chart type - display absolute values or ration
     */
    private ScorecardEnums.EvaluationsVolumeChartType chartType;

    /**
     * Calculate Planned Evaluations
     */
    private boolean planned;

    /**
     * Calculate In Progress Evaluations
     */
    private boolean inProgress;

    /**
     * Calculate Finished Evaluations
     */
    private boolean finished;

    /**
     * calculate from
     */
    private Date from;
    /**
     * calculate to
     */
    private Date to;
    /**
     * timescale for a graph
     */
    private ChartTimeInterval timeScale;
    /**
     * agents to calculate
     */
    private Collection<UserBO> evaluators;
    /**
     * groups to calculate, takes precedence to groups
     */
    private Collection<CcGroupBO> groups;
    /**
     * which questionnaires to calculate
     */
    private Collection<QuestformBO> questionnaires;

    /**
     * Creates new default instance with:
     * <ul>
     * <li>chart type to absolute</li>
     * <li>all statuses</li>
     * <li>from and to set to last month</li>
     * <li>timeScale set to day
     * <li>empty evaluators = all evaluators</li>
     * <li>empty groups = all groups</li>
     * <li>empty questionnaires = all questionnaires</li>
     * </ul>
     */
    public EvaluationVolumeChartSearchCommand() {
        planned = true;
        inProgress = true;
        finished = true;
        setTo( new Date() );
        setFrom( new Date() );
        setTimeScale( ChartTimeInterval.DAY_VALUES );
        evaluators = Collections.emptySet();
        groups = Collections.emptySet();
        questionnaires = Collections.emptySet();
//        setChartType( ScorecardEnums.EvaluationsVolumeChartType.VOLUME );
    }

    /**
     * @return boolean - is Planned checked and will be computed
     */
    public boolean isPlanned() {
        return planned;
    }

    /**
     * @return boolean - is In Progress checked and will be computed
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * @return boolean - is Finished checked and will be computed
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @param enabled
     *            - finished will be computed
     */
    public void setPlanned(boolean enabled) {
        planned = enabled;
    }

    /**
     * @param enabled
     *            - finished will be computed
     */
    public void setInProgress(boolean enabled) {
        inProgress = enabled;
    }

    /**
     * @param enabled
     *            - finished will be computed
     */
    public void setFinished(boolean enabled) {
        finished = enabled;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(Date from) {
        this.from = from;
    }

    /**
     * @return the from
     */
    public Date getFrom() {
        return from;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(Date to) {
        this.to = to;
    }

    /**
     * @return the to
     */
    public Date getTo() {
        return to;
    }

    /**
     * @param timeScale
     *            the timeScale to set
     */
    public void setTimeScale( ChartTimeInterval timeScale) {
        this.timeScale = timeScale;
    }

    /**
     * @return the timeScale
     */
    public ChartTimeInterval getTimeScale() {
        return timeScale;
    }

    /**
//     * @param agents
     *            the agents to set
     */
    public void setEvaluators(Collection<UserBO> evaluators) {
        if( evaluators == null ) {
            this.evaluators = Collections.emptySet();
        } else {
            this.evaluators = evaluators;
        }
    }

    /**
     * @return the agents
     */
    public Collection<UserBO> getEvaluators() {
        return evaluators;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(Collection<CcGroupBO> groups) {
        if( groups == null ) {
            this.groups = Collections.emptySet();
        } else {
            this.groups = groups;
        }
    }

    /**
     * @return the groups
     */
    public Collection<CcGroupBO> getGroups() {
        return groups;
    }

    /**
     * @param questionnaires
     *            the questionnaires to set
     */
    public void setQuestionnaires(Collection<QuestformBO> questionnaires) {
        if( questionnaires == null ) {
            this.questionnaires = Collections.emptySet();
        } else {
            this.questionnaires = questionnaires;
        }
    }

    /**
     * @return the questionnaires
     */
    public Collection<QuestformBO> getQuestionnaires() {
        return questionnaires;
    }

//    /**
//     * @param chartType the chartType to set - if null chartType will be {@link EvaluationsVolumeChartType#ABSOLUTE}
//     */
//    public void setChartType(EvaluationsVolumeChartType chartType) {
//        if( chartType == null ) {
//            this.chartType = EvaluationsVolumeChartType.VOLUME;
//        } else {
//            this.chartType = chartType;
//        }
//    }
//
//    /**
//     * @return the chartType
//     */
//    public EvaluationsVolumeChartType getChartType() {
//        return chartType;
//    }

}
