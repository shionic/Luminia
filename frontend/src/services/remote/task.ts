import type Attachment from './attachment'
import type List from './list'

export default class Task {
    public id: number
    public displayName: string
    public attachments: List<Attachment>
    public task: string
    public status: string

    constructor(
        id: number,
        displayName: string,
        attachments: List<Attachment>,
        task: string,
        status: string
    ) {
        this.id = id
        this.displayName = displayName
        this.attachments = attachments
        this.task = task
        this.status = status
    }
}