package org.fx.debugger;

import static java.lang.System.exit;

import com.google.common.util.concurrent.ServiceManager;
import javafx.application.Application;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.context.DIContext;
import org.fx.debugger.view.ScenicView;
import picocli.CommandLine;

@CommandLine.Command(name = "Visual", mixinStandardHelpOptions = true, helpCommand = true)
@Slf4j
@RequiredArgsConstructor
public class FxDebugger implements Runnable {

  private final String[] args;

  @Override
  public void run() {
    Thread.ofVirtual()
        .name("Bootstrap-", 0)
        .start(
            () -> {
              val serviceManager = DIContext.INSTANCE.get(ServiceManager.class);
              serviceManager.startAsync();
            });
    Application.launch(ScenicView.class, args);
  }

  @SneakyThrows
  public static void main(String[] args) {
    log.atDebug().log("Visual Start");
    val commandLine = new FxDebugger(args);
    val exitCode = new CommandLine(commandLine).execute(args);
    exit(exitCode);
  }
}
