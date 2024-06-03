import type User from "./user";

export default class SelfUser {
    public user: User
    public roles: Array<string>

    constructor(user: User, roles: Array<string>) {
        this.user = user
        this.roles = roles
    }
} 