//package com.ibatis.scorecardmodel.bo.interaction;
//
//import cz.zoom.scorecard.business.app.exception.InvalidConfigurationException;
//import cz.zoom.scorecard.business.services.ExtDataSearchConfigService;
//import cz.zoom.util.configuration.ConfigurationException;
//import cz.zoom.util.configuration.config.mapping.AbsRootElement;
//import cz.zoom.util.configuration.config.mapping.Configuration;
//import cz.zoom.util.configuration.config.service.ConfigurationManager;
//import cz.zoom.util.configuration.config.service.NotifyConfigurationInfo;
//import cz.zoom.util.observer.RemoteObserver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.rmi.RemoteException;
//
///**
// * Stanislav Valenta, 9/30/13
// */
//@Component
//public class ExtDataSearchConfigObserver extends RemoteObserver<NotifyConfigurationInfo> {
//  private static final long serialVersionUID = -2864009203262651741L;
//  private static final Logger LOG = LoggerFactory.getLogger(ExtDataSearchConfigObserver.class);
//
//  private final ExtDataSearchConfigService searchConfigService;
//  private final ConfigurationManager configurationManager;
//
//  @Autowired
//  public ExtDataSearchConfigObserver(ExtDataSearchConfigService searchConfigService,
//                                     @Qualifier("callrecConfManager") ConfigurationManager configurationManager)
//          throws RemoteException {
//    super();
//    this.configurationManager = configurationManager;
//    this.searchConfigService = searchConfigService;
//  }
//
//  @PostConstruct
//  public void initConfiguration() throws ConfigurationException, RemoteException {
//    configurationManager.addObserver(this, "search");
//    notifyConfigChanged(configurationManager.getConfiguration());
//  }
//
//  @Override
//  public void remoteUpdate(NotifyConfigurationInfo obj) throws RemoteException {
//    AbsRootElement config = obj.getConfiguration();
//    notifyConfigChanged(config.getConfiguration());
//  }
//
//  private void notifyConfigChanged(Configuration configuration) {
//    searchConfigService.setConfiguration(configuration);
//    try {
//      searchConfigService.getExtDataSearchConfigs();
//    } catch (InvalidConfigurationException e) {
//      LOG.error("Error (re)loading 'Ext. data search' configurations.", e);
//    }
//  }
//}
