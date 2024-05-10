import BaseService from "./base-service"
import type List from "./remote/list";
import type TaskAssign from "./remote/taskassign";
import type Result from "./remote/result";
import type TaskResult from "./remote/taskresult";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class TaskService {
    static async byStatus(status: string, pageId: number) : Promise<Result<List<TaskAssign>>> {
        return await BaseService.get<List<TaskAssign>>("/taskassign/by/status/"+status+"?pageId="+pageId);
    }

    static async byId(id: number) : Promise<Result<TaskAssign>> {
        return await BaseService.get<TaskAssign>("/taskassign/by/id/"+id);
    }

    static async getResultById(id: number, isSelf: boolean) : Promise<Result<TaskResult>> {
        return await BaseService.get<TaskResult>("/taskassign/by/id/"+id+"/result?self="+isSelf);
    }

    static async upload(id: number, attachments : Array<number>) : Promise<Result<TaskResult>> {
        return await BaseService.post<TaskResult>("/taskassign/by/id/"+id+"/upload", {
            attachments: attachments
        });
    }
}