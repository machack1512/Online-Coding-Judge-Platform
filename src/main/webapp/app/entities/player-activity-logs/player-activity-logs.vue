<template>
  <div>
    <h2 id="page-heading" data-cy="PlayerActivityLogsHeading">
      <span v-text="t$('mockRgs2App.playerActivityLogs.home.title')" id="player-activity-logs-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mockRgs2App.playerActivityLogs.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PlayerActivityLogsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-player-activity-logs"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mockRgs2App.playerActivityLogs.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && playerActivityLogs && playerActivityLogs.length === 0">
      <span v-text="t$('mockRgs2App.playerActivityLogs.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="playerActivityLogs && playerActivityLogs.length > 0">
      <table class="table table-striped" aria-describedby="playerActivityLogs">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerActivityLogs.playerId')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerActivityLogs.action')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerActivityLogs.beforeBalance')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerActivityLogs.afterBalance')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="playerActivityLogs in playerActivityLogs" :key="playerActivityLogs.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PlayerActivityLogsView', params: { playerActivityLogsId: playerActivityLogs.id } }">{{
                playerActivityLogs.id
              }}</router-link>
            </td>
            <td>{{ playerActivityLogs.playerId }}</td>
            <td>{{ playerActivityLogs.action }}</td>
            <td>{{ playerActivityLogs.beforeBalance }}</td>
            <td>{{ playerActivityLogs.afterBalance }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PlayerActivityLogsView', params: { playerActivityLogsId: playerActivityLogs.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PlayerActivityLogsEdit', params: { playerActivityLogsId: playerActivityLogs.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(playerActivityLogs)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="mockRgs2App.playerActivityLogs.delete.question"
          data-cy="playerActivityLogsDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-playerActivityLogs-heading" v-text="t$('mockRgs2App.playerActivityLogs.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-playerActivityLogs"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removePlayerActivityLogs()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./player-activity-logs.component.ts"></script>
