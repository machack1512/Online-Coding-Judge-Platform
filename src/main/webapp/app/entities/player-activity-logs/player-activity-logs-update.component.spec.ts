/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PlayerActivityLogsUpdate from './player-activity-logs-update.vue';
import PlayerActivityLogsService from './player-activity-logs.service';
import AlertService from '@/shared/alert/alert.service';

type PlayerActivityLogsUpdateComponentType = InstanceType<typeof PlayerActivityLogsUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const playerActivityLogsSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PlayerActivityLogsUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PlayerActivityLogs Management Update Component', () => {
    let comp: PlayerActivityLogsUpdateComponentType;
    let playerActivityLogsServiceStub: SinonStubbedInstance<PlayerActivityLogsService>;

    beforeEach(() => {
      route = {};
      playerActivityLogsServiceStub = sinon.createStubInstance<PlayerActivityLogsService>(PlayerActivityLogsService);
      playerActivityLogsServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          playerActivityLogsService: () => playerActivityLogsServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PlayerActivityLogsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.playerActivityLogs = playerActivityLogsSample;
        playerActivityLogsServiceStub.update.resolves(playerActivityLogsSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerActivityLogsServiceStub.update.calledWith(playerActivityLogsSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        playerActivityLogsServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PlayerActivityLogsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.playerActivityLogs = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerActivityLogsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        playerActivityLogsServiceStub.find.resolves(playerActivityLogsSample);
        playerActivityLogsServiceStub.retrieve.resolves([playerActivityLogsSample]);

        // WHEN
        route = {
          params: {
            playerActivityLogsId: `${playerActivityLogsSample.id}`,
          },
        };
        const wrapper = shallowMount(PlayerActivityLogsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.playerActivityLogs).toMatchObject(playerActivityLogsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        playerActivityLogsServiceStub.find.resolves(playerActivityLogsSample);
        const wrapper = shallowMount(PlayerActivityLogsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
