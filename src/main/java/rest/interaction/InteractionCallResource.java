//package rest.interaction;
//
//import cz.zoom.scorecard.business.app.exception.InitializationException;
//import cz.zoom.scorecard.business.app.interaction.InteractionManager;
//import cz.zoom.scorecard.business.bo.interaction.InteractionBO;
//import cz.zoom.scorecard.rest.api.ApiConstants;
//import cz.zoom.scorecard.rest.api.ApiUtils;
//import cz.zoom.scorecard.rest.util.ListInteractionWrapper;
//import cz.zoom.util.configuration.ConfigurationException;
//import org.jetbrains.annotations.NotNull;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.InputStream;
//import java.rmi.RemoteException;
//import java.util.List;
//
//import static cz.zoom.scorecard.rest.api.ApiUtils.assertNotNull;
//
//@Singleton
//@Path("/interaction")
//public final class InteractionCallResource {
//  private InteractionService service;
//
//  @Inject
//  public InteractionCallResource(InteractionManager interactionManager) throws RemoteException, ConfigurationException, InitializationException {
//    this.service = new InteractionService();
//    this.service.setInteractionManager(interactionManager);
//  }
//
//  /**
//   * Same as {@link #getInteractionCoupleByCoupleSid(ApiConstants.ResourceFormatEnum, String)}
//   * with {@link ApiConstants.ResourceFormatEnum#json} format.
//   *
//   * @param coupleSID The couple SID contained in a call
//   * @return Response object, in JSON format
//   */
//  @GET
//  @Produces({MediaType.APPLICATION_JSON})
//  public Response getInteractionCoupleByCoupleSidAsJSON(@QueryParam(ApiConstants.COUPLE_SID_PARAMETER) String coupleSID) {
//    assertNotNull(coupleSID, ApiConstants.COUPLE_SID_PARAMETER + " must be specified");
//    return getInteractions(coupleSID, ApiConstants.ResourceFormatEnum.json);
//  }
//
//  /**
//   * Same as {@link #getInteractionCoupleByCoupleSid(ApiConstants.ResourceFormatEnum, String)}
//   * with {@link ApiConstants.ResourceFormatEnum#xml} format.
//   *
//   * @param coupleSID The couple SID contained in a call
//   * @return Response object, in XML format
//   */
//  @GET
//  @Produces({MediaType.TEXT_XML})
//  public Response getInteractionCoupleByCoupleSidAsXML(@QueryParam(ApiConstants.COUPLE_SID_PARAMETER) String coupleSID) {
//    assertNotNull(coupleSID, ApiConstants.COUPLE_SID_PARAMETER + " must be specified");
//    return getInteractions(coupleSID, ApiConstants.ResourceFormatEnum.xml);
//  }
//
//  /**
//   * Same as {@link #getInteractionCoupleByCoupleSid(ApiConstants.ResourceFormatEnum, String)}
//   * with {@link ApiConstants.ResourceFormatEnum#mp3} format.
//   *
//   * @param coupleSID The couple SID contained in a call
//   * @return Response object, in MP3 format
//   */
//  @GET
//  @Produces(ApiConstants.AUDIO_MP3_MEDIA_TYPE)
//  public Response getInteractionCoupleMp3ByCoupleSid(@QueryParam(ApiConstants.COUPLE_SID_PARAMETER) String coupleSID) {
//    assertNotNull(coupleSID, ApiConstants.COUPLE_SID_PARAMETER + " must be specified");
//    return getInteractionsMediaFiles(coupleSID, ApiConstants.ResourceFormatEnum.mp3);
//  }
//
//  /**
//   * Gets all the couples will belong to the same call as the sid specified
//   *
//   * @param format    the format in which the interaction call will be returned (xml, json, mp3)
//   * @param coupleSID The couple SID contained in a call
//   * @return Response object, in the specified format
//   */
//  @GET
//  @Path("/{format:(xml|json|mp3)}")
//  public Response getInteractionCoupleByCoupleSid(@PathParam("format") ApiConstants.ResourceFormatEnum format,
//                                                  @QueryParam(ApiConstants.COUPLE_SID_PARAMETER) String coupleSID) {
//    assertNotNull(format, "format must be specified");
//    assertNotNull(coupleSID, ApiConstants.COUPLE_SID_PARAMETER + " must be specified");
//
//    switch (format) {
//      case json:
//      case xml:
//        return getInteractions(coupleSID, format);
//      case mp3:
//        return getInteractionsMediaFiles(coupleSID, format);
//      default:
//        return Response.status(Response.Status.BAD_REQUEST).entity("Unknown format").build();
//    }
//  }
//
//  @NotNull
//  public Response getInteractions(@NotNull String couplesid, @NotNull ApiConstants.ResourceFormatEnum format) {
//    try {
//      List<InteractionBO> interactions = service.getRelatedInteractions(couplesid);
//      ListInteractionWrapper interactionWrapper = new ListInteractionWrapper(interactions);
//      return Response.ok(interactionWrapper, format.getMimeType()).build();
//    } catch (Exception exc) {
//      throw ApiUtils.translateException(exc);
//    }
//  }
//
//  @NotNull
//  public Response getInteractionsMediaFiles(@NotNull String coupleSID, @NotNull ApiConstants.ResourceFormatEnum format) {
//    try {
//      InputStream contentStream = service.getInteractionsMp3InputStream(coupleSID, format);
//
//      return Response.ok(contentStream, ApiConstants.ResourceFormatEnum.mp3.getMimeType()).build();
//    } catch (Exception exc) {
//      throw ApiUtils.translateException(exc);
//    }
//  }
//
//}
