package rest.jaxb;

import com.ibatis.scorecardmodel.bo.question.*;
//import cz.zoom.scorecard.business.bo.question.*;
//import cz.zoom.scorecard.business.bo.question.QuestformBO.Type;
import cz.zoom.scorecard.business.app.transformation.jaxb.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.SortedSet;

/**
 * JAXB helper class for transforming QuestformBO to JAXB Questionnaire POJO and vice-versa,
 * used for export, import and API services.
 */
public class JAXBQuestionnaireHelper {
  private JAXBCommonHelper commonHelper = new JAXBCommonHelper();

  @NotNull
  public QuestformBO convertFromJaxbQuestionnaire(@NotNull Questionnaire jaxbQuestionnaire) {
    QuestformBO questformBO = new QuestformBO();
    questformBO.setQformname(jaxbQuestionnaire.getName());
    questformBO.setVersion(jaxbQuestionnaire.getVersion());
    questformBO.setScoringSystemEnum(QuestformBO.ScoringSystem.valueOf(jaxbQuestionnaire.getScoringSystem()));
    questformBO.setReportWeight(jaxbQuestionnaire.getReportWeight());
    if (jaxbQuestionnaire.getType() != null) {
//      questformBO.setTypeEnum(Type.valueOf(jaxbQuestionnaire.getType()));
    }
    int groupOrder = 0;
    for (Group group : jaxbQuestionnaire.getGroups().getGroup()) {
      questformBO.addQuestionGroup(convertFromJaxbQuestionGroup(group, groupOrder++));
    }
    return questformBO;
  }

  @NotNull
  public QuestiongroupBO convertFromJaxbQuestionGroup(@NotNull Group group, @Nullable Integer groupOrder) {
    QuestiongroupBO questiongroupBO = new QuestiongroupBO();
    questiongroupBO.setGroupname(group.getName());
    questiongroupBO.setGroupValue(group.getWeight());
    questiongroupBO.setDescription(group.getDescription());
    questiongroupBO.setGroupOrder(groupOrder);
    int questionOrder = 0;
    for (Question question : group.getQuestions().getQuestion()) {
      questiongroupBO.addQuestion(convertFromJaxbQuestion(question, questionOrder++));
    }
    return questiongroupBO;
  }

  @NotNull
  public QuestionBO convertFromJaxbQuestion(@NotNull Question question, @Nullable Integer questionOrder) {
    QuestionBO questionBO = new QuestionBO();
    questionBO.setQuestionText(question.getQuestionText());
    questionBO.setDescription(question.getDescription());
    questionBO.setQuestionOrder(questionOrder);
//    questionBO.setMediaFileBO(commonHelper.convertFromJaxbMediaFile(question.getMediaFile()));
    if (question.getQuestionType() != null) {
      questionBO.setQuestionTypeEnum(QuestionBO.QuestionType.valueOf(question.getQuestionType()));
    }
    if (questionBO.getQuestionTypeEnum().isScored()) {
      if (question.getWeight() != null) {
        questionBO.setQuestionValue(question.getWeight());
      } else {
        questionBO.setQuestionValue(question.getQuestionWeigh());
      }
    }
    int answerOrder = 0;
    for (Answer answer : question.getAnswers().getAnswer()) {
      questionBO.addAnswer(convertFromJaxbAnswer(answer, answerOrder++));
    }
    return questionBO;
  }


  @NotNull
  public AnswerBO convertFromJaxbAnswer(@NotNull Answer answer, @Nullable Integer answerOrder) {
    AnswerBO answerBO = new AnswerBO();
    answerBO.setAnswerText(answer.getAnswerText());
    answerBO.setAnswerValue(answer.getAnswerWeigh());
    if (answer.getScore() != null) {
      answerBO.setAnswerValue(answer.getScore());
    } else {
      answerBO.setAnswerValue(answer.getAnswerWeigh());
    }
    answerBO.setAnswerKeyEnum(AnswerBO.AnswerKey.fromKey(answer.getAnswerKey()));
    if (answer.getCompliance() != null) {
      answerBO.setComplianceEnum(AnswerBO.Compliance.valueOf(answer.getCompliance()));
    }
    answerBO.setDescription(answer.getDescription());
    answerBO.setAnswerOrder(answerOrder);
    return answerBO;
  }

