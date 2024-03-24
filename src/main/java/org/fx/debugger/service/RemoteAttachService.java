package org.fx.debugger.service;

import com.google.common.util.concurrent.AbstractIdleService;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class RemoteAttachService extends AbstractIdleService implements AttachService {

  @Override
  protected void startUp() throws Exception {
    log.info("Remote Attach Service Started");
  }

  @Override
  protected void shutDown() throws Exception {}
}
