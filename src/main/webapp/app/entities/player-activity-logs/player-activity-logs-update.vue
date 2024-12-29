<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="mockRgs2App.playerActivityLogs.home.createOrEditLabel"
          data-cy="PlayerActivityLogsCreateUpdateHeading"
          v-text="t$('mockRgs2App.playerActivityLogs.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="playerActivityLogs.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="playerActivityLogs.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mockRgs2App.playerActivityLogs.playerId')"
              for="player-activity-logs-playerId"
            ></label>
            <input
              type="text"
              class="form-control"
              name="playerId"
              id="player-activity-logs-playerId"
              data-cy="playerId"
              :class="{ valid: !v$.playerId.$invalid, invalid: v$.playerId.$invalid }"
              v-model="v$.playerId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mockRgs2App.playerActivityLogs.action')"
              for="player-activity-logs-action"
            ></label>
            <input
              type="text"
              class="form-control"
              name="action"
              id="player-activity-logs-action"
              data-cy="action"
              :class="{ valid: !v$.action.$invalid, invalid: v$.action.$invalid }"
              v-model="v$.action.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mockRgs2App.playerActivityLogs.beforeBalance')"
              for="player-activity-logs-beforeBalance"
            ></label>
            <input
              type="number"
              class="form-control"
              name="beforeBalance"
              id="player-activity-logs-beforeBalance"
              data-cy="beforeBalance"
              :class="{ valid: !v$.beforeBalance.$invalid, invalid: v$.beforeBalance.$invalid }"
              v-model.number="v$.beforeBalance.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mockRgs2App.playerActivityLogs.afterBalance')"
              for="player-activity-logs-afterBalance"
            ></label>
            <input
              type="number"
              class="form-control"
              name="afterBalance"
              id="player-activity-logs-afterBalance"
              data-cy="afterBalance"
              :class="{ valid: !v$.afterBalance.$invalid, invalid: v$.afterBalance.$invalid }"
              v-model.number="v$.afterBalance.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./player-activity-logs-update.component.ts"></script>
