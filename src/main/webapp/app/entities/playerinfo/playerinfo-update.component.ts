import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PlayerinfoService from './playerinfo.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PlayerActivityLogsService from '@/entities/player-activity-logs/player-activity-logs.service';
import { type IPlayerActivityLogs } from '@/shared/model/player-activity-logs.model';
import { type IPlayerinfo, Playerinfo } from '@/shared/model/playerinfo.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PlayerinfoUpdate',
  setup() {
    const playerinfoService = inject('playerinfoService', () => new PlayerinfoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const playerinfo: Ref<IPlayerinfo> = ref(new Playerinfo());

    const playerActivityLogsService = inject('playerActivityLogsService', () => new PlayerActivityLogsService());

    const playerActivityLogs: Ref<IPlayerActivityLogs[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      playerActivityLogsService()
        .retrieve()
        .then(res => {
          playerActivityLogs.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      playerId: {},
      playerName: {},
      balance: {},
      playerActivityLogs: {},
    };
    const v$ = useVuelidate(validationRules, playerinfo as any);
    v$.value.$validate();

    return {
      playerinfoService,
      alertService,
      playerinfo,
      previousState,
      isSaving,
      currentLanguage,
      playerActivityLogs,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.playerinfo.id) {
        this.playerinfoService()
          .update(this.playerinfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mockRgs2App.playerinfo.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.playerinfoService()
          .create(this.playerinfo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mockRgs2App.playerinfo.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
