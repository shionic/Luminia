import type User from './user'

export default class Course {
    public id: number
    public name: string
    public description: string
    public teacher: User
    public status: string

    constructor(
        id: number,
        name: string,
        description: string,
        teacher: User,
        status: string
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.teacher = teacher
        this.status = status
    }
}