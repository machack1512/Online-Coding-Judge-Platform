<template>
  <div class="home row">
    <div class="col-md-9">
      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username" v-text="t$('home.logged.message', { username: username })"></span>
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.authenticated.prefix')"></span>
          <a class="alert-link" @click="openLogin()" v-text="t$('global.messages.info.authenticated.link')"></a>
          <span v-html="t$('global.messages.info.authenticated.suffix')"></span>
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.register.noaccount')"></span>&nbsp;
          <router-link class="alert-link" to="/register" v-text="t$('global.messages.info.register.link')"></router-link>
        </div>
      </div>

      <div v-if="authenticated">
        <h1 class="display-4" v-text="t$('home.title')"></h1>
        <p class="lead" v-text="t$('home.subtitle')"></p>
        <p class="lead" v-text="t$('home.info')"></p>

        <div class="input-container">
          <textarea 
            id="jsonInput" 
            v-model="getConfig" 
            rows="2" 
            cols="30" 
            placeholder="Enter JSON here...">
            [{"currency":"USD"}]
          </textarea>
          <button id="submitButton" @click="sendInfo">Config</button>
        </div>

        <p class="lead" v-text="t$('home.result')"></p>
        <pre>{{ JSON.stringify(configResponse, null, 2) }}</pre>

        <div>
          <p class="lead" v-text="t$('home.newGame')"></p>
          <div class="input-container">
            <textarea 
              id="jsonInput" 
              v-model="newGame" 
              rows="2" 
              cols="30" 
              placeholder="Enter JSON here...">
              [{"seed": 4093917358}]
            </textarea>
            <button id="submitButton" @click="sendNewGame">New Game</button>
          </div>

          <p class="lead" v-text="t$('home.result')"></p>
          <pre>{{ JSON.stringify(newGameResponse, null, 2) }}</pre>
        </div>

        <div>
          <p class="lead" v-text="t$('home.play')"></p>
          <div class="input-container">
            <textarea 
              id="jsonInput" 
              v-model="executeSpin" 
              rows="7" 
              cols="30" 
              placeholder="Enter JSON here...">
[
  {
    "gameState": {
      "isComplete": true,
      "private": {
        "seed": 4093917358
      },
      "public": {}
    },
    "command": [
      "Spin",
      1
    ],
    "forceConfig": "",
    "currency": "USD",
    "config": {
      "allowedStake": [
        0.25,
        0.5,
        1,
        2,
        3,
        5,
        10,
        15,
        25,
        50,
        75,
        100
      ],
      "defaultBet": 1,
      "maxBet": 100,
      "maxWinCapping": 9999999,
      "minBet": 0.25
    }
  }
]
            </textarea>
            <button id="submitButton" @click="sendPlay">Execute Spin</button>
          </div>

          <p class="lead" v-text="t$('home.result')"></p>
          <pre>{{ JSON.stringify(executeSpinResponse, null, 2) }}</pre>
        </div>
      </div>
    </div>
  </div>
</template>


<script lang="ts" src="./home.component.ts"></script>

<style scoped>
.input-container {
  display: flex;
  align-items: center; /* Align items vertically */
}

textarea {
  margin-right: 10px; /* Space between textarea and button */
  resize: vertical; /* Allows vertical resizing */
  flex: 1; /* Makes textarea take available space */
}

button {
  padding: 10px 20px; /* Adds padding to the button */
  cursor: pointer; /* Changes cursor to pointer on hover */
}
</style>
