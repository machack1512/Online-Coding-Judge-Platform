import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PlayerActivityLogsService from './player-activity-logs.service';
import { type IPlayerActivityLogs } from '@/shared/model/player-activity-logs.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PlayerActivityLogsDetails',
  setup() {
    const playerActivityLogsService = inject('playerActivityLogsService', () => new PlayerActivityLogsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const playerActivityLogs: Ref<IPlayerActivityLogs> = ref({});

    const retrievePlayerActivityLogs = async playerActivityLogsId => {
      try {
        const res = await playerActivityLogsService().find(playerActivityLogsId);
        playerActivityLogs.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.playerActivityLogsId) {
      retrievePlayerActivityLogs(route.params.playerActivityLogsId);
    }

    return {
      alertService,
      playerActivityLogs,

      previousState,
      t$: useI18n().t,
    };
  },
});
