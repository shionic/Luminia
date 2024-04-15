export default class Attachment {
    public id: number
    public fileName: string
    public path: string
    public size: number
    public user_id: number
    public uploadDate: string

    constructor(
        id: number,
        fileName: string,
        path: string,
        size: number,
        user_id: number,
        uploadDate: string
    ) {
        this.id = id
        this.fileName = fileName
        this.path = path
        this.size = size
        this.user_id = user_id
        this.uploadDate = uploadDate
    }
}