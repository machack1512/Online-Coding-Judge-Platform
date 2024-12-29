import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Playerinfo = () => import('@/entities/playerinfo/playerinfo.vue');
const PlayerinfoUpdate = () => import('@/entities/playerinfo/playerinfo-update.vue');
const PlayerinfoDetails = () => import('@/entities/playerinfo/playerinfo-details.vue');

const PlayerActivityLogs = () => import('@/entities/player-activity-logs/player-activity-logs.vue');
const PlayerActivityLogsUpdate = () => import('@/entities/player-activity-logs/player-activity-logs-update.vue');
const PlayerActivityLogsDetails = () => import('@/entities/player-activity-logs/player-activity-logs-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'playerinfo',
      name: 'Playerinfo',
      component: Playerinfo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'playerinfo/new',
      name: 'PlayerinfoCreate',
      component: PlayerinfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'playerinfo/:playerinfoId/edit',
      name: 'PlayerinfoEdit',
      component: PlayerinfoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'playerinfo/:playerinfoId/view',
      name: 'PlayerinfoView',
      component: PlayerinfoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-activity-logs',
      name: 'PlayerActivityLogs',
      component: PlayerActivityLogs,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-activity-logs/new',
      name: 'PlayerActivityLogsCreate',
      component: PlayerActivityLogsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-activity-logs/:playerActivityLogsId/edit',
      name: 'PlayerActivityLogsEdit',
      component: PlayerActivityLogsUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'player-activity-logs/:playerActivityLogsId/view',
      name: 'PlayerActivityLogsView',
      component: PlayerActivityLogsDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
