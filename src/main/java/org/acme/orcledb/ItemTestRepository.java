package org.acme.orcledb;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class ItemTestRepository extends PanacheEntity {

  @Column(length = 40)
  public String name;

  @Column(length = 255)
  private String description;

  @Column(length = 40)
  private String dataBase;
}
