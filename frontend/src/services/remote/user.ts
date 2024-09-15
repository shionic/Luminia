export default class User {
    public id: number
    public rating: number
    public username: string
    public fullName: string
    public discordId: string
    public socialUsername: string
    public nameProtected: boolean
    public socialProtected: boolean

    constructor(
        id: number,
        rating: number,
        username: string,
        fullName: string,
        discordId: string,
        socialUsername: string,
        nameProtected: boolean,
        socialProtected: boolean
    ) {
        this.id = id
        this.rating = rating
        this.username = username
        this.fullName = fullName
        this.discordId = discordId
        this.socialUsername = socialUsername
        this.nameProtected = nameProtected
        this.socialProtected = socialProtected
    }
}