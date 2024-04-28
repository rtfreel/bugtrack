import { writable } from "svelte/store";
import type { User } from "./types/User";
import type { AuthState } from "./types/AuthState";
import { AuthService } from "./service";

const service = new AuthService();

function createAuthState() {
    const userJson = localStorage.getItem("user");
    const user = userJson ? JSON.parse(userJson) as User : null;
    const initialState: AuthState = user
        ? { user: user, signedIn: true }
        : { user: null, signedIn: false };

    const { subscribe, set } = writable<AuthState>(initialState);

    const signIn = function(user: User) {
        return service.signIn(user)?.then(
            response => {
                set({ user: response, signedIn: true });
                return Promise.resolve(response);
            },
            error => {
                set({ user: null, signedIn: false });
                return Promise.reject(error);
            }
        );
    }
    const signOut = function() {
        set({ user: null, signedIn: false });
        return service.signOut();
    }
    const signUp = function(user: User) {
        return service.signUp(user)?.then(
            response => {
                set({ user: null, signedIn: false });
                return Promise.resolve(response);
            },
            error => {
                set({ user: null, signedIn: false });
                return Promise.reject(error);
            }
        );
    }

    return {
        subscribe,
        signIn,
        signOut,
        signUp
    };
}

export const authState = createAuthState();
