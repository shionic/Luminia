import LButton from '../components/base/LButton.vue'
import {type App, type Plugin} from 'vue';

export const MyPlugin : Plugin = {
    install(app : App) {
        app.component("l-button", LButton);
    }
};