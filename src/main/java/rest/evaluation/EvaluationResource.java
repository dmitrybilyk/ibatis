//package rest.evaluation;
//
////import cz.zoom.scorecard.business.app.evaluation.EvaluationCalculator;
////import cz.zoom.scorecard.business.app.evaluation.EvaluationManager;
////import cz.zoom.scorecard.business.app.exception.InitializationException;
////import cz.zoom.scorecard.business.app.exception.NotAllowedException;
////import cz.zoom.scorecard.business.app.exception.RequestFailedException;
////import cz.zoom.scorecard.business.app.question.QuestionManager;
////import cz.zoom.scorecard.business.app.tool.ToolManager;
////import cz.zoom.scorecard.business.app.transformation.jaxb.Evaluation;
////import cz.zoom.scorecard.business.app.transformation.jaxb.JAXBEvaluationHelper;
////import cz.zoom.scorecard.business.app.user.UserManager;
////import cz.zoom.scorecard.business.bo.evaluation.EvaluationBO;
////import cz.zoom.scorecard.business.bo.question.QuestformBO;
////import cz.zoom.scorecard.business.bo.tools.AppoptionBO;
////import cz.zoom.scorecard.business.bo.tools.ScorecardEnums;
////import cz.zoom.scorecard.business.bo.user.UserBO;
////import cz.zoom.scorecard.business.options.ApplicationOptions;
////import cz.zoom.scorecard.business.security.ScorecardSecurityUtils;
////import cz.zoom.scorecard.business.security.exception.AuthorizationRequiredException;
////import cz.zoom.util.configuration.ConfigurationException;
//import org.glassfish.jersey.media.multipart.FormDataBodyPart;
//import org.glassfish.jersey.media.multipart.FormDataMultiPart;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import rest.api.UserManager;
//import rest.exception.InitializationException;
//import rest.jaxb.JAXBEvaluationHelper;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.naming.ConfigurationException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.io.StringWriter;
//import java.nio.charset.Charset;
//import java.rmi.RemoteException;
//
////import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
////import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//
//@Singleton
//@Path("/evaluation")
//public final class EvaluationResource {
//  private static final Logger logger = LoggerFactory.getLogger(EvaluationResource.class);
//
////  private ToolManager toolManager;
////  private QuestionManager questionManager;
//  private EvaluationService service;
//
//  private JAXBEvaluationHelper jaxbHelper = new JAXBEvaluationHelper();
//
//  private JAXBContext jaxbContext;
//
//  @Inject
//  public EvaluationResource(ToolManager toolManager,
//                            QuestionManager questionManager,
//                            EvaluationManager evaluationManager,
//                            UserManager userManager) throws RemoteException, ConfigurationException, InitializationException, JAXBException {
//    this.toolManager = toolManager;
//    this.questionManager = questionManager;
//
//    service = new EvaluationService();
//    service.setEvaluationManager(evaluationManager);
//    service.setUserManager(userManager);
//    jaxbContext = JAXBContext.newInstance(Evaluation.class);
//  }
//
//  @POST
//  @Path("/submit")
//  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//  public Response submitEvaluation(@QueryParam("Evaluation") Evaluation evaluation,
//                                   final MultivaluedMap<String, String> params) throws JAXBException {
//
//    return submitEvaluation(evaluation,
//            // how to get a media filesfrom query:
//            new EvaluationService.MediaFileProvider() {
//              public InputStream getMediaFileStream(@NotNull String filename) {
//                String fileContent = params.getFirst(filename);
//                return fileContent == null ? null
//                        : new ByteArrayInputStream(fileContent.getBytes(Charset.forName("UTF-8")));
//              }
//            });
//  }
//
//  @POST
//  @Path("/submit")
//  @Consumes(MediaType.MULTIPART_FORM_DATA)
//  public Response submitEvaluation(@FormDataParam("Evaluation") Evaluation evaluation,
//                                   final FormDataMultiPart multiPartData) throws JAXBException {
//
//    return submitEvaluation(evaluation,
//            //how to get a media file from multipart:
//            new EvaluationService.MediaFileProvider() {
//              public InputStream getMediaFileStream(@NotNull String filename) {
//                FormDataBodyPart fileContent = multiPartData.getField(filename);
//                return fileContent == null ? null : fileContent.getValueAs(InputStream.class);
//              }
//            });
//  }
//
//  @POST
//  @Path("/submit")
//  @Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_XML})
//  public Response submitEvaluation(Evaluation evaluation) throws JAXBException {
//
//    return submitEvaluation(evaluation,
//            // no media files provided:
//            new EvaluationService.MediaFileProvider() {
//              public InputStream getMediaFileStream(@NotNull String filename) {
//                return null;
//              }
//            });
//  }
//
//  private Response submitEvaluation(Evaluation evaluation, EvaluationService.MediaFileProvider mediaFileProvider)
//          throws JAXBException {
//    assertNotNull(evaluation, "Evaluation must be provided");
//    logEvaluation(evaluation);
//    assertNotNull(evaluation.getQuestionnaire(), "Evaluation questionnaire must be specified");
//    Integer questionnaireID = evaluation.getQuestionnaire().getQuestionnaireID();
//    assertNotNull(questionnaireID, "Evaluation questionnaire ID must be specified");
//
//    try {
//      QuestformBO questformBO = questionManager.getQuestionFormComplete(searchQuestForm(questionnaireID));
//      assertNotNull(questformBO, "Questionnaire not found: " + questionnaireID);
//
//      EvaluationBO evaluationBO = jaxbHelper.convertFromJaxbEvaluation(evaluation, questformBO);
//      logger.debug("Converted to evaluation: {}", evaluationBO);
//
//      // only allow current user to be evaluator
//      UserBO currentUser = ScorecardSecurityUtils.getCurrentUser();
//      if (currentUser == null) {
//        throw new AuthorizationRequiredException("No user logged in for inserting evaluation");
//      }
//      evaluationBO.setEvaluatorBO(currentUser);
//      evaluationBO.setCreatedByBO(currentUser);
//      evaluationBO = service.insertEvaluation(evaluationBO, mediaFileProvider, getEvaluationCalculator(evaluationBO));
//      return Response.ok(String.valueOf(evaluationBO.getId()), MediaType.TEXT_PLAIN_TYPE).build();
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private void logEvaluation(Evaluation evaluation) throws JAXBException {
//    if (logger.isDebugEnabled()) {
//      StringWriter stringWriter = new StringWriter();
//      Marshaller marshaller = jaxbContext.createMarshaller();
//      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, logger.isTraceEnabled());
//      marshaller.marshal(evaluation, stringWriter);
//      logger.debug("New evaluation received for submission:\n{}", stringWriter.toString());
//    }
//  }
//
//  private QuestformBO searchQuestForm(Integer questionnaireID) {
//    QuestformBO questform = new QuestformBO();
//    questform.setId(questionnaireID);
//    return questform;
//  }
//
//  private EvaluationCalculator getEvaluationCalculator(EvaluationBO evaluationBO) throws NotAllowedException, RequestFailedException {
//    ApplicationOptions<AppoptionBO.KEY> applicationOptions =
//            toolManager.getApplicationOptions(evaluationBO.getCreatedByBO().getCompanyBO());
//    ScorecardEnums.NOT_APPLICABLE_ANSWER_CALCULATING_METHOD_VALUES naCalculateMethod =
//            applicationOptions.getOptionObjectValue(
//                    AppoptionBO.KEY.NOT_APPLICABLE_ANSWER_CALCULATING_METHOD,
//                    ScorecardEnums.NOT_APPLICABLE_ANSWER_CALCULATING_METHOD_VALUES.METHOD1);
//    return new EvaluationCalculator(naCalculateMethod);
//  }
//
//}
