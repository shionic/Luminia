import type Attachment from './attachment'
import type Course from './course'
import type List from './list'

export default class Task {
    public id: number
    public displayName: string
    public attachments: List<Attachment>
    public task: string
    public course: Course
    public status: string

    constructor(
        id: number,
        displayName: string,
        attachments: List<Attachment>,
        task: string,
        course: Course,
        status: string
    ) {
        this.id = id
        this.displayName = displayName
        this.attachments = attachments
        this.task = task
        this.course = course
        this.status = status
    }
}