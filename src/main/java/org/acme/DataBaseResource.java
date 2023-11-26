package org.acme;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import org.acme.mongodb.ItemTest;
import org.acme.mongodb.MongodbService;
import org.acme.orcledb.ItemTestRepository;

@Path("/database")
public class DataBaseResource {

  @Inject
  MongodbService mongoService;

  @GET
  public Uni<List<ItemTest>> getAll() {
    Uni<List<ItemTest>> item = ItemTestRepository
      .listAll(Sort.by("name")).onItem().transformToUni()

     

    return Uni.combine().all().unis(item, mongoService.list()).asTuple();
  }

  private List<ItemTest> converterItemTestRepositoryToItemTest(
    List<ItemTestRepository> itemsTestRepository
  ) {
    return itemsTestRepository
      .stream()
      .map(itemTestRepository -> {
        ItemTest itemTest = new ItemTest();
        itemTest.setDataBase(itemTestRepository.getDataBase());
        itemTest.setDescription(itemTestRepository.getDescription());
        itemTest.setName(itemTestRepository.getName());
        return itemTest;
      })
      .toList();
  }
}
