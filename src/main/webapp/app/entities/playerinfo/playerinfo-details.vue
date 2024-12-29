<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="playerinfo">
        <h2 class="jh-entity-heading" data-cy="playerinfoDetailsHeading">
          <span v-text="t$('mockRgs2App.playerinfo.detail.title')"></span> {{ playerinfo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mockRgs2App.playerinfo.playerId')"></span>
          </dt>
          <dd>
            <span>{{ playerinfo.playerId }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerinfo.playerName')"></span>
          </dt>
          <dd>
            <span>{{ playerinfo.playerName }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerinfo.balance')"></span>
          </dt>
          <dd>
            <span>{{ playerinfo.balance }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerinfo.playerActivityLogs')"></span>
          </dt>
          <dd>
            <div v-if="playerinfo.playerActivityLogs">
              <router-link :to="{ name: 'PlayerActivityLogsView', params: { playerActivityLogsId: playerinfo.playerActivityLogs.id } }">{{
                playerinfo.playerActivityLogs.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="playerinfo.id"
          :to="{ name: 'PlayerinfoEdit', params: { playerinfoId: playerinfo.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./playerinfo-details.component.ts"></script>
