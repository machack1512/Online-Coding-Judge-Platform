import { type ComputedRef, defineComponent, inject } from 'vue';
import { useI18n } from 'vue-i18n';
import axios from 'axios';

import type LoginService from '@/account/login.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  data() {
      return {
        getConfig: '[{"currency":"USD"}]',
        newGame: '[{"seed": 4093917358}]',
        executeSpin: '[{"gameState":{"isComplete":true,"private":{"seed":4093917358},"public":{}},"command":["Spin",1],"forceConfig":"","currency":"USD","config":{"allowedStake":[0.25,0.5,1,2,3,5,10,15,25,50,75,100],"defaultBet":1,"maxBet":100,"maxWinCapping":9999999,"minBet":0.25}}]',
        configResponse: '',
        newGameResponse: '',
        executeSpinResponse: ''
      };
  },
  methods: {
      async sendInfo() {
        try {
        const jsonInput = JSON.parse(this.getConfig);
        const apiUrl = 'api/playerinfos';
        const id = localStorage.getItem('username');
        const activityapiUrl = 'api/player-activity-logs';
        const res = await axios.post('api/config', jsonInput, {
                                          headers: {
                                              'Content-Type': 'application/json'
                                          }
                                      });
        this.configResponse = res.data;
        const response = await axios.get(`${apiUrl}/${id}`);
        const now = new Date();
                const dateTime = now.toISOString();
                const randomComponent = Math.random().toString(36).substring(2, 8);
                const currentDateTime = dateTime + '-' + randomComponent;
          const temp = {
            id: currentDateTime,
            playerId: id,
            action: "config",
            beforeBalance: response.data.balance,
            afterBalance: response.data.balance,
          };
        await axios.post(`${activityapiUrl}`, temp, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        } catch (error) {
          this.configResponse = 'Error: ' + error.message;
        }
      },
      async sendNewGame() {
              try {
              const jsonInput = JSON.parse(this.newGame);
              const apiUrl = 'api/playerinfos';
              const id = localStorage.getItem('username');
              const activityapiUrl = 'api/player-activity-logs';
              const res = await axios.post('api/newGame', jsonInput, {
                                                headers: {
                                                    'Content-Type': 'application/json'
                                                }
                                            });
              this.newGameResponse = res.data;
              const response = await axios.get(`${apiUrl}/${id}`);
              const now = new Date();
                const dateTime = now.toISOString();
                const randomComponent = Math.random().toString(36).substring(2, 8);
                const currentDateTime = dateTime + '-' + randomComponent;
              const temp = {
                id: currentDateTime,
                playerId: id,
                action: "newGame",
                beforeBalance: response.data.balance,
                afterBalance: response.data.balance,
              };
              await axios.post(`${activityapiUrl}`, temp, {
                headers: {
                    'Content-Type': 'application/json',
              },
              });
              } catch (error) {
                this.response = 'Error: ' + error.message;
              }
            },
      async sendPlay() {
              try {
                const jsonInput = JSON.parse(this.executeSpin);
                const apiUrl = 'api/playerinfos';
                const activityapiUrl = 'api/player-activity-logs';
                const id = localStorage.getItem('username');
                const res = await axios.post('api/executeSpin', jsonInput, {
                                                headers: {
                                                    'Content-Type': 'application/json'
                                                }
                                            });
                this.executeSpinResponse = res.data;
                const response = await axios.get(`${apiUrl}/${id}`);
                const beforeBalance = response.data.balance;
                response.data.balance = response.data.balance + res.data.result.gameState.private.gameStatus.totalWin + res.data.result.transaction.amount;
                await axios.put(`${apiUrl}/${id}`, response.data, {
                  headers: {
                      'Content-Type': 'application/json'
                  }
                });
                const now = new Date();
                const dateTime = now.toISOString();
                const randomComponent = Math.random().toString(36).substring(2, 8);
                const currentDateTime = dateTime + '-' + randomComponent;
                const temp = {
                  id: currentDateTime,
                  playerId: id,
                  action: "executeSpin",
                  beforeBalance: beforeBalance,
                  afterBalance: response.data.balance,
                };
                await axios.post(`${activityapiUrl}`, temp, {
                  headers: {
                      'Content-Type': 'application/json',
                },
                });
              } catch (error) {
                this.response = 'Error: ' + error.message;
              }
            }
  },
  setup() {
    const loginService = inject<LoginService>('loginService');
    const playerinfoService = inject('playerinfoService', () => new PlayerinfoService());
    const authenticated = inject<ComputedRef<boolean>>('authenticated');
    const username = inject<ComputedRef<string>>('currentUsername');

    const openLogin = () => {
      loginService.openLogin();
    };

    return {
      authenticated,
      username,
      openLogin,
      t$: useI18n().t,
    };
  },
});
