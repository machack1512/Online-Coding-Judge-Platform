/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PlayerActivityLogsDetails from './player-activity-logs-details.vue';
import PlayerActivityLogsService from './player-activity-logs.service';
import AlertService from '@/shared/alert/alert.service';

type PlayerActivityLogsDetailsComponentType = InstanceType<typeof PlayerActivityLogsDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const playerActivityLogsSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PlayerActivityLogs Management Detail Component', () => {
    let playerActivityLogsServiceStub: SinonStubbedInstance<PlayerActivityLogsService>;
    let mountOptions: MountingOptions<PlayerActivityLogsDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      playerActivityLogsServiceStub = sinon.createStubInstance<PlayerActivityLogsService>(PlayerActivityLogsService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          playerActivityLogsService: () => playerActivityLogsServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        playerActivityLogsServiceStub.find.resolves(playerActivityLogsSample);
        route = {
          params: {
            playerActivityLogsId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(PlayerActivityLogsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.playerActivityLogs).toMatchObject(playerActivityLogsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        playerActivityLogsServiceStub.find.resolves(playerActivityLogsSample);
        const wrapper = shallowMount(PlayerActivityLogsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
