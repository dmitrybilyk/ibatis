//package rest.questionnaire;
//
//import cz.zoom.scorecard.business.app.exception.InitializationException;
//import cz.zoom.scorecard.business.app.exception.NotAllowedException;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.question.QuestionManager;
//import cz.zoom.scorecard.business.app.transformation.jaxb.JAXBQuestionnaireHelper;
//import cz.zoom.scorecard.business.app.transformation.jaxb.Questionnaire;
//import cz.zoom.scorecard.business.bo.question.QuestformBO;
//import cz.zoom.scorecard.rest.api.ApiUtils;
//import cz.zoom.util.configuration.ConfigurationException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.rmi.RemoteException;
//
//import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
//
//@Singleton
//@Path("/questionnaire")
//public final class QuestionnaireResource {
//  private static final Logger logger = LoggerFactory.getLogger(QuestionnaireResource.class);
//
//  private JAXBQuestionnaireHelper jaxbHelper = new JAXBQuestionnaireHelper();
//  private QuestionManager questionManager;
//
//  @Inject
//  public QuestionnaireResource(QuestionManager questionManager) {
//    this.questionManager = questionManager;
//  }
//
//  @GET
//  @Path("/{id}")
//  @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
//  public Response getQuestionnaireById(@PathParam("id") Integer questionnaireId)
//          throws RemoteException, ConfigurationException, InitializationException, RequestFailedException, NotAllowedException {
//
//    try {
//      ApiUtils.checkQuestionnairesPermission();
//
//      assertNotNull(questionnaireId, "Questionnaire ID was not provided");
//
//      logger.debug("Looking for questionnaire with ID: {}", questionnaireId);
//
//      QuestformBO questform = questionManager.getQuestionFormComplete(searchQuestForm(questionnaireId));
//      assertNotNull(questform, "Questionnaire with ID \"" + questionnaireId + "\" does not exist");
//
//      logger.debug("Found QuestformBO: {}", questform);
//
//      Questionnaire jaxbResponse = jaxbHelper.convertToJaxbQuestionnaire(questform);
//      logger.debug("Converted to JAXBQuestionnaire: {}", jaxbResponse);
//
//      return Response.ok(jaxbResponse).build();
//    } catch (Exception exc) {
//      throw ApiUtils.translateException(exc);
//    }
//  }
//
//  private QuestformBO searchQuestForm(Integer questformId) {
//    QuestformBO questform = new QuestformBO();
//    questform.setId(questformId);
//    return questform;
//  }
//}
