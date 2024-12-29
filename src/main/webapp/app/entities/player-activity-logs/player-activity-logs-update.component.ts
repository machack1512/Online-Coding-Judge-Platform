import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PlayerActivityLogsService from './player-activity-logs.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IPlayerActivityLogs, PlayerActivityLogs } from '@/shared/model/player-activity-logs.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PlayerActivityLogsUpdate',
  setup() {
    const playerActivityLogsService = inject('playerActivityLogsService', () => new PlayerActivityLogsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const playerActivityLogs: Ref<IPlayerActivityLogs> = ref(new PlayerActivityLogs());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      playerId: {},
      action: {},
      beforeBalance: {},
      afterBalance: {},
    };
    const v$ = useVuelidate(validationRules, playerActivityLogs as any);
    v$.value.$validate();

    return {
      playerActivityLogsService,
      alertService,
      playerActivityLogs,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.playerActivityLogs.id) {
        this.playerActivityLogsService()
          .update(this.playerActivityLogs)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mockRgs2App.playerActivityLogs.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.playerActivityLogsService()
          .create(this.playerActivityLogs)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mockRgs2App.playerActivityLogs.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
