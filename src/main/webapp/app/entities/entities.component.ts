import { defineComponent, provide } from 'vue';

import PlayerinfoService from './playerinfo/playerinfo.service';
import PlayerActivityLogsService from './player-activity-logs/player-activity-logs.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('playerinfoService', () => new PlayerinfoService());
    provide('playerActivityLogsService', () => new PlayerActivityLogsService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
