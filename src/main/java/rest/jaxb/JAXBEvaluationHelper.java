package rest.jaxb;

//import cz.zoom.scorecard.business.bo.evaluation.*;
//import cz.zoom.scorecard.business.bo.evaluation.CriteriaBO.CallDirection;
//import cz.zoom.scorecard.business.bo.evaluation.SubevaluationBO.SubEvalStatus;
//import cz.zoom.scorecard.business.bo.question.AnswerBO;
//import cz.zoom.scorecard.business.bo.question.AnswerBO.AnswerKey;
//import cz.zoom.scorecard.business.bo.question.AnswerBO.Compliance;
//import cz.zoom.scorecard.business.bo.question.QuestformBO;
//import cz.zoom.scorecard.business.bo.question.QuestionBO;
//import cz.zoom.scorecard.business.bo.question.QuestiongroupBO;
//import cz.zoom.scorecard.business.bo.user.UserBO;
import cz.zoom.scorecard.business.app.transformation.jaxb.Evaluation;
import cz.zoom.scorecard.business.app.transformation.jaxb.Criteria;
import cz.zoom.scorecard.business.app.transformation.jaxb.User;
import cz.zoom.scorecard.business.app.transformation.jaxb.SubEvaluation;
import cz.zoom.scorecard.business.app.transformation.jaxb.EvalCall;
import cz.zoom.scorecard.business.app.transformation.jaxb.EvalAnswer;
import cz.zoom.scorecard.business.app.transformation.jaxb.Answer;
import com.ibatis.scorecardmodel.bo.evaluation.*;
import com.ibatis.scorecardmodel.bo.question.AnswerBO;
import com.ibatis.scorecardmodel.bo.question.QuestformBO;
import com.ibatis.scorecardmodel.bo.question.QuestionBO;
import com.ibatis.scorecardmodel.bo.question.QuestiongroupBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 *
 */
public class JAXBEvaluationHelper {
  private JAXBCommonHelper commonHelper = new JAXBCommonHelper();

  @NotNull
  public EvaluationBO convertFromJaxbEvaluation(@NotNull Evaluation jaxbEvaluation,
                                                @NotNull QuestformBO forQuestForm) {
    EvaluationBO evaluation = new EvaluationBO();
    evaluation.setQuestformBO(forQuestForm);
    evaluation.setEvaluatorBO(convertFromJaxbUser(jaxbEvaluation.getEvaluator()));
    evaluation.setEvaluatedUserBO(convertFromJaxbUser(jaxbEvaluation.getEvaluatee()));
    evaluation.setEvaluationDate(commonHelper.toDate(jaxbEvaluation.getEvaluationDate()));
    boolean criteriaIsMain = true;
    for (Criteria jaxbCriteria : jaxbEvaluation.getCriteria()) {
      CriteriaBO criteriaBO = convertFromJaxbCriteria(jaxbCriteria, forQuestForm, criteriaIsMain);
      evaluation.addCriteria(criteriaBO);
      criteriaIsMain = false; // first is main, rest are not
    }
    return evaluation;
  }

  @Nullable
  private UserBO convertFromJaxbUser(@Nullable User jaxbUser) {
    if (jaxbUser == null) {
      return null;
    }

    UserBO user = new UserBO();
    user.setName(jaxbUser.getName());
    user.setPhone(jaxbUser.getPhone());
    return user;
  }

  @NotNull
  private CriteriaBO convertFromJaxbCriteria(@NotNull Criteria jaxbCriteria,
                                             @NotNull QuestformBO forQuestForm,
                                             @NotNull Boolean isMain) {
    CriteriaBO criteria = new CriteriaBO();
    criteria.setIsMain(isMain);
    int totalEvents = 0;
    for (SubEvaluation jaxbSubEvaluation : jaxbCriteria.getSubEvaluation()) {
      SubevaluationBO subevaluationBO = convertFromJaxbSubEvaluation(jaxbSubEvaluation, forQuestForm);
      totalEvents += subevaluationBO.getEvalCalls().size(); // count the number of calls
      criteria.addSubevaluation(subevaluationBO);
    }
    criteria.setTotalEvents(totalEvents);
    criteria.setCallDirectionEnum(CriteriaBO.CallDirection.UNKNOWN);
    return criteria;
  }

