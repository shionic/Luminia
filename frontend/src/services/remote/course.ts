import type User from './user'

export default class Course {
    public id: number
    public name: string
    public description: string
    public teacher: User

    constructor(
        id: number,
        name: string,
        description: string,
        teacher: User
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.teacher = teacher
    }
}