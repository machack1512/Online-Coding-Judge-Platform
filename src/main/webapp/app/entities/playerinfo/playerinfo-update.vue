<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="mockRgs2App.playerinfo.home.createOrEditLabel"
          data-cy="PlayerinfoCreateUpdateHeading"
          v-text="t$('mockRgs2App.playerinfo.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="playerinfo.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="playerinfo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mockRgs2App.playerinfo.playerId')" for="playerinfo-playerId"></label>
            <input
              type="text"
              class="form-control"
              name="playerId"
              id="playerinfo-playerId"
              data-cy="playerId"
              :class="{ valid: !v$.playerId.$invalid, invalid: v$.playerId.$invalid }"
              v-model="v$.playerId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mockRgs2App.playerinfo.playerName')" for="playerinfo-playerName"></label>
            <input
              type="text"
              class="form-control"
              name="playerName"
              id="playerinfo-playerName"
              data-cy="playerName"
              :class="{ valid: !v$.playerName.$invalid, invalid: v$.playerName.$invalid }"
              v-model="v$.playerName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mockRgs2App.playerinfo.balance')" for="playerinfo-balance"></label>
            <input
              type="number"
              class="form-control"
              name="balance"
              id="playerinfo-balance"
              data-cy="balance"
              :class="{ valid: !v$.balance.$invalid, invalid: v$.balance.$invalid }"
              v-model.number="v$.balance.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('mockRgs2App.playerinfo.playerActivityLogs')"
              for="playerinfo-playerActivityLogs"
            ></label>
            <select
              class="form-control"
              id="playerinfo-playerActivityLogs"
              data-cy="playerActivityLogs"
              name="playerActivityLogs"
              v-model="playerinfo.playerActivityLogs"
            >
              <option :value="null"></option>
              <option
                :value="
                  playerinfo.playerActivityLogs && playerActivityLogsOption.id === playerinfo.playerActivityLogs.id
                    ? playerinfo.playerActivityLogs
                    : playerActivityLogsOption
                "
                v-for="playerActivityLogsOption in playerActivityLogs"
                :key="playerActivityLogsOption.id"
              >
                {{ playerActivityLogsOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./playerinfo-update.component.ts"></script>
