/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PlayerinfoUpdate from './playerinfo-update.vue';
import PlayerinfoService from './playerinfo.service';
import AlertService from '@/shared/alert/alert.service';

import PlayerActivityLogsService from '@/entities/player-activity-logs/player-activity-logs.service';

type PlayerinfoUpdateComponentType = InstanceType<typeof PlayerinfoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const playerinfoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PlayerinfoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Playerinfo Management Update Component', () => {
    let comp: PlayerinfoUpdateComponentType;
    let playerinfoServiceStub: SinonStubbedInstance<PlayerinfoService>;

    beforeEach(() => {
      route = {};
      playerinfoServiceStub = sinon.createStubInstance<PlayerinfoService>(PlayerinfoService);
      playerinfoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          playerinfoService: () => playerinfoServiceStub,
          playerActivityLogsService: () =>
            sinon.createStubInstance<PlayerActivityLogsService>(PlayerActivityLogsService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PlayerinfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.playerinfo = playerinfoSample;
        playerinfoServiceStub.update.resolves(playerinfoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerinfoServiceStub.update.calledWith(playerinfoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        playerinfoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PlayerinfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.playerinfo = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerinfoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        playerinfoServiceStub.find.resolves(playerinfoSample);
        playerinfoServiceStub.retrieve.resolves([playerinfoSample]);

        // WHEN
        route = {
          params: {
            playerinfoId: `${playerinfoSample.id}`,
          },
        };
        const wrapper = shallowMount(PlayerinfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.playerinfo).toMatchObject(playerinfoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        playerinfoServiceStub.find.resolves(playerinfoSample);
        const wrapper = shallowMount(PlayerinfoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
