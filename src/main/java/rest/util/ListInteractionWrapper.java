//package rest.util;
//
//import cz.zoom.scorecard.business.bo.interaction.InteractionBO;
//
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//import java.util.List;
//
//@XmlRootElement(name = "interaction")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class ListInteractionWrapper {
//  @XmlElement(name = "couple")
//  private List<InteractionBO> couples;
//
//  /**
//   * No-argument constructor is required by JAXB
//   */
//  public ListInteractionWrapper() {
//  }
//
//  public ListInteractionWrapper(List<InteractionBO> couples) {
//    this.couples = couples;
//  }
//
//  public List<InteractionBO> getCouples() {
//    return couples;
//  }
//
//
//  public void setCouples(List<InteractionBO> couples) {
//    this.couples = couples;
//  }
//
//  @Override
//  public boolean equals(Object other) {
//    if (this == other) {
//      return true;
//    }
//    return this.getCouples() != null
//            && (other instanceof ListInteractionWrapper)
//            && this.getCouples().equals(((ListInteractionWrapper) other).getCouples());
//  }
//
//  @Override
//  public int hashCode() {
//    return couples != null ? couples.hashCode() : 0;
//  }
//}
