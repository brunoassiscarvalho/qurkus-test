package org.acme.orcledb;

import static jakarta.ws.rs.core.Response.Status.CREATED;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/oracledb")
@Produces("application/json")
@Consumes("application/json")
public class OracledbResource {

  @GET
  public Uni<List<ItemTestRepository>> list() {
    return ItemTestRepository.listAll(Sort.by("name"));
  }

  @GET
  @Path("{id}")
  public Uni<ItemTestRepository> getSingle(Long id) {
    return ItemTestRepository.findById(id);
  }

  @POST
  public Uni<Response> create(ItemTestRepository item) {
    if (item == null || item.id != null) {
      throw new WebApplicationException(
        "Id was invalidly set on request.",
        422
      );
    }

    return Panache
      .withTransaction(item::persist)
      .replaceWith(Response.ok(item).status(CREATED)::build);
  }
}
