package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PlayerActivityLogsAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PlayerActivityLogs;
import com.mycompany.myapp.repository.PlayerActivityLogsRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PlayerActivityLogsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerActivityLogsResourceIT {

    private static final String DEFAULT_PLAYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_BEFORE_BALANCE = 1;
    private static final Integer UPDATED_BEFORE_BALANCE = 2;

    private static final Integer DEFAULT_AFTER_BALANCE = 1;
    private static final Integer UPDATED_AFTER_BALANCE = 2;

    private static final String ENTITY_API_URL = "/api/player-activity-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlayerActivityLogsRepository playerActivityLogsRepository;

    @Autowired
    private MockMvc restPlayerActivityLogsMockMvc;

    private PlayerActivityLogs playerActivityLogs;

    private PlayerActivityLogs insertedPlayerActivityLogs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerActivityLogs createEntity() {
        return new PlayerActivityLogs()
            .playerId(DEFAULT_PLAYER_ID)
            .action(DEFAULT_ACTION)
            .beforeBalance(Float.valueOf(DEFAULT_BEFORE_BALANCE))
            .afterBalance(Float.valueOf(DEFAULT_AFTER_BALANCE));
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerActivityLogs createUpdatedEntity() {
        return new PlayerActivityLogs()
            .playerId(UPDATED_PLAYER_ID)
            .action(UPDATED_ACTION)
            .beforeBalance(Float.valueOf(UPDATED_BEFORE_BALANCE))
            .afterBalance(Float.valueOf(UPDATED_AFTER_BALANCE));
    }

    @BeforeEach
    public void initTest() {
        playerActivityLogs = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlayerActivityLogs != null) {
            playerActivityLogsRepository.delete(insertedPlayerActivityLogs);
            insertedPlayerActivityLogs = null;
        }
    }

    @Test
    void createPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlayerActivityLogs
        var returnedPlayerActivityLogs = om.readValue(
            restPlayerActivityLogsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerActivityLogs)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlayerActivityLogs.class
        );

        // Validate the PlayerActivityLogs in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlayerActivityLogsUpdatableFieldsEquals(
            returnedPlayerActivityLogs,
            getPersistedPlayerActivityLogs(returnedPlayerActivityLogs)
        );

        insertedPlayerActivityLogs = returnedPlayerActivityLogs;
    }

    @Test
    void createPlayerActivityLogsWithExistingId() throws Exception {
        // Create the PlayerActivityLogs with an existing ID
        playerActivityLogs.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerActivityLogsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerActivityLogs)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPlayerActivityLogs() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        // Get all the playerActivityLogsList
        restPlayerActivityLogsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerActivityLogs.getId())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].beforeBalance").value(hasItem(DEFAULT_BEFORE_BALANCE)))
            .andExpect(jsonPath("$.[*].afterBalance").value(hasItem(DEFAULT_AFTER_BALANCE)));
    }

    @Test
    void getPlayerActivityLogs() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        // Get the playerActivityLogs
        restPlayerActivityLogsMockMvc
            .perform(get(ENTITY_API_URL_ID, playerActivityLogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playerActivityLogs.getId()))
            .andExpect(jsonPath("$.playerId").value(DEFAULT_PLAYER_ID))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.beforeBalance").value(DEFAULT_BEFORE_BALANCE))
            .andExpect(jsonPath("$.afterBalance").value(DEFAULT_AFTER_BALANCE));
    }

    @Test
    void getNonExistingPlayerActivityLogs() throws Exception {
        // Get the playerActivityLogs
        restPlayerActivityLogsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPlayerActivityLogs() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerActivityLogs
        PlayerActivityLogs updatedPlayerActivityLogs = playerActivityLogsRepository.findById(playerActivityLogs.getId()).orElseThrow();
        updatedPlayerActivityLogs
            .playerId(UPDATED_PLAYER_ID)
            .action(UPDATED_ACTION)
            .beforeBalance(Float.valueOf(UPDATED_BEFORE_BALANCE))
            .afterBalance(Float.valueOf(UPDATED_AFTER_BALANCE));

        restPlayerActivityLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlayerActivityLogs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlayerActivityLogs))
            )
            .andExpect(status().isOk());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlayerActivityLogsToMatchAllProperties(updatedPlayerActivityLogs);
    }

    @Test
    void putNonExistingPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerActivityLogs.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playerActivityLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playerActivityLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerActivityLogs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlayerActivityLogsWithPatch() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerActivityLogs using partial update
        PlayerActivityLogs partialUpdatedPlayerActivityLogs = new PlayerActivityLogs();
        partialUpdatedPlayerActivityLogs.setId(playerActivityLogs.getId());

        partialUpdatedPlayerActivityLogs.afterBalance(Float.valueOf(UPDATED_AFTER_BALANCE));

        restPlayerActivityLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerActivityLogs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlayerActivityLogs))
            )
            .andExpect(status().isOk());

        // Validate the PlayerActivityLogs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlayerActivityLogsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlayerActivityLogs, playerActivityLogs),
            getPersistedPlayerActivityLogs(playerActivityLogs)
        );
    }

    @Test
    void fullUpdatePlayerActivityLogsWithPatch() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerActivityLogs using partial update
        PlayerActivityLogs partialUpdatedPlayerActivityLogs = new PlayerActivityLogs();
        partialUpdatedPlayerActivityLogs.setId(playerActivityLogs.getId());

        partialUpdatedPlayerActivityLogs
            .playerId(UPDATED_PLAYER_ID)
            .action(UPDATED_ACTION)
            .beforeBalance(Float.valueOf(UPDATED_BEFORE_BALANCE))
            .afterBalance(Float.valueOf(UPDATED_AFTER_BALANCE));

        restPlayerActivityLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerActivityLogs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlayerActivityLogs))
            )
            .andExpect(status().isOk());

        // Validate the PlayerActivityLogs in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlayerActivityLogsUpdatableFieldsEquals(
            partialUpdatedPlayerActivityLogs,
            getPersistedPlayerActivityLogs(partialUpdatedPlayerActivityLogs)
        );
    }

    @Test
    void patchNonExistingPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerActivityLogs.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playerActivityLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playerActivityLogs))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlayerActivityLogs() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerActivityLogs.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerActivityLogsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(playerActivityLogs)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerActivityLogs in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlayerActivityLogs() throws Exception {
        // Initialize the database
        insertedPlayerActivityLogs = playerActivityLogsRepository.save(playerActivityLogs);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the playerActivityLogs
        restPlayerActivityLogsMockMvc
            .perform(delete(ENTITY_API_URL_ID, playerActivityLogs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return playerActivityLogsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected PlayerActivityLogs getPersistedPlayerActivityLogs(PlayerActivityLogs playerActivityLogs) {
        return playerActivityLogsRepository.findById(playerActivityLogs.getId()).orElseThrow();
    }

    protected void assertPersistedPlayerActivityLogsToMatchAllProperties(PlayerActivityLogs expectedPlayerActivityLogs) {
        assertPlayerActivityLogsAllPropertiesEquals(expectedPlayerActivityLogs, getPersistedPlayerActivityLogs(expectedPlayerActivityLogs));
    }

    protected void assertPersistedPlayerActivityLogsToMatchUpdatableProperties(PlayerActivityLogs expectedPlayerActivityLogs) {
        assertPlayerActivityLogsAllUpdatablePropertiesEquals(
            expectedPlayerActivityLogs,
            getPersistedPlayerActivityLogs(expectedPlayerActivityLogs)
        );
    }
}
