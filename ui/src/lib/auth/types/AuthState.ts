import type { User } from "./User"

export type AuthState = {
    user: User | null,
    signedIn: boolean
}
