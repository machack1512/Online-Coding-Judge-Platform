<template>
  <div>
    <b-dropdown-item to="/playerinfo">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.playerinfo')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/player-activity-logs">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.playerActivityLogs')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
