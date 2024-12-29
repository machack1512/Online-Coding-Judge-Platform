package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Playerinfo.
 */
@Document(collection = "playerinfo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Playerinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("player_id")
    private String playerId;

    @Field("player_name")
    private String playerName;

    @Field("balance")
    private Float balance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Playerinfo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public Playerinfo playerId(String playerId) {
        this.setPlayerId(playerId);
        return this;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Playerinfo playerName(String playerName) {
        this.setPlayerName(playerName);
        return this;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Float getBalance() {
        return this.balance;
    }

    public Playerinfo balance(Float balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Playerinfo)) {
            return false;
        }
        return getId() != null && getId().equals(((Playerinfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Playerinfo{" +
            "id=" + getId() +
            ", playerId='" + getPlayerId() + "'" +
            ", playerName='" + getPlayerName() + "'" +
            ", balance=" + getBalance() +
            "}";
    }
}
