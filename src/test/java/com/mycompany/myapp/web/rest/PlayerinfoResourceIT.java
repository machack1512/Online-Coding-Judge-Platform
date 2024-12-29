package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PlayerinfoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Playerinfo;
import com.mycompany.myapp.repository.PlayerinfoRepository;
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
 * Integration tests for the {@link PlayerinfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerinfoResourceIT {

    private static final String DEFAULT_PLAYER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BALANCE = 1;
    private static final Integer UPDATED_BALANCE = 2;

    private static final String ENTITY_API_URL = "/api/playerinfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlayerinfoRepository playerinfoRepository;

    @Autowired
    private MockMvc restPlayerinfoMockMvc;

    private Playerinfo playerinfo;

    private Playerinfo insertedPlayerinfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playerinfo createEntity() {
        return new Playerinfo().playerId(DEFAULT_PLAYER_ID).playerName(DEFAULT_PLAYER_NAME).balance(Float.valueOf(DEFAULT_BALANCE));
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Playerinfo createUpdatedEntity() {
        return new Playerinfo().playerId(UPDATED_PLAYER_ID).playerName(UPDATED_PLAYER_NAME).balance(Float.valueOf(UPDATED_BALANCE));
    }

    @BeforeEach
    public void initTest() {
        playerinfo = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlayerinfo != null) {
            playerinfoRepository.delete(insertedPlayerinfo);
            insertedPlayerinfo = null;
        }
    }

    @Test
    void createPlayerinfo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Playerinfo
        var returnedPlayerinfo = om.readValue(
            restPlayerinfoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerinfo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Playerinfo.class
        );

        // Validate the Playerinfo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlayerinfoUpdatableFieldsEquals(returnedPlayerinfo, getPersistedPlayerinfo(returnedPlayerinfo));

        insertedPlayerinfo = returnedPlayerinfo;
    }

    @Test
    void createPlayerinfoWithExistingId() throws Exception {
        // Create the Playerinfo with an existing ID
        playerinfo.setId("existing_id");

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerinfo)))
            .andExpect(status().isBadRequest());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPlayerinfos() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        // Get all the playerinfoList
        restPlayerinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerinfo.getId())))
            .andExpect(jsonPath("$.[*].playerId").value(hasItem(DEFAULT_PLAYER_ID)))
            .andExpect(jsonPath("$.[*].playerName").value(hasItem(DEFAULT_PLAYER_NAME)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE)));
    }

    @Test
    void getPlayerinfo() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        // Get the playerinfo
        restPlayerinfoMockMvc
            .perform(get(ENTITY_API_URL_ID, playerinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playerinfo.getId()))
            .andExpect(jsonPath("$.playerId").value(DEFAULT_PLAYER_ID))
            .andExpect(jsonPath("$.playerName").value(DEFAULT_PLAYER_NAME))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE));
    }

    @Test
    void getNonExistingPlayerinfo() throws Exception {
        // Get the playerinfo
        restPlayerinfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPlayerinfo() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerinfo
        Playerinfo updatedPlayerinfo = playerinfoRepository.findById(playerinfo.getId()).orElseThrow();
        updatedPlayerinfo.playerId(UPDATED_PLAYER_ID).playerName(UPDATED_PLAYER_NAME).balance(Float.valueOf(UPDATED_BALANCE));

        restPlayerinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlayerinfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlayerinfo))
            )
            .andExpect(status().isOk());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlayerinfoToMatchAllProperties(updatedPlayerinfo);
    }

    @Test
    void putNonExistingPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerinfo.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerinfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(playerinfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(playerinfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlayerinfoWithPatch() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerinfo using partial update
        Playerinfo partialUpdatedPlayerinfo = new Playerinfo();
        partialUpdatedPlayerinfo.setId(playerinfo.getId());

        partialUpdatedPlayerinfo.balance(Float.valueOf(UPDATED_BALANCE));

        restPlayerinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlayerinfo))
            )
            .andExpect(status().isOk());

        // Validate the Playerinfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlayerinfoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlayerinfo, playerinfo),
            getPersistedPlayerinfo(playerinfo)
        );
    }

    @Test
    void fullUpdatePlayerinfoWithPatch() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the playerinfo using partial update
        Playerinfo partialUpdatedPlayerinfo = new Playerinfo();
        partialUpdatedPlayerinfo.setId(playerinfo.getId());

        partialUpdatedPlayerinfo.playerId(UPDATED_PLAYER_ID).playerName(UPDATED_PLAYER_NAME).balance(Float.valueOf(UPDATED_BALANCE));

        restPlayerinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlayerinfo))
            )
            .andExpect(status().isOk());

        // Validate the Playerinfo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlayerinfoUpdatableFieldsEquals(partialUpdatedPlayerinfo, getPersistedPlayerinfo(partialUpdatedPlayerinfo));
    }

    @Test
    void patchNonExistingPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playerinfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(playerinfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlayerinfo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        playerinfo.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerinfoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(playerinfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Playerinfo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlayerinfo() throws Exception {
        // Initialize the database
        insertedPlayerinfo = playerinfoRepository.save(playerinfo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the playerinfo
        restPlayerinfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, playerinfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return playerinfoRepository.count();
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

    protected Playerinfo getPersistedPlayerinfo(Playerinfo playerinfo) {
        return playerinfoRepository.findById(playerinfo.getId()).orElseThrow();
    }

    protected void assertPersistedPlayerinfoToMatchAllProperties(Playerinfo expectedPlayerinfo) {
        assertPlayerinfoAllPropertiesEquals(expectedPlayerinfo, getPersistedPlayerinfo(expectedPlayerinfo));
    }

    protected void assertPersistedPlayerinfoToMatchUpdatableProperties(Playerinfo expectedPlayerinfo) {
        assertPlayerinfoAllUpdatablePropertiesEquals(expectedPlayerinfo, getPersistedPlayerinfo(expectedPlayerinfo));
    }
}
