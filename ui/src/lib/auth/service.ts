import axios from "axios";
import type { User } from "./types/User";
import type { SignInData, SignUpData } from "./types/SignData";

const API_ENDPOINT = "http://localhost:8080/auth/";

export class AuthService {
    signIn(user: User): Promise<User> | null {
        if (!user.password) {
            return null;
        }
        const data: SignInData = {
            username: user.username,
            password: user.password
        };
        return axios
            .post(API_ENDPOINT + "signin", data)
            .then(response => {
                const user = response.data as User;
                if (user.token) {
                    localStorage.setItem("user", JSON.stringify(user));
                }
                return user;
            });
    }

    signOut() {
        localStorage.removeItem("user");
    }

    signUp(user: User): Promise<User> | null {
        if (!user.password || !user.firstName || !user.lastName) {
            return null;
        }
        const data: SignUpData = {
            username: user.username,
            password: user.password,
            firstName: user.firstName,
            lastName: user.firstName
        };
        return axios.post(API_ENDPOINT + "signup", data);
    }
}

