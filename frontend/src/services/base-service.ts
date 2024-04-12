import axios from "axios";
import LoginService from "./login-service"

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default {
    async test() {
        await LoginService.requireAuthorize();
        return await axios.get(serverUrl + "/test/echo", {
            headers: {
                'Authorization': LoginService.getAuthorizationHeader()
            }
        })
    }
}