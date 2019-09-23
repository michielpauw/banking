package com.michiel.football.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class League {
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private long id;

  @ManyToMany
  @JoinTable(name = "league_teams",
      joinColumns = @JoinColumn(name = "league_id"),
      inverseJoinColumns = @JoinColumn(name = "team_id"))
  private List<League> leagueList;

}
