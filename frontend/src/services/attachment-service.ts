import BaseService from "./base-service"
import type List from "./remote/list";
import type TaskAssign from "./remote/taskassign";
import type Result from "./remote/result";
import type Attachment from "./remote/attachment";

const serverUrl = import.meta.env.VITE_BACKEND_URL;

export default class AttachmentService {
    static async upload(file: any, fileName: string) : Promise<Result<Attachment>> {
        let formData = new FormData();
        formData.append("file", file, fileName);
        return await BaseService.post<Attachment>("/attachment/upload", formData);
    }
}