/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PlayerActivityLogs from './player-activity-logs.vue';
import PlayerActivityLogsService from './player-activity-logs.service';
import AlertService from '@/shared/alert/alert.service';

type PlayerActivityLogsComponentType = InstanceType<typeof PlayerActivityLogs>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PlayerActivityLogs Management Component', () => {
    let playerActivityLogsServiceStub: SinonStubbedInstance<PlayerActivityLogsService>;
    let mountOptions: MountingOptions<PlayerActivityLogsComponentType>['global'];

    beforeEach(() => {
      playerActivityLogsServiceStub = sinon.createStubInstance<PlayerActivityLogsService>(PlayerActivityLogsService);
      playerActivityLogsServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          playerActivityLogsService: () => playerActivityLogsServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        playerActivityLogsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(PlayerActivityLogs, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(playerActivityLogsServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.playerActivityLogs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: PlayerActivityLogsComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PlayerActivityLogs, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        playerActivityLogsServiceStub.retrieve.reset();
        playerActivityLogsServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        playerActivityLogsServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removePlayerActivityLogs();
        await comp.$nextTick(); // clear components

        // THEN
        expect(playerActivityLogsServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(playerActivityLogsServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
