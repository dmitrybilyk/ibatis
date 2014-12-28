//package rest.interaction;
//
//import cz.zoom.scorecard.business.app.exception.NoSuchEntityException;
//import cz.zoom.scorecard.business.app.exception.NotAllowedException;
//import cz.zoom.scorecard.business.app.exception.RequestFailedException;
//import cz.zoom.scorecard.business.app.interaction.InteractionManager;
//import cz.zoom.scorecard.business.bo.interaction.InteractionBO;
//import cz.zoom.scorecard.business.bo.interaction.MediaFileBO;
//import cz.zoom.scorecard.rest.api.ApiConstants;
//import cz.zoom.scorecard.rest.api.ApiUtils;
//import org.apache.commons.io.FilenameUtils;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
///**
// *
// */
//public class InteractionService {
//  private static final Logger logger = LoggerFactory.getLogger(InteractionService.class);
//
//  private InteractionManager manager;
//
//  @NotNull
//  public List<InteractionBO> getRelatedInteractions(@NotNull String sid)
//          throws NotAllowedException, NoSuchEntityException, RequestFailedException {
//    logger.debug("Looking for related interactions by sid: {}", sid);
//
//    // getRelatedInteractionsBySid() will also filter by permissions
//    List<InteractionBO> interactions = manager.getRelatedInteractionsBySid(sid);
//    if (interactions.isEmpty()) {
//      throw new NoSuchEntityException("Interaction with the specified parameters not found");
//    } else {
//      logger.debug("Found {} related interactions for sid: {}", interactions.size(), sid);
//      return interactions;
//    }
//  }
//
//  /**
//   * Finds all media files for specified couple SID and returns them as a single joined MP3 stream.
//   *
//   * See {@link InteractionManager#getMp3StreamFromAudioMediaFiles(java.util.List)} for details.
//   *
//   * @param coupleSID the couple SID to find interaction media files for
//   * @param format the format of the files to look for;
//   *               this only affects the lookup, since the files are converted to MP3 regardless of this format
//   * @return a single MP3 stream of all the files that were found, separated by an audio beep
//   * @throws NotAllowedException
//   * @throws NoSuchEntityException
//   * @throws RequestFailedException
//   * @throws java.io.FileNotFoundException
//   */
//  public InputStream getInteractionsMp3InputStream(String coupleSID, ApiConstants.ResourceFormatEnum format)
//          throws NotAllowedException, NoSuchEntityException, RequestFailedException, FileNotFoundException {
//    List<InteractionBO> interactions = getRelatedInteractions(coupleSID);
//    logger.debug("Filtering media files by format: {}", format);
//    List<MediaFileBO> mediaFiles = getCorrectMediaFiles(interactions, format);
//    if (mediaFiles.isEmpty()) {
//      throw new NoSuchEntityException("No media files with the specified format were found for the interactions.");
//    }
//    logger.debug("Found {} media files, converting to a single MP3 stream", mediaFiles.size());
//    return manager.getMp3StreamFromAudioMediaFiles(mediaFiles);
//  }
//
//  public MediaFileBO findMediaFile(String coupleSid, ApiConstants.ResourceFormatEnum format)
//          throws NoSuchEntityException, RequestFailedException, NotAllowedException {
//    InteractionBO interaction = manager.getInteractionCoupleBySid(coupleSid);
//    Set<MediaFileBO> mediaFiles = interaction.getMediaFiles();
//    if (mediaFiles.isEmpty()) {
//      throw new NoSuchEntityException("No media files found for specified couple SID: " + coupleSid);
//    }
//
//    MediaFileBO mediaFile = null;
//    if (format == ApiConstants.ResourceFormatEnum.audio) {
//      // audio format means we don't know the exact format
//      // first try mp3
//      mediaFile = getMediaFileByFormat(ApiConstants.ResourceFormatEnum.mp3, mediaFiles);
//      if (mediaFile == null) {
//        // mp3 was not found, try wav
//        mediaFile = getMediaFileByFormat(ApiConstants.ResourceFormatEnum.wav, mediaFiles);
//      }
//    } else {
//      mediaFile = getMediaFileByFormat(format, mediaFiles);
//    }
//
//    if (mediaFile == null) {
//      throw new NoSuchEntityException(String.format(
//              "No media files found with format \"%s\" for the specified couple SID: %s",
//              format, coupleSid));
//    } else {
//      logger.debug("For couple SID \"{}\" found media file: {}", coupleSid, mediaFile);
//    }
//
//    return mediaFile;
//  }
//
//  private List<MediaFileBO> getCorrectMediaFiles(List<InteractionBO> interactions,
//                                                 ApiConstants.ResourceFormatEnum format)
//          throws NoSuchEntityException {
//    logger.trace("Filtering media files by format: {}", format);
//
//    List<MediaFileBO> mediaFiles = new ArrayList<MediaFileBO>();
//
//    for (InteractionBO interaction : interactions) {
//      MediaFileBO found = null;
//      for (MediaFileBO mediaFile : interaction.getMediaFiles()) {
//        String requestedType = ApiUtils.getMediaFileType(format);
//
//        if (requestedType.equals(mediaFile.getType())) {
//          // same type (e.g., audio), but not necessarily same format - wav/mp3
//          found = mediaFile;
//          if (FilenameUtils.isExtension(mediaFile.getPath(), format.name())) {
//            break; // same type AND format - stop looking
//          }
//        }
//      }
//      if (found != null) {
//        logger.trace("Found media file: {}", found);
//        mediaFiles.add(found);
//      }
//    }
//    logger.trace("Found {} media files for format: {}", mediaFiles.size(), format);
//    return mediaFiles;
//  }
//
//  private MediaFileBO getMediaFileByFormat(ApiConstants.ResourceFormatEnum format, Set<MediaFileBO> mediaFiles) {
//    logger.trace("Looking for media file with format: {} in {} files", format, mediaFiles.size());
//    // get the type to search for
//    String requestedType = ApiUtils.getMediaFileType(format);
//    for (MediaFileBO mediaFile : mediaFiles) {
//      if (requestedType.equals(mediaFile.getType()) && FilenameUtils.isExtension(mediaFile.getPath(), format.name())) {
//        logger.trace("Found the media file: {}", mediaFile);
//        return mediaFile;
//      }
//    }
//    return null;
//  }
//
//  public InteractionManager getInteractionManager() {
//    return manager;
//  }
//
//  public void setInteractionManager(InteractionManager interactionManager) {
//    this.manager = interactionManager;
//  }
//}
