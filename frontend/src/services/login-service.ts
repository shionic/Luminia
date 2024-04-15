import axios from "axios";

const serverUrl = import.meta.env.VITE_OAUTH_URL;

const clientId = import.meta.env.VITE_OAUTH_CLIENT_ID;
const authHeaderValue = import.meta.env.VITE_OAUTH_AUTH_HEADER;
const redirectUri = import.meta.env.VITE_OAUTH_REDIRECT_URI;

const ACCESS_TOKEN_KEY : string = "access_token";
const REFRESH_TOKEN_KEY : string = "refresh_token";
const EXPIRE_IN_KEY : string = "expires_in";

export default class LoginService {
    static login() {
        let requestParams = new URLSearchParams({
            response_type: "code",
            client_id: clientId,
            redirect_uri: redirectUri,
            scope: 'read.scope write.scope'
        });
        window.location.href = serverUrl + "/oauth2/authorize?" + requestParams;
    }

    static getAuthorizationHeader() : string {
        return 'Bearer '+window.sessionStorage.getItem(ACCESS_TOKEN_KEY);
    }

    static getTokens(code : string) {
        let payload = new FormData()
        payload.append('grant_type', 'authorization_code')
        payload.append('code', code)
        payload.append('redirect_uri', redirectUri)
        payload.append('client_id', clientId)

        return axios.post(serverUrl + '/oauth2/token', payload, {
                headers: {
                    'Content-type': 'application/url-form-encoded',
                    'Authorization': authHeaderValue
                }
            }
        ).then(response => {
            this.updateSesstionStorage(response);
        })
    }

    static updateSesstionStorage(response : any) {
        var now = new Date().getTime() / 1000;
        var expired = now + response.data[EXPIRE_IN_KEY];
        window.sessionStorage.setItem(ACCESS_TOKEN_KEY, response.data[ACCESS_TOKEN_KEY]);
        window.sessionStorage.setItem(REFRESH_TOKEN_KEY, response.data[REFRESH_TOKEN_KEY]);
        window.sessionStorage.setItem(EXPIRE_IN_KEY, expired);
    }

    static async getTokenInfo() {
        await this.refreshIfNeeded();
        let payload = new FormData();
        payload.append('token', window.sessionStorage.getItem(ACCESS_TOKEN_KEY)  || '');

        return await axios.post(serverUrl + '/oauth2/token-info', payload, {
            headers: {
                'Authorization': authHeaderValue
            }
        });
    }

    static async requireAuthorize() {
        if(!window.sessionStorage.getItem(EXPIRE_IN_KEY)) {
            console.log("First authorization. EXPIRE_IN_KEY not found")
            this.login();
        }
        try {
            await this.refreshIfNeeded();
        } catch(e) {
            this.login();
        }
    }

    static async refreshIfNeeded() {
        var expiredIn = parseInt(window.sessionStorage.getItem(EXPIRE_IN_KEY) || '0');
        var now = new Date().getTime() / 1000;
        console.log("Token expired in ", expiredIn, " now ", now, " diff ", expiredIn - now)
        if(expiredIn - now > 5) {
            return;
        }
        let payload = new FormData()
        payload.append('grant_type', 'refresh_token')
        payload.append('refresh_token', window.sessionStorage.getItem(REFRESH_TOKEN_KEY) || '')
        payload.append('client_id', clientId)
        var result = await axios.post(serverUrl + '/oauth2/token', payload, {
            headers: {
                'Content-type': 'application/url-form-encoded',
                'Authorization': authHeaderValue
            }
        });
        this.updateSesstionStorage(result)
    }
}