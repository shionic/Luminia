import axios from "axios";
import LoginService from "./login-service"
import Result from "./remote/result";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class BaseService {
    static async test() {
        return await this.get("/test/echo")
    }

    static async get<T>(url:string) : Promise<Result<T>>{
        await LoginService.requireAuthorize();
        var axiosResponse = await axios.get(serverUrl + url, {
            headers: {
                'Authorization': LoginService.getAuthorizationHeader()
            }
        })
        if(axiosResponse.status >= 200 && axiosResponse.status < 300) {
            var data : T|null = axiosResponse.data;
            return new Result(data, null, null);
        } else {
            var err : any = axiosResponse.data;
            return new Result<T>(null, err.error, err.code);
        }
    }

    static async post<T>(url:string, postData : any) : Promise<Result<T>>{
        await LoginService.requireAuthorize();
        var axiosResponse = await axios.post(serverUrl + url, postData, {
            headers: {
                'Authorization': LoginService.getAuthorizationHeader()
            }
        })
        if(axiosResponse.status >= 200 && axiosResponse.status < 300) {
            var data : T|null = axiosResponse.data;
            return new Result(data, null, null);
        } else {
            var err : any = axiosResponse.data;
            return new Result<T>(null, err.error, err.code);
        }
    }
}