  @NotNull
  public Questionnaire convertToJaxbQuestionnaire(@NotNull QuestformBO questformBO) {
    Questionnaire jaxbQuestionnaire = new Questionnaire();
    jaxbQuestionnaire.setName(questformBO.getQformname());
    jaxbQuestionnaire.setVersion(questformBO.getVersion());
    if (questformBO.getScoringSystemEnum() != null) {
      jaxbQuestionnaire.setScoringSystem(questformBO.getScoringSystemEnum().name());
    }
    jaxbQuestionnaire.setReportWeight(questformBO.getReportWeight());
    if (questformBO.getTypeEnum() != null) {
      jaxbQuestionnaire.setType(questformBO.getTypeEnum().name());
    }
    jaxbQuestionnaire.setGroups(convertToJaxbQuestionGroups(
            commonHelper.asSortedSet(questformBO.getQuestionGroups(), QuestiongroupBO.QUESTIONGROUP_ORDER_COMPARATOR)));
    return jaxbQuestionnaire;
  }

  @NotNull
  public Questionnaire.Groups convertToJaxbQuestionGroups(@NotNull SortedSet<QuestiongroupBO> questionGroupBOs) {
    Questionnaire.Groups groups = new Questionnaire.Groups();
    for (QuestiongroupBO questiongroupBO : questionGroupBOs) {
      groups.getGroup().add(convertToJaxbQuestionGroup(questiongroupBO));
    }
    return groups;
  }

  @NotNull
  public Group convertToJaxbQuestionGroup(@NotNull QuestiongroupBO questiongroupBO) {
    Group group = new Group();
    group.setName(questiongroupBO.getGroupname());
    if (questiongroupBO.getGroupValue() != null) {
      group.setWeight(questiongroupBO.getGroupValue());
    }
    group.setDescription(questiongroupBO.getDescription());
    group.setQuestions(convertToJaxbQuestions(
            commonHelper.asSortedSet(questiongroupBO.getQuestions(), QuestionBO.QUESTION_ORDER_COMPARATOR)));
    return group;
  }

  @NotNull
  public Group.Questions convertToJaxbQuestions(@NotNull SortedSet<QuestionBO> questionBOs) {
    Group.Questions questions = new Group.Questions();
    for (QuestionBO questionBO : questionBOs) {
      questions.getQuestion().add(convertToJaxbQuestion(questionBO));
    }
    return questions;
  }

  @NotNull
  public Question convertToJaxbQuestion(@NotNull QuestionBO questionBO) {
    Question question = new Question();
    question.setQuestionText(questionBO.getQuestionText());
    if (questionBO.getQuestionValue() != null) {
      question.setQuestionWeigh(questionBO.getQuestionValue());
    }
    question.setDescription(questionBO.getDescription());
    if (questionBO.getQuestionTypeEnum() != null) {
      question.setQuestionType(questionBO.getQuestionTypeEnum().name());
    }
//    question.setMediaFile(convertToJaxbMediaFile(questionBO.getMediaFileBO()));
    question.setAnswers(convertToJaxbAnswers(
            commonHelper.asSortedSet(questionBO.getAnswers(), AnswerBO.ANSWER_ORDER_COMPARATOR)));
    return question;
  }

  @Nullable
  private MediaFile convertToJaxbMediaFile(@Nullable SMediaFileBO mediaFile) {
    if (mediaFile == null) {
      return null;
    }
    MediaFile jaxbMediaFile = new MediaFile();
    jaxbMediaFile.setPath(mediaFile.getPath());
    jaxbMediaFile.setType(mediaFile.getType());
    jaxbMediaFile.setContentType(mediaFile.getContentType());
    return jaxbMediaFile;
  }

  @NotNull
  public Question.Answers convertToJaxbAnswers(@NotNull SortedSet<AnswerBO> answerBOs) {
    Question.Answers answers = new Question.Answers();
    for (AnswerBO answerBO : answerBOs) {
      answers.getAnswer().add(convertToJaxbAnswer(answerBO));
    }
    return answers;
  }

  @NotNull
  public Answer convertToJaxbAnswer(@NotNull AnswerBO answerBO) {
    Answer answer = new Answer();
    answer.setAnswerText(answerBO.getAnswerText());
    answer.setAnswerWeigh(answerBO.getAnswerValue() != null ? answerBO.getAnswerValue() : 0.0);
    answer.setAnswerKey(answerBO.getAnswerKeyEnum() != null ? answerBO.getAnswerKeyEnum().getKey() : null);
    answer.setDescription(answerBO.getDescription());
    if (answerBO.getComplianceEnum() != null) {
      answer.setCompliance(answerBO.getComplianceEnum().name());
    }
    return answer;
  }

}
