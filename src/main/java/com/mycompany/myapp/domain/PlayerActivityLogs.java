package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PlayerActivityLogs.
 */
@Document(collection = "player_activity_logs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlayerActivityLogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("player_id")
    private String playerId;

    @Field("action")
    private String action;

    @Field("before_balance")
    private Float beforeBalance;

    @Field("after_balance")
    private Float afterBalance;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PlayerActivityLogs id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public PlayerActivityLogs playerId(String playerId) {
        this.setPlayerId(playerId);
        return this;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAction() {
        return this.action;
    }

    public PlayerActivityLogs action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Float getBeforeBalance() {
        return this.beforeBalance;
    }

    public PlayerActivityLogs beforeBalance(Float beforeBalance) {
        this.setBeforeBalance(beforeBalance);
        return this;
    }

    public void setBeforeBalance(Float beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public Float getAfterBalance() {
        return this.afterBalance;
    }

    public PlayerActivityLogs afterBalance(Float afterBalance) {
        this.setAfterBalance(afterBalance);
        return this;
    }

    public void setAfterBalance(Float afterBalance) {
        this.afterBalance = afterBalance;
    }




    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerActivityLogs)) {
            return false;
        }
        return getId() != null && getId().equals(((PlayerActivityLogs) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerActivityLogs{" +
            "id=" + getId() +
            ", playerId='" + getPlayerId() + "'" +
            ", action='" + getAction() + "'" +
            ", beforeBalance=" + getBeforeBalance() +
            ", afterBalance=" + getAfterBalance() +
            "}";
    }
}
