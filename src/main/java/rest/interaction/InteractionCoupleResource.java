//package rest.interaction;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import cz.zoom.scorecard.business.app.exception.InitializationException;
//import cz.zoom.scorecard.business.app.exception.InvalidConfigurationException;
//import cz.zoom.scorecard.business.app.interaction.InteractionManager;
//import cz.zoom.scorecard.business.bo.interaction.ExtDataSearchConfig;
//import cz.zoom.scorecard.business.bo.interaction.InteractionBO;
//import cz.zoom.scorecard.business.bo.interaction.MediaFileBO;
//import cz.zoom.scorecard.business.services.ExtDataSearchConfigService;
//import cz.zoom.scorecard.business.utils.JsonResourceView;
//import cz.zoom.scorecard.rest.api.ApiConstants;
//import cz.zoom.util.configuration.ConfigurationException;
//import org.apache.commons.io.FilenameUtils;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.InputStream;
//import java.rmi.RemoteException;
//import java.util.Collection;
//
//import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
//import static cz.zoom.scorecard.rest.api.ApiUtils.translateException;
//
//@Singleton
//@Path("/couple")
//public final class InteractionCoupleResource {
//
//  private InteractionManager interactionManager;
//  private ExtDataSearchConfigService extDataSearchConfigService;
//  private InteractionService service;
//
//  @Inject
//  public InteractionCoupleResource(InteractionManager interactionManager,
//                                   ExtDataSearchConfigService extDataSearchConfigService)
//          throws RemoteException, ConfigurationException, InitializationException {
//    this.extDataSearchConfigService = extDataSearchConfigService;
//    this.interactionManager = interactionManager;
//
//    service = new InteractionService();
//    service.setInteractionManager(interactionManager);
//  }
//
//  @GET
//  @Path("/extdata")
//  @Produces("application/json; charset=UTF-8")
//  @JsonView(JsonResourceView.ExternalDataKeyTextPair.class)
//  public Collection<ExtDataSearchConfig> getExtData() throws InvalidConfigurationException {
//    return extDataSearchConfigService.getExtDataSearchConfigs();
//  }
//
//  /**
//   * Gets the metadata of a couple in json format
//   *
//   * @param coupleSid the couple sid that belongs to the interaction
//   * @return json object with the interaction
//   */
//  @GET
//  @Path("{couplesid}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response getCoupleJson(@PathParam("couplesid") String coupleSid) {
//    return getInteractionAnyFormat(coupleSid, ApiConstants.ResourceFormatEnum.json);
//  }
//
//  @GET
//  @Path("{couplesid}/audio")
//  @Produces(ApiConstants.AUDIO_MEDIA_TYPE)
//  public Response getInteractionAudioWav(@PathParam("couplesid") String coupleSid) {
//    return getInteractionAnyFormat(coupleSid, ApiConstants.ResourceFormatEnum.audio);
//  }
//
//  /**
//   * Get the data of couple with format specified as parameter
//   *
//   * @param format    output format of the interaction. can be xml, mp3, recd, and wav
//   * @param coupleSid the couple sid that belongs to the interaction
//   * @return xml text, mp3, wav, or recd depending on the acceptHeader parameter
//   */
//  @GET
//  @Path("{couplesid}/{format:(json|mp3|recd|wav|xml)}")
//  public Response getInteractionAnyFormat(
//          @PathParam("couplesid") String coupleSid,
//          @PathParam("format") ApiConstants.ResourceFormatEnum format) {
//
//    assertNotNull(coupleSid, "couplesid must be specified");
//    assertNotNull(format, "format must be specified");
//
//    // first get the interaction
//    switch (format) {
//      case mp3:
//      case recd:
//      case wav:
//      case audio:
//        return buildMediaFileResponse(coupleSid, format);
//      case xml:
//      case json:
//        return buildInteractionResponse(coupleSid, format);
//      default:
//        return Response.status(Response.Status.BAD_REQUEST)
//                .entity("Invalid requested format: " + format.toString())
//                .type(MediaType.TEXT_PLAIN_TYPE)
//                .build();
//    }
//  }
//
//  private Response buildInteractionResponse(String coupleSid, ApiConstants.ResourceFormatEnum format) {
//    try {
//      InteractionBO interactionCoupleBySid = interactionManager.getInteractionCoupleBySid(coupleSid);
//      return Response.ok(interactionCoupleBySid, format.getMimeType()).build();
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private Response buildMediaFileResponse(String coupleSid, ApiConstants.ResourceFormatEnum format) {
//    try {
//      MediaFileBO mediaFile = service.findMediaFile(coupleSid, format);
//      String responseMimeType = getMimeType(mediaFile, format);
//      InputStream contentStream = interactionManager.getMediaFileStream(mediaFile);
//      return Response.ok(contentStream, responseMimeType).build();
//    } catch (Exception exc) {
//      throw translateException(exc);
//    }
//  }
//
//  private String getMimeType(MediaFileBO mediaFile, ApiConstants.ResourceFormatEnum format) {
//    String responseMimeType;
//    if (format.equals(ApiConstants.ResourceFormatEnum.audio)) {
//      String extension = FilenameUtils.getExtension(mediaFile.getPath());
//      responseMimeType = ApiConstants.ResourceFormatEnum.valueOf(extension).getMimeType();
//    } else {
//      responseMimeType = format.getMimeType();
//    }
//    return responseMimeType;
//  }
//}