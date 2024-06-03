import BaseService from "./base-service"
import type Result from "./remote/result";
import type Course from "./remote/course";
import type List from "./remote/list";
import type Task from "./remote/task";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class CourseService {
    static async byId(id: number) : Promise<Result<Course>> {
        return await BaseService.get<Course>("/course/by/id/"+id);
    }

    static async byUser(pageId: number) : Promise<Result<List<Course>>> {
        return await BaseService.get<List<Course>>("/course/by/user?pageId="+pageId);
    }

    static async create(name: string, description: string) : Promise<Result<Course>> {
        return await BaseService.post<Course>("/course/create", {
            "name": name,
            "description": description
        });
    }

    static async createTask(id: number, name: string, description: string, attachments: Array<number>, privateAttachments: Array<number>) : Promise<Result<Task>> {
        return await BaseService.post<Task>("/course/by/id/"+id+"/newtask", {
            "name": name,
            "description": description,
            "type": "TASK", // TODO
            "attachments": attachments,
            "privateAttachments": privateAttachments
        });
    }
}