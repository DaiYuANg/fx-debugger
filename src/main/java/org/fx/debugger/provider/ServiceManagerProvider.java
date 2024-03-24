package org.fx.debugger.provider;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ServiceManagerProvider implements Provider<ServiceManager> {

  private final Set<Service> services;

  @Override
  public ServiceManager get() {
    return new ServiceManager(services);
  }
}
