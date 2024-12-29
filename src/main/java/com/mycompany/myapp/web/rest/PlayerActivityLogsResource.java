package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PlayerActivityLogs;
import com.mycompany.myapp.repository.PlayerActivityLogsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PlayerActivityLogs}.
 */
@RestController
@RequestMapping("/api/player-activity-logs")
public class PlayerActivityLogsResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerActivityLogsResource.class);

    private static final String ENTITY_NAME = "playerActivityLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerActivityLogsRepository playerActivityLogsRepository;

    public PlayerActivityLogsResource(PlayerActivityLogsRepository playerActivityLogsRepository) {
        this.playerActivityLogsRepository = playerActivityLogsRepository;
    }

    /**
     * {@code POST  /player-activity-logs} : Create a new playerActivityLogs.
     *
     * @param playerActivityLogs the playerActivityLogs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerActivityLogs, or with status {@code 400 (Bad Request)} if the playerActivityLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlayerActivityLogs> createPlayerActivityLogs(@RequestBody PlayerActivityLogs playerActivityLogs)
        throws URISyntaxException {
        LOG.debug("REST request to save PlayerActivityLogs : {}", playerActivityLogs);
//        if (playerActivityLogs.getId() != null) {
//            throw new BadRequestAlertException("A new playerActivityLogs cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        playerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);
        return ResponseEntity.created(new URI("/api/player-activity-logs/" + playerActivityLogs.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, playerActivityLogs.getId()))
            .body(playerActivityLogs);
    }

    /**
     * {@code PUT  /player-activity-logs/:id} : Updates an existing playerActivityLogs.
     *
     * @param id the id of the playerActivityLogs to save.
     * @param playerActivityLogs the playerActivityLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerActivityLogs,
     * or with status {@code 400 (Bad Request)} if the playerActivityLogs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerActivityLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlayerActivityLogs> updatePlayerActivityLogs(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PlayerActivityLogs playerActivityLogs
    ) throws URISyntaxException {
        LOG.debug("REST request to update PlayerActivityLogs : {}, {}", id, playerActivityLogs);
        if (playerActivityLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerActivityLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerActivityLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        playerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerActivityLogs.getId()))
            .body(playerActivityLogs);
    }

    /**
     * {@code PATCH  /player-activity-logs/:id} : Partial updates given fields of an existing playerActivityLogs, field will ignore if it is null
     *
     * @param id the id of the playerActivityLogs to save.
     * @param playerActivityLogs the playerActivityLogs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerActivityLogs,
     * or with status {@code 400 (Bad Request)} if the playerActivityLogs is not valid,
     * or with status {@code 404 (Not Found)} if the playerActivityLogs is not found,
     * or with status {@code 500 (Internal Server Error)} if the playerActivityLogs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlayerActivityLogs> partialUpdatePlayerActivityLogs(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody PlayerActivityLogs playerActivityLogs
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PlayerActivityLogs partially : {}, {}", id, playerActivityLogs);
        if (playerActivityLogs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerActivityLogs.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerActivityLogsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlayerActivityLogs> result = playerActivityLogsRepository
            .findById(playerActivityLogs.getId())
            .map(existingPlayerActivityLogs -> {
                if (playerActivityLogs.getPlayerId() != null) {
                    existingPlayerActivityLogs.setPlayerId(playerActivityLogs.getPlayerId());
                }
                if (playerActivityLogs.getAction() != null) {
                    existingPlayerActivityLogs.setAction(playerActivityLogs.getAction());
                }
                if (playerActivityLogs.getBeforeBalance() != null) {
                    existingPlayerActivityLogs.setBeforeBalance(playerActivityLogs.getBeforeBalance());
                }
                if (playerActivityLogs.getAfterBalance() != null) {
                    existingPlayerActivityLogs.setAfterBalance(playerActivityLogs.getAfterBalance());
                }

                return existingPlayerActivityLogs;
            })
            .map(playerActivityLogsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerActivityLogs.getId())
        );
    }

    /**
     * {@code GET  /player-activity-logs} : get all the playerActivityLogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerActivityLogs in body.
     */
    @GetMapping("")
    public List<PlayerActivityLogs> getAllPlayerActivityLogs() {
        LOG.debug("REST request to get all PlayerActivityLogs");
        return playerActivityLogsRepository.findAll();
    }

    /**
     * {@code GET  /player-activity-logs/:id} : get the "id" playerActivityLogs.
     *
     * @param id the id of the playerActivityLogs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerActivityLogs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlayerActivityLogs> getPlayerActivityLogs(@PathVariable("id") String id) {
        LOG.debug("REST request to get PlayerActivityLogs : {}", id);
        Optional<PlayerActivityLogs> playerActivityLogs = playerActivityLogsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerActivityLogs);
    }

    /**
     * {@code DELETE  /player-activity-logs/:id} : delete the "id" playerActivityLogs.
     *
     * @param id the id of the playerActivityLogs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerActivityLogs(@PathVariable("id") String id) {
        LOG.debug("REST request to delete PlayerActivityLogs : {}", id);
        playerActivityLogsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
