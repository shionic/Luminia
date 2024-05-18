import BaseService from "./base-service"
import type Result from "./remote/result";
import type User from "./remote/user";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class UserService {
    static async current() : Promise<Result<User>> {
        return await BaseService.get<User>("/user/current");
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