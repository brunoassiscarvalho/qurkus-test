package org.acme.mongodb;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.bson.Document;

@ApplicationScoped
public class MongodbService {

  @Inject
  ReactiveMongoClient mongoClient;

  public Uni<List<ItemTest>> list() {
    return getCollection()
      .find()
      .map(doc -> {
        ItemTest itemTest = new ItemTest();
        itemTest.setName(doc.getString("name"));
        itemTest.setDescription(doc.getString("description"));
        itemTest.setDataBase(doc.getString("dataBase"));
        return itemTest;
      })
      .collect()
      .asList();
  }

  public Uni<Void> add(ItemTest itemTest) {
    Document document = new Document()
      .append("name", itemTest.getName())
      .append("description", itemTest.getDescription())
      .append("dataBase", itemTest.getDataBase());
    return getCollection()
      .insertOne(document)
      .onItem()
      .ignore()
      .andContinueWithNull();
  }

  private ReactiveMongoCollection<Document> getCollection() {
    return mongoClient.getDatabase("quarkus-test").getCollection("ItemTest");
  }
}
