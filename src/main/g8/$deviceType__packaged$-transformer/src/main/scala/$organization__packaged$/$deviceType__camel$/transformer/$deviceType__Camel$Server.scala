package $organization;format="package"$.$deviceType;format="camel"$.transformer

import akka.stream.scaladsl.Sink
import com.cisco.streambed.{ Application, ApplicationContext, ApplicationProcess }
import com.cisco.streambed.durablequeue.chroniclequeue.DurableQueueProvider
import com.cisco.streambed.identity.iox.SecretStoreProvider
import com.cisco.streambed.storage.fs.RawStorageProvider
import com.cisco.streambed.tracing.jaeger.TracerConfig

/**
  * Bootstraps our application and handles signals
  */
object $deviceType;format="Camel"$ServerEntryPoints {
  private val applicationProcess = ApplicationProcess($deviceType;format="Camel"$Server)

  def main(args: Array[String]): Unit =
    applicationProcess.main(args)

  def trap(signal: Int): Unit =
    applicationProcess.trap(signal)
}

/**
  * The $deviceType$ application
  */
object $deviceType;format="Camel"$Server extends Application with DurableQueueProvider with RawStorageProvider with SecretStoreProvider {
  /**
    * Main entry point for the transformer and filter.
    */
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  override def main(args: Array[String], context: ApplicationContext): Unit = {

    import context._

    val tracer = TracerConfig.tracer(config)

    {
      val _ = $deviceType;format="Camel"$MetaFilter
        .source(durableQueue, getSecret, tracer)
        .runWith(Sink.ignore)
    }
    {
      val _ = $deviceType;format="Camel"$Transformer
        .source(durableQueue, getSecret, tracer)
        .runWith(Sink.ignore)
    }
  }
}
