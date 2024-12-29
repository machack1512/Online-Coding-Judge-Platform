<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="playerActivityLogs">
        <h2 class="jh-entity-heading" data-cy="playerActivityLogsDetailsHeading">
          <span v-text="t$('mockRgs2App.playerActivityLogs.detail.title')"></span> {{ playerActivityLogs.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mockRgs2App.playerActivityLogs.playerId')"></span>
          </dt>
          <dd>
            <span>{{ playerActivityLogs.playerId }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerActivityLogs.action')"></span>
          </dt>
          <dd>
            <span>{{ playerActivityLogs.action }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerActivityLogs.beforeBalance')"></span>
          </dt>
          <dd>
            <span>{{ playerActivityLogs.beforeBalance }}</span>
          </dd>
          <dt>
            <span v-text="t$('mockRgs2App.playerActivityLogs.afterBalance')"></span>
          </dt>
          <dd>
            <span>{{ playerActivityLogs.afterBalance }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="playerActivityLogs.id"
          :to="{ name: 'PlayerActivityLogsEdit', params: { playerActivityLogsId: playerActivityLogs.id } }"
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

<script lang="ts" src="./player-activity-logs-details.component.ts"></script>
