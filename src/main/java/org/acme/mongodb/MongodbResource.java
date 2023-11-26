package org.acme.mongodb;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/mongodb")
@Produces(MediaType.APPLICATION_JSON)
public class MongodbResource {

  @Inject
  MongodbService mongoService;

  @GET
  public Uni<List<ItemTest>> list() {
    return mongoService.list();
  }

  @POST
  public Uni<List<ItemTest>> add(ItemTest itemTest) {
    return mongoService.add(itemTest).onItem().ignore().andSwitchTo(this::list);
  }
}
