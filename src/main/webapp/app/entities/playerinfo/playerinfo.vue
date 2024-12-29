<template>
  <div>
    <h2 id="page-heading" data-cy="PlayerinfoHeading">
      <span v-text="t$('mockRgs2App.playerinfo.home.title')" id="playerinfo-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mockRgs2App.playerinfo.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PlayerinfoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-playerinfo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mockRgs2App.playerinfo.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && playerinfos && playerinfos.length === 0">
      <span v-text="t$('mockRgs2App.playerinfo.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="playerinfos && playerinfos.length > 0">
      <table class="table table-striped" aria-describedby="playerinfos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerinfo.playerId')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerinfo.playerName')"></span></th>
            <th scope="row"><span v-text="t$('mockRgs2App.playerinfo.balance')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="playerinfo in playerinfos" :key="playerinfo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PlayerinfoView', params: { playerinfoId: playerinfo.id } }">{{ playerinfo.id }}</router-link>
            </td>
            <td>{{ playerinfo.playerId }}</td>
            <td>{{ playerinfo.playerName }}</td>
            <td>{{ playerinfo.balance }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PlayerinfoView', params: { playerinfoId: playerinfo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PlayerinfoEdit', params: { playerinfoId: playerinfo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(playerinfo)"
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
        <span id="mockRgs2App.playerinfo.delete.question" data-cy="playerinfoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-playerinfo-heading" v-text="t$('mockRgs2App.playerinfo.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-playerinfo"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removePlayerinfo()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./playerinfo.component.ts"></script>
