/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Playerinfo from './playerinfo.vue';
import PlayerinfoService from './playerinfo.service';
import AlertService from '@/shared/alert/alert.service';

type PlayerinfoComponentType = InstanceType<typeof Playerinfo>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Playerinfo Management Component', () => {
    let playerinfoServiceStub: SinonStubbedInstance<PlayerinfoService>;
    let mountOptions: MountingOptions<PlayerinfoComponentType>['global'];

    beforeEach(() => {
      playerinfoServiceStub = sinon.createStubInstance<PlayerinfoService>(PlayerinfoService);
      playerinfoServiceStub.retrieve.resolves({ headers: {} });

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
          playerinfoService: () => playerinfoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        playerinfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Playerinfo, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(playerinfoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.playerinfos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: PlayerinfoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Playerinfo, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        playerinfoServiceStub.retrieve.reset();
        playerinfoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        playerinfoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removePlayerinfo();
        await comp.$nextTick(); // clear components

        // THEN
        expect(playerinfoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(playerinfoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
