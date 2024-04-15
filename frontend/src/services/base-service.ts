import axios from "axios";
import LoginService from "./login-service"
import Result from "./remote/result";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default {
    async test() {
        await LoginService.requireAuthorize();
        return await this.get("/test/echo")
    },

    async get<T>(url:string) : Promise<Result<T>>{
        await LoginService.requireAuthorize();
        var axiosResponse = await axios.get(serverUrl + url, {
            headers: {
                'Authorization': LoginService.getAuthorizationHeader()
            }
        })
        if(axiosResponse.status >= 200 && axiosResponse.status < 300) {
            var data : T|null = JSON.parse(axiosResponse.data);
            return new Result(data, null, null);
        } else {
            var err : any = JSON.parse(axiosResponse.data);
            return new Result<T>(null, err.error, err.code);
        }
    }
}