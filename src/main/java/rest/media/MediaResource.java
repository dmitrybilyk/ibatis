//package rest.media;
//
//import cz.zoom.scorecard.business.app.question.QuestionManager;
//import cz.zoom.scorecard.business.bo.question.SMediaFileBO;
//import cz.zoom.scorecard.rest.api.ApiUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.Response;
//import java.io.InputStream;
//
//import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//
//@Singleton
//@Path("/media")
//public final class MediaResource {
//  private static final Logger logger = LoggerFactory.getLogger(MediaResource.class);
//
//  private QuestionManager questionManager;
//
//  @Inject
//  public MediaResource(QuestionManager questionManager) {
//    this.questionManager = questionManager;
//  }
//
//  @GET
//  @Path("/{mediaFileType}/{mediaFilePath:.*}") // .* allows paths to contain slash /
//  public Response getQuestionnaireMediaFile(@PathParam("mediaFileType") String mediaFileType,
//                                            @PathParam("mediaFilePath") String mediaFilePath) {
//
//    try {
//      ApiUtils.checkQuestionnairesPermission();
//
//      assertNotNull(mediaFileType, "mediaFileType must be specified");
//      assertNotNull(mediaFilePath, "mediaFilePath must be specified");
//
//      logger.debug("Looking for {} media file at {}", mediaFileType, mediaFilePath);
//
//      SMediaFileBO mediaFile = new SMediaFileBO();
//      mediaFile.setType(mediaFileType);
//      mediaFile.setPath(mediaFilePath);
//
//      mediaFile = questionManager.getSMediaFile(mediaFile); // load fully from DB, fails if not found
//      logger.debug("Found media file record: {}", mediaFile);
//      InputStream stream = questionManager.readSMediaFileContent(mediaFile);
//      return Response.ok(stream, mediaFile.getContentType()).build();
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//}
