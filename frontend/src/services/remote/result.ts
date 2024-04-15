export default class Result<T> {
    public data: T|null
    public error: string|null
    public errorCode: number|null

    constructor(data: T|null, error: string|null, errorCode: number|null) {
        this.data = data
        this.error = error
        this.errorCode = errorCode
    }

    isOk() {
        return this.error == null && this.errorCode == null;
    }

    get() : T {
        if(this.isOk()) {
            return this.data as T;
        }
        throw new Error(this.errorString());
    }

    errorString() : string {
        if(this.error != null && this.errorCode != null) {
            return ""+this.errorCode+": "+ this.error;
        }
        if(this.error != null) {
            return this.error;
        }
        if(this.errorCode != null) {
            return "Code "+this.errorCode;
        }
         return "Unknown API error";
    }
}