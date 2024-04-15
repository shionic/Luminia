import type Task from './task'
import type User from './user'

export default class TaskAssign {
    public id: number
    public variant: string
    public type: string
    public deadlineDate: string
    public importantDate: string
    public task: Task
    public target: User

    constructor(
        id: number,
        variant: string,
        type: string,
        deadlineDate: string,
        importantDate: string,
        task: Task,
        target: User
    ) {
        this.id = id
        this.variant = variant
        this.type = type
        this.deadlineDate = deadlineDate
        this.importantDate = importantDate
        this.task = task
        this.target = target
    }
}