//package rest.evaluation;
//
//import cz.zoom.callrec.core.callstorage.util.SearchBO;
//import cz.zoom.callrec.core.callstorage.util.SearchBuilder;
//import cz.zoom.scorecard.business.app.evaluation.EvaluationCalculator;
//import cz.zoom.scorecard.business.app.evaluation.EvaluationManager;
//import cz.zoom.scorecard.business.app.exception.InvalidConfigurationException;
//import cz.zoom.scorecard.business.app.exception.NoSuchEntityException;
//import cz.zoom.scorecard.business.app.exception.NotAllowedException;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.user.UserManager;
//import cz.zoom.scorecard.business.bo.evaluation.*;
//import cz.zoom.scorecard.business.bo.question.SMediaFileBO;
//import cz.zoom.scorecard.business.bo.user.UserBO;
//import cz.zoom.scorecard.business.security.exception.AuthorizationRequiredException;
//import org.apache.commons.io.IOUtils;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.Set;
//import java.util.UUID;
//
///**
// * Service that does the actual business logic of the REST API related to the evaluations.
// */
//public class EvaluationService {
//  private static final Logger logger = LoggerFactory.getLogger(EvaluationService.class);
//
//  private EvaluationManager evaluationManager;
//  private UserManager userManager;
//
//  public EvaluationBO insertEvaluation(@NotNull EvaluationBO newEvaluationBO,
//                                       @Nullable EvaluationService.MediaFileProvider mediaFileProvider,
//                                       @NotNull EvaluationCalculator evaluationCalculator)
//          throws NotAllowedException, RequestFailedException, InvalidConfigurationException,
//          NoSuchEntityException, UnsupportedEncodingException, AuthorizationRequiredException {
//    Assert.notNull(newEvaluationBO, "newEvaluationBO must not be null");
//    Assert.notNull(newEvaluationBO.getQuestformBO(), "newEvaluationBO.questformBO must not be null");
//    Assert.notNull(newEvaluationBO.getQuestformBO().getQformid(), "newEvaluationBO.questformBO.qformid must not be null");
//    Assert.notNull(newEvaluationBO.getCreatedByBO(), "newEvaluationBO.createdByBO must not be null");
//    Assert.notNull(newEvaluationBO.getEvaluatorBO(), "newEvaluationBO.evaluatorBO must not be null");
//
//    // step 1: save eval with CREATED status and media files
//    prepareEvaluationBO(newEvaluationBO, evaluationCalculator);
//
//    EvaluationBO evaluationBO = evaluationManager.insertEvaluation(newEvaluationBO, true, false);
//    logger.debug("Successfully inserted evaluation with {} status: {}", evaluationBO.getEvalstatusEnum(), evaluationBO);
//
//    // step 2: save media files, if any
//    saveMediaFiles(evaluationBO, mediaFileProvider);
//
//    // step 3: finalize by looking up eval call couple SIDs
//    evaluationManager.finalizeEvaluationWithExternalEvalCalls(evaluationBO);
//    logger.debug("Successfully updated evaluation to {} status: {}", evaluationBO.getEvalstatusEnum(), evaluationBO);
//
//    return evaluationBO;
//  }
//
//  private void prepareEvaluationBO(EvaluationBO evaluationBO, EvaluationCalculator evaluationCalculator)
//          throws NotAllowedException, RequestFailedException,
//          InvalidConfigurationException, AuthorizationRequiredException, NoSuchEntityException {
//
//    assignEvaluatedUser(evaluationBO);
//
//    evaluationBO.setId(null); // ensure there's no ID, as it's a new eval
//    evaluationBO.setAllowModification(false);
//    if (evaluationBO.getEvaluationDate() == null) {
//      evaluationBO.setEvaluationDate(new Date());
//    }
//
//    InteractionTypeBO defaultInteractionType =
//            evaluationManager.getInteractionTypeByName(InteractionTypeBO.CALL_SYSTEM_TYPE_NAME);
//    for (CriteriaBO criteriaBO : evaluationBO.getCriteria()) {
//      prepareCriteriaBO(criteriaBO, defaultInteractionType);
//    }
//
//    if (!evaluationCalculator.recalculateEvaluation(evaluationBO)) {
//      throw new RequestFailedException("Failed recalculating the evaluation");
//    }
//
//    // reset statuses to CREATED, because couple SIDs are not found yet:
//    evaluationBO.setEvalstatusEnum(EvaluationBO.EvalStatus.CREATED);
//    for (CriteriaBO criteriaBO : evaluationBO.getCriteria()) {
//      for (SubevaluationBO subevaluationBO : criteriaBO.getSubevaluations()) {
//        subevaluationBO.setSubevalstatusEnum(SubevaluationBO.SubEvalStatus.CREATED);
//      }
//    }
//
//    logger.debug("Prepared evaluation: {}", evaluationBO);
//  }
//
//  private void assignEvaluatedUser(EvaluationBO evaluationBO) throws RequestFailedException {
//    if (evaluationBO.getEvaluatedUserBO() != null) {
//      UserBO calledUser = getCalledUser(evaluationBO.getEvaluatedUserBO().getPhone());
//      evaluationBO.setEvaluatedUserBO(calledUser);
//      return;
//    }
//    for (CriteriaBO criteriaBO : evaluationBO.getCriteria()) {
//      for (SubevaluationBO subevaluationBO : criteriaBO.getSubevaluations()) {
//        for (EvalcallBO evalcallBO : subevaluationBO.getEvalCalls()) {
//          if (StringUtils.hasText(evalcallBO.getToNumber())) {
//            UserBO calledUser = getCalledUser(evalcallBO.getToNumber());
//            evaluationBO.setEvaluatedUserBO(calledUser);
//            return;
//          }
//        }
//      }
//    }
//    throw new IllegalArgumentException("Cannot find evaluated user - no calls with toNumber specified");
//  }
//
//  private void prepareCriteriaBO(CriteriaBO criteriaBO, InteractionTypeBO defaultInteractionType) {
//    if (criteriaBO.getCallDirectionEnum() == null
//            || criteriaBO.getCallDirectionEnum() == CriteriaBO.CallDirection.UNKNOWN) {
//      criteriaBO.setCallDirectionEnum(CriteriaBO.CallDirection.BOTH);
//    }
//    criteriaBO.setAllowCallFilling(false);
//    if (criteriaBO.getAllowCallReplacement() == null) {
//      criteriaBO.setAllowCallReplacement(false);
//    }
//    if (criteriaBO.getAllowCallSelection() == null) {
//      criteriaBO.setAllowCallSelection(false);
//    }
//    if (criteriaBO.getFinishSubcriteriaFirst() == null) {
//      criteriaBO.setFinishSubcriteriaFirst(true);
//    }
//    if (criteriaBO.getOrdering() == null) {
//      criteriaBO.setOrdering(0);
//    }
//    criteriaBO.setRandomOrder(false);
//    if (criteriaBO.getPeriodFrom() == null) {
//      criteriaBO.setPeriodFrom(criteriaBO.getEvaluationBO().getEvaluationDate());
//    }
//    if (criteriaBO.getPeriodTo() == null) {
//      criteriaBO.setPeriodTo(criteriaBO.getEvaluationBO().getEvaluationDate());
//    }
//
//    for (SubevaluationBO subevaluationBO : criteriaBO.getSubevaluations()) {
//      prepareSubevaluationBO(subevaluationBO, defaultInteractionType);
//    }
//  }
//
//  private void prepareSubevaluationBO(SubevaluationBO subevaluationBO, InteractionTypeBO defaultInteractionType) {
//    if (subevaluationBO.getInteractionTypeBO() == null) {
//      subevaluationBO.setInteractionTypeBO(defaultInteractionType);
//    }
//    for (EvalcallBO evalcallBO : subevaluationBO.getEvalCalls()) {
//      if (evalcallBO.getInteractionTypeBO() == null) {
//        evalcallBO.setInteractionTypeBO(defaultInteractionType);
//      }
//      if (evalcallBO.getStartDate() == null) {
//        evalcallBO.setStartDate(subevaluationBO.getCriteriaBO().getEvaluationBO().getEvaluationDate());
//      }
//    }
//    for (EvalAnswerBO evalAnswerBO : subevaluationBO.getEvalAnswers()) {
//      SMediaFileBO mediaFileBO = evalAnswerBO.getMediaFileBO();
//      if (mediaFileBO != null) {
//        Assert.notNull(mediaFileBO.getPath(), "path must be specified for media file");
//        Assert.notNull(mediaFileBO.getType(), "type must be specified for media file");
//        Assert.notNull(mediaFileBO.getContentType(), "content type must be specified for media file");
//        // ensure path is unique - via UUID because IDs are not known at this point
//        mediaFileBO.setPath("processing/" + UUID.randomUUID().toString() + "/" + mediaFileBO.getFilename());
//      }
//    }
//  }
//
//  private UserBO getCalledUser(String calledNumber) throws RequestFailedException {
//    SearchBO searchBO = new SearchBuilder()
//            .and(SearchBuilder.create(UserBO.Fields.ID_USED, UserBO.IdentificatorUsed.PHONE))
//            .and(SearchBuilder.create(UserBO.Fields.PHONE, calledNumber))
//            .toSearch();
//
//    Set<UserBO> users = userManager.getUsersBasic(searchBO, true);
//    if (users.isEmpty()) {
//      // no active users found - try inactive users too:
//      users = userManager.getUsersBasic(searchBO, false);
//    }
//
//    if (users.isEmpty()) {
//      throw new IllegalArgumentException("Phone number doesn't match any known user: " + calledNumber);
//    } else if (users.size() > 1) {
//      throw new IllegalArgumentException("Phone number matches multiple users: " + calledNumber);
//    }
//    return users.iterator().next();
//  }
//
//  private void saveMediaFiles(EvaluationBO evaluationBO, EvaluationService.MediaFileProvider mediaFileProvider)
//          throws UnsupportedEncodingException, RequestFailedException, NotAllowedException {
//    for (CriteriaBO criteriaBO : evaluationBO.getCriteria()) {
//      for (SubevaluationBO subevaluationBO : criteriaBO.getSubevaluations()) {
//        for (EvalAnswerBO evalAnswerBO : subevaluationBO.getEvalAnswers()) {
//          SMediaFileBO mediaFileBO = evalAnswerBO.getMediaFileBO();
//          if (mediaFileBO == null) {
//            continue;
//          }
//          InputStream mediaFileStream = mediaFileProvider != null
//                  ? mediaFileProvider.getMediaFileStream(mediaFileBO.getFilename()) : null;
//          if (mediaFileStream == null) {
//            throw new IllegalArgumentException("Missing content for media file: " + mediaFileBO.getFilename());
//          }
//          try {
//            setEvalAnswerMediaFilePath(mediaFileBO, evalAnswerBO); // set correct path
//            evaluationManager.writeSMediaFileContent(mediaFileBO, mediaFileStream); // save the content
//          } finally {
//            IOUtils.closeQuietly(mediaFileStream);
//          }
//          logger.debug("Saved content for media file: {}", mediaFileBO);
//        }
//      }
//    }
//  }
//
//  private void setEvalAnswerMediaFilePath(SMediaFileBO mediaFileBO, EvalAnswerBO evalAnswerBO)
//          throws RequestFailedException {
//    EvaluationBO evaluationBO = evalAnswerBO.getSubevaluationBO().getCriteriaBO().getEvaluationBO();
//
//    // note: SMediaFileBO paths always contain / as a separator
//    String filePath = "evaluation/" + evaluationBO.getId() + "/"
//            + evalAnswerBO.getId() + "/" + mediaFileBO.getFilename();
//    mediaFileBO.setPath(filePath);
//
//    evaluationManager.updateSMediaFileBO(mediaFileBO); // save the correct path
//    logger.debug("Updated path for media file: {}", mediaFileBO);
//  }
//
//  public EvaluationManager getEvaluationManager() {
//    return evaluationManager;
//  }
//
//  public void setEvaluationManager(EvaluationManager evaluationManager) {
//    this.evaluationManager = evaluationManager;
//  }
//
//  public UserManager getUserManager() {
//    return userManager;
//  }
//
//  public void setUserManager(UserManager userManager) {
//    this.userManager = userManager;
//  }
//
//  interface MediaFileProvider {
//    /**
//     * Returns the contents of the media file for the specified filename, if any
//     *
//     * @param filename the filename uniquely identifying the media file
//     * @return the media file content as a stream, or null if no such content was provided
//     */
//    @Nullable
//    InputStream getMediaFileStream(@NotNull String filename);
//  }
//}