  @NotNull
  private SubevaluationBO convertFromJaxbSubEvaluation(@NotNull SubEvaluation jaxbSubEvaluation,
                                                       @NotNull QuestformBO forQuestForm) {
    SubevaluationBO subEvaluation = new SubevaluationBO();
    subEvaluation.setSubevalstatusEnum(SubevaluationBO.SubEvalStatus.CREATED);

    boolean isMainEvalCall = true;
    for (EvalCall jaxbEvalCall : jaxbSubEvaluation.getEvalCalls().getEvalCall()) {
      subEvaluation.addEvalCall(convertFromJaxbEvalCall(jaxbEvalCall, isMainEvalCall));
      isMainEvalCall = false; // only the first is main
    }

    List<QuestionBO> questionIndex = generateQuestionIndex(forQuestForm);
    if (questionIndex.size() != jaxbSubEvaluation.getEvalAnswers().getEvalAnswer().size()) {
      throw new IllegalArgumentException(String.format(
              "Got %s answers (in subevaluation), but expected %s questions (in questform).",
              jaxbSubEvaluation.getEvalAnswers().getEvalAnswer().size(), questionIndex.size()));
    }

    int order = 0;
    for (EvalAnswer jaxbEvalAnswer : jaxbSubEvaluation.getEvalAnswers().getEvalAnswer()) {
      QuestionBO forQuestion = questionIndex.get(order++);
      if (!forQuestion.getQuestionTypeEnum().isAnswerable()) {
        // prompt-only cannot have answers
        if (jaxbEvalAnswer.getAnswer() != null) {
          throw new IllegalArgumentException(String.format(
                  "%s questions cannot have an answer, but an answer was provided: %s",
                  forQuestion.getQuestionTypeEnum(), jaxbEvalAnswer.getAnswer()));
        }
      } else {
        subEvaluation.addEvalAnswer(convertFromJaxbEvalAnswer(jaxbEvalAnswer, forQuestion));
      }
    }
    return subEvaluation;
  }

  @NotNull
  private EvalcallBO convertFromJaxbEvalCall(@NotNull EvalCall jaxbEvalCall, boolean isMain) {
    EvalcallBO evalCall = new EvalcallBO();
    evalCall.setSid(jaxbEvalCall.getSID());
    evalCall.setDescription(jaxbEvalCall.getDescription());
    evalCall.setExternalIdType(jaxbEvalCall.getExternalIDType());
    evalCall.setExternalId(jaxbEvalCall.getExternalID());
    evalCall.setFromNumber(jaxbEvalCall.getFromNumber());
    evalCall.setToNumber(jaxbEvalCall.getToNumber());
    evalCall.setStartDate(commonHelper.toDate(jaxbEvalCall.getStartDate()));
    evalCall.setStopDate(commonHelper.toDate(jaxbEvalCall.getStopDate()));
    if (jaxbEvalCall.getLength() != null) {
      evalCall.setLength(jaxbEvalCall.getLength());
    }
    evalCall.setIsMain(isMain);
    return evalCall;
  }

  @NotNull
  private EvalAnswerBO convertFromJaxbEvalAnswer(@NotNull EvalAnswer jaxbEvalAnswer,
                                                 @NotNull QuestionBO forQuestion) {
    EvalAnswerBO evalAnswer = new EvalAnswerBO();
//    evalAnswer.setMediaFileBO(commonHelper.convertFromJaxbMediaFile(jaxbEvalAnswer.getMediaFile()));
    evalAnswer.setAnswerBO(findAnswer(jaxbEvalAnswer.getAnswer(), forQuestion));
    evalAnswer.setNote(jaxbEvalAnswer.getNote());

    return evalAnswer;
  }

  private AnswerBO findAnswer(@Nullable Answer jaxbAnswer, @NotNull QuestionBO forQuestion) {
    if (jaxbAnswer != null && jaxbAnswer.getAnswerKey() != null) {
      // normal scenario => map by answer key
      AnswerBO.AnswerKey key = AnswerBO.AnswerKey.fromKey(jaxbAnswer.getAnswerKey());
      if (key == null) {
        throw new IllegalArgumentException("Invalid answer key value: " + jaxbAnswer.getAnswerKey());
      }
      for (AnswerBO answer : forQuestion.getAnswers()) {
        if (answer.getAnswerKeyEnum() == key) {
          return answer;
        }
      }
      throw new IllegalArgumentException(String.format(
              "Answer has key %s, but there's no answer with this key: %s", key, forQuestion.getAnswers()));
    } else {
      // no answer scenario => translate to the answer with no key and N/A compliance
      for (AnswerBO answer : forQuestion.getAnswers()) {
        if (answer.getAnswerKeyEnum() == AnswerBO.AnswerKey.NONE
                && answer.getComplianceEnum() == AnswerBO.Compliance.NOT_APPLICABLE) {
          return answer;
        }
      }
      throw new IllegalArgumentException(String.format(
              "Answer is empty or has no key, but there's no matching N/A answer without a key: %s",
              forQuestion.getAnswers()));
    }
  }

  private List<QuestionBO> generateQuestionIndex(@NotNull QuestformBO forQuestForm) {
    List<QuestionBO> questionIndex = new ArrayList<QuestionBO>();

    SortedSet<QuestiongroupBO> sortedGroups = commonHelper.asSortedSet(
            forQuestForm.getQuestionGroups(), QuestiongroupBO.QUESTIONGROUP_ORDER_COMPARATOR);

    for (QuestiongroupBO group : sortedGroups) {
      SortedSet<QuestionBO> sortedQuestions = commonHelper.asSortedSet(
              group.getQuestions(), QuestionBO.QUESTION_ORDER_COMPARATOR);

      questionIndex.addAll(sortedQuestions);
    }

    return questionIndex;
  }
}
