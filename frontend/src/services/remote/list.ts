export default class List<T> {
    public list: Array<T>
    public totalPages: number
    public size: number

    constructor(list: Array<T>, totalPages: number, size: number) {
        this.list = list;
        this.totalPages = totalPages;
        this.size = size;
    }
}