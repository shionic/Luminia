import BaseService from "./base-service"
import type List from "./remote/list";
import type TaskAssign from "./remote/taskassign";
import type Result from "./remote/result";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class TaskService {
    static async byStatus(status: string, pageId: number) : Promise<Result<List<TaskAssign>>> {
        return await BaseService.get<List<TaskAssign>>("/task/by/status/"+status+"?pageId="+pageId);
    }
}