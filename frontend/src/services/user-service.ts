import BaseService from "./base-service"
import type List from "./remote/list";
import type Result from "./remote/result";
import type SelfUser from "./remote/selfuser";
import type User from "./remote/user";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class UserService {
    static async current() : Promise<Result<SelfUser>> {
        return await BaseService.get<SelfUser>("/user/current");
    }

    static async byId(id: number) : Promise<Result<User>> {
        return await BaseService.get<User>("/user/by/id/"+id);
    }

    static async byUsername(username: string) : Promise<Result<User>> {
        return await BaseService.get<User>("/user/by/username/"+username);
    }

    static async byRating(pageId: number) : Promise<Result<List<User>>> {
        return await BaseService.get<List<User>>("/user/by/rating?pageId="+pageId);
    }

    static async search(name: string, pageId: number) : Promise<Result<List<User>>> {
        return await BaseService.get<List<User>>("/user/search?name="+name+"&pageId="+pageId);
    }

    static getName(user : User|null|undefined) : string {
        if(!user) {
            return "undefined";
        }
        if(user.fullName) {
            return user.fullName;
        }
        if(user.username) {
            return user.username;
        }
        return "uid:"+user.id;
    }
}