import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PlayerinfoService from './playerinfo.service';
import { type IPlayerinfo } from '@/shared/model/playerinfo.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PlayerinfoDetails',
  setup() {
    const playerinfoService = inject('playerinfoService', () => new PlayerinfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const playerinfo: Ref<IPlayerinfo> = ref({});

    const retrievePlayerinfo = async playerinfoId => {
      try {
        const res = await playerinfoService().find(playerinfoId);
        playerinfo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.playerinfoId) {
      retrievePlayerinfo(route.params.playerinfoId);
    }

    return {
      alertService,
      playerinfo,

      previousState,
      t$: useI18n().t,
    };
  },
});
