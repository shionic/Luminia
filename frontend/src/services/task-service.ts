import axios, { type AxiosResponse } from "axios";
import LoginService from "./login-service"
import type List from "./remote/list";
import type TaskAssign from "./remote/taskassign";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default {
    async byStatus(status: string, pageId: number) : Promise<AxiosResponse<List<TaskAssign>, any>> {
        await LoginService.requireAuthorize();
        var r = await axios.get<List<TaskAssign>, any>(serverUrl + "/task/by/status/"+status+"/page/"+pageId, {
            headers: {
                'Authorization': LoginService.getAuthorizationHeader()
            }
        })
        return r;
    }
}