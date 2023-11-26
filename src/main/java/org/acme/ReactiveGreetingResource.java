package org.acme;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/hello")
public class ReactiveGreetingResource {

  @Inject
  ReactiveGreetingService service;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/greeting/{name}")
  public Uni<String> greeting(String name) {
    return service.greeting(name);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/greeting/{count}/{name}")
  public Multi<String> greetings(int count, String name) {
    return service.greetings(count, name);
  }

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  @Path("/stream/{count}/{name}")
  public Multi<String> greetingsAsStream(int count, String name) {
    return service.greetings(count, name);
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @NonBlocking
  public String hello() {
    return "hello";
  }
}
