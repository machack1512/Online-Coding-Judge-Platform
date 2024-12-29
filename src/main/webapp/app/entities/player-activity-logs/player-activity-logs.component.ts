import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PlayerActivityLogsService from './player-activity-logs.service';
import { type IPlayerActivityLogs } from '@/shared/model/player-activity-logs.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PlayerActivityLogs',
  setup() {
    const { t: t$ } = useI18n();
    const playerActivityLogsService = inject('playerActivityLogsService', () => new PlayerActivityLogsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const playerActivityLogs: Ref<IPlayerActivityLogs[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePlayerActivityLogss = async () => {
      isFetching.value = true;
      try {
        const res = await playerActivityLogsService().retrieve();
        playerActivityLogs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePlayerActivityLogss();
    };

    onMounted(async () => {
      await retrievePlayerActivityLogss();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPlayerActivityLogs) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePlayerActivityLogs = async () => {
      try {
        await playerActivityLogsService().delete(removeId.value);
        const message = t$('mockRgs2App.playerActivityLogs.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePlayerActivityLogss();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      playerActivityLogs,
      handleSyncList,
      isFetching,
      retrievePlayerActivityLogss,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePlayerActivityLogs,
      t$,
    };
  },
});
