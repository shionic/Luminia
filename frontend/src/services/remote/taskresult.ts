import type Attachment from "./attachment"
import type List from "./list"
import type TaskAssign from "./taskassign"
import type User from "./user"



export default class TaskResult {
    public id: number
    public taskAssign: TaskAssign
    public target: User
    public author: User
    public status: string
    public rating: number
    public attachments: List<Attachment>
    public uploadDate: string

    constructor(
        id: number,
        taskAssign: TaskAssign,
        target: User,
        author: User,
        status: string,
        rating: number,
        attachments: List<Attachment>,
        uploadDate: string
    ) {
        this.id = id
        this.taskAssign = taskAssign
        this.target = target
        this.author = author
        this.status = status
        this.rating = rating
        this.attachments = attachments
        this.uploadDate = uploadDate
    }
}