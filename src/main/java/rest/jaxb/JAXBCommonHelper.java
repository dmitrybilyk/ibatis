package rest.jaxb;

import com.ibatis.scorecardmodel.bo.question.SMediaFileBO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Common helper for other JAXB helpers.
 */
public class JAXBCommonHelper {

  /**
   * Creates a new {@link java.util.SortedSet} from a specified collection.
   * The new set will use the specified comparator to order the elements.
   * It can be subsequently modified and the items will remain in the order specified by the comparator.
   *
   * @param collection a collection of items to fill the new set with (can be null for no items)
   * @param comparator a comparator to order the items by (can be null for natural ordering)
   * @return a new {@link java.util.SortedSet} filled with specified items order by the specified comparator
   */
  @NotNull
  public <T> SortedSet<T> asSortedSet(@Nullable Collection<T> collection, @Nullable Comparator<T> comparator) {
    SortedSet<T> answerBOsSorted = new TreeSet<T>(comparator);
    if (collection != null) {
      answerBOsSorted.addAll(collection);
    }
    return answerBOsSorted;
  }

  @Nullable
  public SMediaFileBO convertFromJaxbMediaFile(@Nullable MediaFile mediaFile) {
    if (mediaFile == null) {
      return null;
    }
    SMediaFileBO sMediaFileBO = new SMediaFileBO();
    sMediaFileBO.setPath(mediaFile.getPath());
    sMediaFileBO.setType(mediaFile.getType());
    sMediaFileBO.setContentType(mediaFile.getContentType());
    return sMediaFileBO;
  }

  @Nullable
  public Date toDate(@Nullable Calendar calendar) {
    return calendar == null ? null : calendar.getTime();
  }
}
