package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Playerinfo;
import com.mycompany.myapp.repository.PlayerinfoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Playerinfo}.
 */
@RestController
@RequestMapping("/api/playerinfos")
public class PlayerinfoResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerinfoResource.class);

    private static final String ENTITY_NAME = "playerinfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerinfoRepository playerinfoRepository;

    public PlayerinfoResource(PlayerinfoRepository playerinfoRepository) {
        this.playerinfoRepository = playerinfoRepository;
    }

    /**
     * {@code POST  /playerinfos} : Create a new playerinfo.
     *
     * @param playerinfo the playerinfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerinfo, or with status {@code 400 (Bad Request)} if the playerinfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Playerinfo> createPlayerinfo(@RequestBody Playerinfo playerinfo) throws URISyntaxException {
        LOG.debug("REST request to save Playerinfo : {}", playerinfo);
        if (playerinfo.getId() != null) {
            throw new BadRequestAlertException("A new playerinfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        playerinfo.setId("player_" + playerinfo.getPlayerId());
        playerinfo = playerinfoRepository.save(playerinfo);
        playerinfo.setId("player_" + playerinfo.getPlayerId());
        return ResponseEntity.created(new URI("/api/playerinfos/" + playerinfo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, playerinfo.getId()))
            .body(playerinfo);
    }

    /**
     * {@code PUT  /playerinfos/:id} : Updates an existing playerinfo.
     *
     * @param id the id of the playerinfo to save.
     * @param playerinfo the playerinfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerinfo,
     * or with status {@code 400 (Bad Request)} if the playerinfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerinfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Playerinfo> updatePlayerinfo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Playerinfo playerinfo
    ) throws URISyntaxException {
        LOG.debug("REST request to update Playerinfo : {}, {}", id, playerinfo);
        if (playerinfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerinfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        playerinfo = playerinfoRepository.save(playerinfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerinfo.getId()))
            .body(playerinfo);
    }

    /**
     * {@code PATCH  /playerinfos/:id} : Partial updates given fields of an existing playerinfo, field will ignore if it is null
     *
     * @param id the id of the playerinfo to save.
     * @param playerinfo the playerinfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerinfo,
     * or with status {@code 400 (Bad Request)} if the playerinfo is not valid,
     * or with status {@code 404 (Not Found)} if the playerinfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the playerinfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Playerinfo> partialUpdatePlayerinfo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Playerinfo playerinfo
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Playerinfo partially : {}, {}", id, playerinfo);
        if (playerinfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerinfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerinfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Playerinfo> result = playerinfoRepository
            .findById(playerinfo.getId())
            .map(existingPlayerinfo -> {
                if (playerinfo.getPlayerId() != null) {
                    existingPlayerinfo.setPlayerId(playerinfo.getPlayerId());
                }
                if (playerinfo.getPlayerName() != null) {
                    existingPlayerinfo.setPlayerName(playerinfo.getPlayerName());
                }
                if (playerinfo.getBalance() != null) {
                    existingPlayerinfo.setBalance(playerinfo.getBalance());
                }

                return existingPlayerinfo;
            })
            .map(playerinfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, playerinfo.getId())
        );
    }

    /**
     * {@code GET  /playerinfos} : get all the playerinfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerinfos in body.
     */
    @GetMapping("")
    public List<Playerinfo> getAllPlayerinfos() {
        LOG.debug("REST request to get all Playerinfos");
        return playerinfoRepository.findAll();
    }

    /**
     * {@code GET  /playerinfos/:id} : get the "id" playerinfo.
     *
     * @param id the id of the playerinfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerinfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Playerinfo> getPlayerinfo(@PathVariable("id") String id) {
        LOG.debug("REST request to get Playerinfo : {}", id);
        Optional<Playerinfo> playerinfo = playerinfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(playerinfo);
    }

    /**
     * {@code DELETE  /playerinfos/:id} : delete the "id" playerinfo.
     *
     * @param id the id of the playerinfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerinfo(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Playerinfo : {}", id);
        playerinfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
