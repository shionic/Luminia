import BaseService from "./base-service"
import type Result from "./remote/result";
import type Course from "./remote/course";
import type List from "./remote/list";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class CourseService {
    static async byId(id: number) : Promise<Result<Course>> {
        return await BaseService.get<Course>("/course/by/id/"+id);
    }
    static async byUser(pageId: number) : Promise<Result<List<Course>>> {
        return await BaseService.get<List<Course>>("/course/by/user?pageId="+pageId);
    }
}