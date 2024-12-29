/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PlayerinfoDetails from './playerinfo-details.vue';
import PlayerinfoService from './playerinfo.service';
import AlertService from '@/shared/alert/alert.service';

type PlayerinfoDetailsComponentType = InstanceType<typeof PlayerinfoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const playerinfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Playerinfo Management Detail Component', () => {
    let playerinfoServiceStub: SinonStubbedInstance<PlayerinfoService>;
    let mountOptions: MountingOptions<PlayerinfoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      playerinfoServiceStub = sinon.createStubInstance<PlayerinfoService>(PlayerinfoService);

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
          playerinfoService: () => playerinfoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        playerinfoServiceStub.find.resolves(playerinfoSample);
        route = {
          params: {
            playerinfoId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(PlayerinfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.playerinfo).toMatchObject(playerinfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        playerinfoServiceStub.find.resolves(playerinfoSample);
        const wrapper = shallowMount(PlayerinfoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
