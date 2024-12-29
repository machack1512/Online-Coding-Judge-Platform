import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PlayerinfoService from './playerinfo.service';
import { type IPlayerinfo } from '@/shared/model/playerinfo.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Playerinfo',
  setup() {
    const { t: t$ } = useI18n();
    const playerinfoService = inject('playerinfoService', () => new PlayerinfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const playerinfos: Ref<IPlayerinfo[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePlayerinfos = async () => {
      isFetching.value = true;
      try {
        const res = await playerinfoService().retrieve();
        playerinfos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePlayerinfos();
    };

    onMounted(async () => {
      await retrievePlayerinfos();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPlayerinfo) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePlayerinfo = async () => {
      try {
        await playerinfoService().delete(removeId.value);
        const message = t$('mockRgs2App.playerinfo.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePlayerinfos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      playerinfos,
      handleSyncList,
      isFetching,
      retrievePlayerinfos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePlayerinfo,
      t$,
    };
  },
});
